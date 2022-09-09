package qa.framwork.excel;

public enum ExcelOperation {
	LOAD("load"), CREATE("create");
	private String operation;

	ExcelOperation(String operation) {
		this.operation = operation;
	}

	public String getOpeartion() {
		return this.operation;
	}
}
