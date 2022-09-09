package qa.framework.httpclient;

import java.util.Map;

public interface RequestBuilder {
	
	RequestBuilder setContentType(String type);
	RequestBuilder setAcceptType(String type);
	
	RequestBuilder setHeader(String headerName,String headerValue);
	RequestBuilder setHeader(Map<String,String>headerMap);
	
	RequestBuilder setPathParameter(String parameterName,String parameterValue);
	RequestBuilder setPathParameter(Map<String,String> pathParameterMap);
	
	RequestBuilder buildURI();
	
	RequestBuilder setQueryParameter(String queryParameterName,String queryParameterValue);
	RequestBuilder setQueryParameter(Map<String,String> queryParameterMap);
	
	RequestBuilder setFormParameter(String formParameterName,String formParameterValue);
	RequestBuilder setFormParameter(Map<String,String> formParameterMap);
	
	RequestBuilder setBody(String body);
	RequestBuilder setCertificate(String path, String password) throws Exception;
	RequestBuilder setProxy(String host, int port);
	RetriveResponse executeRequest(MethodType type) throws Exception;
	
	
	 
}
