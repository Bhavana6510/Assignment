package qa.framework.assertions;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import qa.framework.utils.Reporter;

/**
 * 
 * @author 10650956
 *
 */
public class AssertLogger {

	private static ThreadLocal<SoftAssert> threadLocal_softAssert = new ThreadLocal<SoftAssert>();

	private static String actExpHTMLInjection = "<br><div><table><tr><td><strong>Verification</strong></td><td>&nbsp:&nbsp</td><td>${operation}</td></tr><tr><td>Expected Value</td><td>&nbsp:&nbsp</td><td>${expectedValue}</td></tr><tr><td>Actual Value</td><td>&nbsp:&nbsp</td  ><td>${actualValue}</td></tr></table></div>";
	//private static String passHTMLInjection = "<strong><font color=#9ACD32>${message}</font></strong>";
	//private static String failHTMLInjection = "<strong><font color=#DC143C>${message}</font></strong>";

	public static synchronized void assertEquals(Object actValue, Object expValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Equal?")
				.replace("${actualValue}", actValue.toString()).replace("${expectedValue}", expValue.toString());

		Reporter.addStepLog(msg);
		Assert.assertEquals(expValue, actValue, msgOnFailure);

	}

	public static synchronized void assertNotEquals(Object actValue, Object expValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Not Equal?")
				.replace("${actualValue}", actValue.toString()).replace("${expectedValue}", expValue.toString());

		Reporter.addStepLog(msg);
		Assert.assertNotEquals(expValue, actValue, msgOnFailure);

	}

	public static synchronized void assertNull(Object actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Null?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "null");

		Reporter.addStepLog(msg);
		
		Assert.assertNull(actValue, msgOnFailure);

	}

	public static synchronized void assertNotNull(Object actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Not Null?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "not null");

		Reporter.addStepLog(msg);

		Assert.assertNotNull(actValue, msgOnFailure);

	}
	
	public static synchronized void assertTrue(boolean actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is True?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "true");

		Reporter.addStepLog(msg);
		Assert.assertTrue(actValue, msgOnFailure);

	}
	
	public static synchronized void assertFalse(boolean actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is False?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "false");

		Reporter.addStepLog(msg);
		Assert.assertFalse(actValue, msgOnFailure);

	}
	
	public static void multiAssertStart() {
		SoftAssert softAssert = new SoftAssert();
		threadLocal_softAssert.set(softAssert);
	}
	
	public static void multiAssertEnd() {
		threadLocal_softAssert.get().assertAll();
	}
	
	public static synchronized void multiAssertEquals(Object actValue, Object expValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Equal?")
				.replace("${actualValue}", actValue.toString()).replace("${expectedValue}", expValue.toString());

		Reporter.addStepLog(msg);
		threadLocal_softAssert.get().assertEquals(expValue, actValue, msgOnFailure);

	}

	public static synchronized void multiAssertNotEquals(Object actValue, Object expValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Not Equal?")
				.replace("${actualValue}", actValue.toString()).replace("${expectedValue}", expValue.toString());

		Reporter.addStepLog(msg);
		threadLocal_softAssert.get().assertNotEquals(expValue, actValue, msgOnFailure);

	}

	public static synchronized void multiAssertNull(Object actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Null?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "null");

		Reporter.addStepLog(msg);

		threadLocal_softAssert.get().assertNull(actValue, msgOnFailure);

	}

	public static synchronized void multiAssertNotNull(Object actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is Not Null?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "null");

		Reporter.addStepLog(msg);

		threadLocal_softAssert.get().assertNotNull(actValue, msgOnFailure);

	}
	
	public static synchronized void multiAssertTrue(boolean actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is True?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "true");

		Reporter.addStepLog(msg);
		threadLocal_softAssert.get().assertTrue(actValue, msgOnFailure);

	}
	
	public static synchronized void multiAssertFalse(boolean actValue, String msgOnFailure) {

		String msg = actExpHTMLInjection.replace("${operation}", "Is False?")
				.replace("${actualValue}", String.valueOf(actValue)).replace("${expectedValue}", "false");

		Reporter.addStepLog(msg);
		threadLocal_softAssert.get().assertFalse(actValue, msgOnFailure);

	}



}
