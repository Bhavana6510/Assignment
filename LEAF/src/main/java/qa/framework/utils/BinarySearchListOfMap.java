package qa.framework.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BinarySearchListOfMap {
	String mapKey;
	List<Map<String, String>> mapList;

	public BinarySearchListOfMap(List<Map<String, String>> mapList, String mapKey) {
		this.mapKey = mapKey;
		Collections.sort(mapList, new SortListOfMap(mapKey));// sorting list based on map key provided
		this.mapList = mapList;
	}

	public int binarySearch(int startIndex, int endIndex, String search) {
		if (endIndex >= startIndex) {
			int midIndex = startIndex + (endIndex - startIndex) / 2; // getting mid index of list
			String value = mapList.get(midIndex).get(mapKey);

			if (search.compareTo(value) == 0) {
				return midIndex;
			}
			if (search.compareTo(value) < 0) {
				// searched value is smaller then mid value
				return binarySearch(0, midIndex - 1, search);
			}
			if (search.compareTo(value) > 0) {
				// searched value is greater then mid value
				return binarySearch(midIndex + 1, endIndex, search);
			}
		}
		return -1;
	}
}
