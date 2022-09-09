package qa.framework.excelor;

public interface Connection {

	public Object runSelectQuery(String query);
	public int runInsertQuery(String query);
	public int runDeleteQuery(String query);
	public int runUpdateQuery(String query);
	
	
}
