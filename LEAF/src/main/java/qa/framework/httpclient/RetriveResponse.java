package qa.framework.httpclient;

import org.apache.http.HttpResponse;

public interface RetriveResponse {

	int getStatusCode();
	String getSatusLine();
	ResponseBody getBody() throws Exception;
	
	HttpResponse getResponse();
}
