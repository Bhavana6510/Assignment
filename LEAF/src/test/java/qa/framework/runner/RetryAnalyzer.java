package qa.framework.runner;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import qa.framework.utils.LoggerHelper;

public class RetryAnalyzer implements IRetryAnalyzer {
	private Logger log;
	private int retryCount = 0;
	private int maxRetryCount = 1;

	@Override
	public boolean retry(ITestResult result) {
		log = LoggerHelper.getLogger(result.getClass());
		/* retry code-start */
		if (retryCount < maxRetryCount) {
			log.debug("Retrying Method:" + result.getName() + "again and the count is" + (retryCount + 1));
			++retryCount;
			return true;
		} else {
			return false;
		}
		/* retry code-end */
	}

}
