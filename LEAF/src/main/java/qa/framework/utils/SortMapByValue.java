package qa.framework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.map.LinkedMap;

public class SortMapByValue implements Comparator<Entry<String, Integer>> {

	public Map<String, Integer> sortByValue(Map<String, Integer> map, boolean order) {

		List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Map<String, Integer> sortedMap = new LinkedMap<String, Integer>();

		Collections.sort(entryList, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> entryO1, Entry<String, Integer> entryO2) {
				if (order) {
					return entryO1.getValue().compareTo(entryO2.getValue());
				} else {
					return entryO2.getValue().compareTo(entryO1.getValue());
				}

			}
		});

		entryList.forEach(x -> {

			sortedMap.put(x.getKey(), x.getValue());
		});
		
		return sortedMap;

	}
	
	public Map<String, Integer> sortByKey(Map<String, Integer> map, boolean order) {

		List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Map<String, Integer> sortedMap = new LinkedMap<String, Integer>();

		Collections.sort(entryList, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> entryO1, Entry<String, Integer> entryO2) {
				if (order) {
					return entryO1.getKey().compareTo(entryO2.getKey());
				} else {
					return entryO2.getKey().compareTo(entryO1.getKey());
				}

			}
		});

		entryList.forEach(x -> {

			sortedMap.put(x.getKey(), x.getValue());
		});
		
		return sortedMap;

	}

	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
