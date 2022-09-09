package qa.framework.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckBrokenLinks {
	Logger log = LoggerHelper.getLogger(CheckBrokenLinks.class);

	public boolean checkBrokenLinks(WebDriver driver, String baseurl) {
		String url;
		HttpURLConnection huc = null;
		int respCode = 200;

		List<WebElement> links = driver.findElements(By.tagName("a"));
		Iterator<WebElement> it = links.iterator();
		while (it.hasNext()) {
			url = it.next().getAttribute("href");
			log.debug("URL:" + url);
			if (url == null || url.isEmpty()) {
				log.debug("Response:URL is either not configured for anchor tag or it is empty");
				continue;
			}
			if (!url.startsWith(baseurl)) {
				log.debug("Response:Url belongs to another domain,skipping it.");
				continue;
			}
			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400) {
					log.debug("Response:URL is broken link");
				} else {
					log.debug("Response:URL is valid link");
				}
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
			}
		}
		return true;
	}
}
