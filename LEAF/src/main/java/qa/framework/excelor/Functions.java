package qa.framework.excelor;

import java.util.HashMap;
import java.util.Map;

public class Functions {

	public Map<String, Object> ifnull(String ifNull, Map<String, Object> record) {
		// ifNull(ElementValue,Test)

		String[] columns = ifNull.replace("IFNULL", "").replace("(", "").replace(")", "").split(",");

		Map<String, Object> temp = new HashMap<String, Object>();

		String firstColumnValue = record.get(columns[0]).toString().trim();

		if (firstColumnValue.length() > 0) {
			temp.put(ifNull, record.get(columns[0]));
		} else {
			temp.put(ifNull, record.get(columns[1]));
		}

		return temp;
	}

}
