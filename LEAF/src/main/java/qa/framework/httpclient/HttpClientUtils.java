package qa.framework.httpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;

import com.google.common.net.HttpHeaders;

import io.restassured.path.json.JsonPath;

/**
 * Do Rest calls using HttpClient [mainly for certification use] <br><br>
 * <b>Example:</b> 
 * <br><br>
 * HttpClientUtils.baseURI = "https://application.com/something";
 * <br>
 * HttpClientUtils.basePath = "/Entity/{resourceId}";
 * <br>
 * RetriveResponse response = HttpClientUtils.given()
 * <br>
 * .setPathParameter("resourceId", "123")
 * <br>
 * .buildURI()
 * <br>
 * .setQueryParameter("password","password")
 * <br>
 * .setCertificate("<path of .pfx * certificate>", "<password>")
 * <br>
 * .executeRequest(MethodType.GET);
 * <br><br>
 * int statusCode = response.getStatusCode(); 
 * <br>
 * String body = response.getBody().asString(); 
 * <br>
 * String satusLine = response.getSatusLine();
 * <br>
 * Object jsonPath = response.getBody().jsonPath("equityInfo.Dividens");
 * <br><br>
 * <b>Note:</b> first set path parameter then builURI() followed by Query/Form parameter
 * 
 * @author 10650956
 *
 */
public class HttpClientUtils implements RequestBuilder, RetriveResponse, ResponseBody {

	public static String baseURI;
	public static String basePath;

	private URIBuilder uriBuilder;
	private HttpResponse response;
	private StringEntity requestEntity;

	private static Set<NameValuePair> formParams;
	private static Set<Header> headers;
	private static HttpClientBuilder httpClientBuilder;

	private static HttpClientUtils httpClientUtils;

	private String bodyAsString;

	public static RequestBuilder given() {
		formParams = new HashSet<NameValuePair>();
		headers = new HashSet<Header>();
		httpClientBuilder = HttpClients.custom();

		httpClientUtils = new HttpClientUtils();
		return httpClientUtils;
	}

	/**
	 * Setting protocol
	 */
	private void setScheme() {
		if (baseURI.contains("https://")) {
			uriBuilder.setScheme("https");
		} else if (baseURI.contains("http://")) {
			uriBuilder.setScheme("http");
		}
	}

	private void setHost() {
		uriBuilder.setHost(baseURI.split("/")[2]);
	}

	private void setPath() {
		String leftOver = baseURI.split(uriBuilder.getHost())[1];
		if (!basePath.contains(leftOver)) {
			basePath = leftOver + basePath;
		}
		uriBuilder.setPath(basePath);
	}

	public RequestBuilder buildURI() {
		uriBuilder = new URIBuilder();
		setScheme();
		setHost();
		setPath();
		
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setContentType(String type) {

		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, type));
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setAcceptType(String type) {
		headers.add(new BasicHeader("Accept", type));
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setHeader(String headerName, String headerValue) {

		headers.add(new BasicHeader(headerName, headerValue));
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setHeader(Map<String, String> headerMap) {
		headerMap.forEach((headerName, headerValue) -> {
			headers.add(new BasicHeader(headerName, headerValue));
		});
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setPathParameter(String parameterName, String parameterValue) {
		basePath = basePath.replace("{" + parameterName + "}", parameterValue);
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setPathParameter(Map<String, String> pathParameterMap) {
		pathParameterMap.forEach((parameter, value) -> {
			basePath = basePath.replace("{" + parameter + "}", value);
		});

		return httpClientUtils;
	}

	@Override
	public RequestBuilder setQueryParameter(String queryParameterName, String queryParameterValue) {

		uriBuilder.setParameter(queryParameterName, queryParameterName);
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setQueryParameter(Map<String, String> queryParameterMap) {
		queryParameterMap.forEach((queryParameterName, queryParameterValue) -> {
			uriBuilder.setParameter(queryParameterName, queryParameterValue);
		});
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setFormParameter(String formParameterName, String formParameterValue) {

		formParams.add(new BasicNameValuePair(formParameterName, formParameterValue));

		return httpClientUtils;
	}

	@Override
	public RequestBuilder setFormParameter(Map<String, String> formParameterMap) {
		formParameterMap.forEach((parameterName, parameterValue) -> {
			formParams.add(new BasicNameValuePair(parameterName, parameterValue));
		});
		return httpClientUtils;
	}

	@Override
	public RequestBuilder setBody(String body) {

		requestEntity = new StringEntity(body, "UTF-8");
		// requestEntity = new StringEntity(body, contentType,"UFT-8");

		return httpClientUtils;
	}

	@Override
	public RequestBuilder setCertificate(String path, String password) throws Exception {

		final KeyStore store = KeyStore.getInstance("PKCS12");
		try (FileInputStream stream = new FileInputStream(new File(path))) {
			store.load(stream, password.toCharArray());
		}

		SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(store, password.toCharArray())
				.loadTrustMaterial(store, TrustAllStrategy.INSTANCE).build();

		final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);

		final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory()).register("https", sslsf).build();

		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);

		cm.setMaxTotal(100);
		cm.setValidateAfterInactivity(100);

		httpClientBuilder.setSSLSocketFactory(sslsf).setConnectionManager(cm);

		return httpClientUtils;
	}

	@Override
	public RequestBuilder setProxy(String host, int port) {

		httpClientUtils.setProxy(host, port);

		return httpClientUtils;
	}

	@Override
	public RetriveResponse executeRequest(MethodType type) throws Exception {

		//buildURI();

		URI uri = uriBuilder.build();

		if (!headers.isEmpty()) {
			httpClientBuilder.setDefaultHeaders(headers);
		}

		HttpClient client = httpClientBuilder.build();

		switch (type) {
		case POST: {
			HttpPost postRequest = new HttpPost(uri);
			if (!formParams.isEmpty()) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
				postRequest.setEntity(entity);
			}
			
			/*setting request body*/
			postRequest.setEntity(requestEntity);

			response = client.execute(postRequest);

			break;
		}

		case PUT: {
			HttpPut putRequest = new HttpPut(uri);

			if (requestEntity.getContentLength() > 0) {
				putRequest.setEntity(requestEntity);
			}
			response = client.execute(putRequest);
			break;
		}

		case DELETE: {
			HttpDelete deleteRequest = new HttpDelete(uri);
			response = client.execute(deleteRequest);
			break;
		}

		case GET: {
			HttpGet getRequest = new HttpGet(uri);
			response = client.execute(getRequest);
			break;
		}

		}

		return httpClientUtils;
	}

	/**********************************
	 * Response
	 **********************************/

	@Override
	public Object jsonPath(String path) {

		JsonPath j = new JsonPath(bodyAsString);
		return j.get(path);

	}

	@Override
	public String asString() {

		return bodyAsString;
	}

	@Override
	public int getStatusCode() {

		return response.getStatusLine().getStatusCode();
	}

	@Override
	public String getSatusLine() {

		return response.getStatusLine().toString();
	}

	@Override
	public ResponseBody getBody() {
		String line;
		StringBuilder body = new StringBuilder();

		InputStreamReader streamReader = null;
		BufferedReader bis = null;
		try {

			streamReader = new InputStreamReader(response.getEntity().getContent());
			bis = new BufferedReader(streamReader);
			while ((line = bis.readLine()) != null) {
				body.append(line + "\n");
			}
			bodyAsString = body.toString();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				bis.close();
				streamReader.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		}

		return httpClientUtils;
	}

	@Override
	public HttpResponse getResponse() {

		return response;
	}

}
