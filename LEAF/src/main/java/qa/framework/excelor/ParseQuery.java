package qa.framework.excelor;

import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.LinkedMap;

import qa.framework.utils.SortMapByValue;

public class ParseQuery {

	private String select;
	private String from;
	private String where;
	private String insertInto;
	private String delete;
	private String update;
	private String orderBy;
	private String having;
	private String groupBy;
	private String union;

	public void parseQuery(String query) {

		query = query.toUpperCase();

		Map<String, Integer> indexMap = new LinkedMap<String, Integer>();

		indexMap.put("FROM", query.indexOf("FROM"));
		indexMap.put("WHERE", query.indexOf("WHERE"));
		indexMap.put("HAVING", query.indexOf("HAVING"));
		indexMap.put("SELECT", query.indexOf("SELECT"));
		indexMap.put("DISTINCT", query.indexOf("DISTINCT"));
		indexMap.put("UNION", query.indexOf("UNION"));
		indexMap.put("ORDER BY", query.indexOf("ORDER BY"));
		indexMap.put("DELETE", query.indexOf("DELETE"));
		indexMap.put("INSERT INTO", query.indexOf("INSERT INTO"));
		indexMap.put("GROUP BY", query.indexOf("GROUP BY"));

		SortMapByValue objSortMap = new SortMapByValue();
		Map<String, Integer> sortedIndexMap = objSortMap.sortByValue(indexMap, true);

		Set<String> keySet = sortedIndexMap.keySet();
		String[] keyArray = new String[keySet.size()];
		keySet.toArray(keyArray);

		Integer[] indexArray = new Integer[sortedIndexMap.size()];
		sortedIndexMap.values().toArray(indexArray);

		for (int index = 0; index < indexArray.length; index++) {

			if (indexArray[index] >= 0) {
				String key = keyArray[index];

				if (key.equalsIgnoreCase("SELECT")) {

					try {
						this.select = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.select = query.substring(indexArray[index]);
					}

					select = select.trim();

					if (select.length() > 0) {
						select = select.replace("SELECT", "").trim();
					}

				} else if (key.equalsIgnoreCase("FROM")) {

					try {
						this.from = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.from = query.substring(indexArray[index]);
					}

					from = from.trim();

					if (from.length() > 0) {
						from = from.replace("FROM", "").trim();
					}

				} else if (key.equalsIgnoreCase("WHERE")) {

					try {
						this.where = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.where = query.substring(indexArray[index]);
					}

					where = where.trim();

					if (where.length() > 0) {
						where = where.replace("WHERE", "").trim();
					}

				} else if (key.equalsIgnoreCase("ORDER BY")) {

					try {
						this.orderBy = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.orderBy = query.substring(indexArray[index]);
					}

					orderBy = orderBy.trim();

					if (orderBy.length() > 0) {
						orderBy = orderBy.replace("ORDER BY", "").trim();
					}

				} else if (key.equalsIgnoreCase("HAVING")) {

					try {
						this.having = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.having = query.substring(indexArray[index]);
					}

					having = having.trim();

					if (having.length() > 0) {
						having = having.replace("HAVING", "").trim();
					}

				} else if (key.equalsIgnoreCase("INSERT INTO")) {

					try {
						this.insertInto = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.insertInto = query.substring(indexArray[index]);
					}

					insertInto = insertInto.trim();

					if (insertInto.length() > 0) {
						insertInto = insertInto.replace("INSERT INTO", "").trim();
					}

				} else if (key.equalsIgnoreCase("DELETE")) {

					try {
						this.delete = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.delete = query.substring(indexArray[index]);
					}

					delete = delete.trim();

					if (delete.length() > 0) {
						delete = delete.replace("DELETE", "").trim();
					}

				} else if (key.equalsIgnoreCase("UPDATE")) {

					try {
						this.update = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.update = query.substring(indexArray[index]);
					}

					update = update.trim();

					if (update.length() > 0) {
						update = update.replace("UPDATE", "").trim();
					}

				} else if (key.equalsIgnoreCase("GROUP BY")) {

					try {
						this.groupBy = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.groupBy = query.substring(indexArray[index]);
					}
					
					groupBy = groupBy.trim();

					if (groupBy.length() > 0) {
						groupBy = groupBy.replace("GROUP BY", "").trim();
					}

				} else if (key.equalsIgnoreCase("UNION")) {

					try {
						this.union = query.substring(indexArray[index], indexArray[index + 1]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						this.union = query.substring(indexArray[index]);
					}
					
					union = union.trim();

					if (union.length() > 0) {
						union = union.replace("UNION", "").trim();
					}

				}
			}
		}

	}

	public String getSelect() {
		return select;
	}

	public String getFrom() {
		return from;
	}

	public String getWhere() {
		return where;
	}

	public String getInsertInto() {
		return insertInto;
	}

	public String getDelete() {
		return delete;
	}

	public String getUpdate() {
		return update;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getHaving() {
		return having;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public String getUnion() {
		return union;
	}

}
