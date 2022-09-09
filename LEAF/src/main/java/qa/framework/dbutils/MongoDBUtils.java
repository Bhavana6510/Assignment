package qa.framework.dbutils;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * 
 * <b>Example: </b><br> 
 * String host = "10.101.0.8";//"localhost";<br> 
 * int port = 27018; <br>
 * String db ="pm"; <br>
 * String collectionName="transactions"; <br><br>
 * 
 * MongoDBUtils objMongoDB = new MongoDBUtils();<br> 
 * 
 * //1st: Connect to DB<br>
 * objMongoDB.connect(host, port, db);<br><br>
 * 
 * //2nd: Get Collection <br> 
 * MongoCollection<Document> collection = objMongoDB.getCollection(collectionName);<br><br>
 * 
 * //3rd: Apply Filter<br> 
 * List record = objMongoDB.getRecord(collection,eq("id.pet","1234"));<br><br>
 * 
 * //4th: Disconnect DB<br> 
 * objMongoDB.disconnect();<br><br>
 * 
 * <b>Reference:</b>
 * https://mongodb.github.io/mongo-java-driver/3.4/driver/tutorials/perform-read-operations/
 * 
 * 
 * @author 10650956
 *
 */
public class MongoDBUtils {

	private MongoClient mongoClient;
	private MongoDatabase dataBase;

	public void connect(String host, int port, String db) {

		String url = "mongodb://" + host + ":" + port;

		mongoClient = new MongoClient(new MongoClientURI(url));
		dataBase = mongoClient.getDatabase(db);

	}

	public List<String> getAllCollectionNames() {

		List<String> collectionNames = new ArrayList<String>();

		MongoIterable<String> listCollectionNames = dataBase.listCollectionNames();

		for (String name : listCollectionNames) {
			collectionNames.add(name);
		}

		return collectionNames;
	}

	public MongoCollection<Document> getCollection(String collection) {
		return dataBase.getCollection(collection);
	}

	/**
	 * Finds record in collection<br>
	 * Bson attribute comes from Filters. Apply filter as describe below:<br>
	 * <br>
	 * static import: import static com.mongo.db.client.mondel.Filters.*;<br>
	 * Filter method are shown below:
	 * <ul>
	 * <li>and(gte("starts",2), lt("starts",5), eq("categories", "Bakery"))
	 * <li>gte is for 'Greater Than Equal To'
	 * <li>lt is for 'Less Than'
	 * </ul>
	 * 
	 * @param collection
	 * @param bson
	 * @return List<String>
	 */
	public List<String> getRecord(MongoCollection<Document> collection, Bson bson) {
		List<String> record = new ArrayList<String>();

		MongoCursor<Document> iterator = collection.find(bson).iterator();

		while (iterator.hasNext()) {
			record.add(iterator.next().toString());
		}

		return record;
	}

	public void disconnect() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

	/*
	 * Block<Document> pringBlock = new Block<Document>() {
	 * 
	 * @Override public void apply(final Document document) {
	 * System.out.println(document.toJson); } }
	 */

}
