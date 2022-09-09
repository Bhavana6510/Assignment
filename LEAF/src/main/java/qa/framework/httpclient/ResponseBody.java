package qa.framework.httpclient;

public interface ResponseBody {
	Object jsonPath(String path);
	String asString();
}
