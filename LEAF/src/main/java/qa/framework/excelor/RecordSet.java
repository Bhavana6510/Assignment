package qa.framework.excelor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordSet {

	private List<Map<String, Object>> record;

	public RecordSet() {
		super();
		this.record = new ArrayList<Map<String, Object>>();
	}
	
	protected List<Map<String, Object>> getRecord() {
		return this.record;
	}

}
