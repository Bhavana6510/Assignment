package qa.framework.utils;

import java.util.Comparator;
import java.util.Map;

public class SortListOfMap implements Comparator<Map<String, String>> {
	private String key;

	public SortListOfMap(String key) {
		this.key = key;
	}

	@Override
	public int compare(Map<String, String> firstMap, Map<String, String> secondMap) {

		return firstMap.get(key).compareTo(secondMap.get(key));
	}

}
