package qa.framework.runner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import qa.framework.graphql.GraphQLQuery;
import qa.framework.graphql.QueryVariable;



public class GraphQLDemoScriptsEXP {
		
	
	
	@Test(enabled = false)
	public void getAllFilmsTest2() {
		
		//https://api.spacex.land/graphql
		
		RestAssured.baseURI ="https://api.spacex.land";
		String query = "{\"query\":\"{\\n  company {\\n    name\\n    ceo\\n    coo\\n  }\\n}\",\"variables\":null}";
		
		given().log().all()
			.contentType("application/json")
			.body(query)
				.when().log().all()
					.post("/graphql")
						.then().log().all()	
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.company.ceo", equalTo("Elon Musk"));
					
	}
	
	
	@Test(enabled = true)
	public void getAllUsersTest() {
		RestAssured.baseURI ="https://hasura.io";
		String query = "{\"query\":\"{\\n  users(limit: 10) {\\n    id\\n    name\\n  }\\n}\\n\",\"variables\":null}";
		
		given().log().all()
			.contentType("application/json")
			.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9")
				
				.body(query)
					.when().log().all()
						.post("/learn/graphql")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.body("data.users[0].name", equalTo("tui.glen"));	
			
	}
	
	@DataProvider
	public Object[][] getQueryData() {
		return new Object[][] {{"10", "akshayapsangi123", "Flutter development"}
								};
	}
	
	@Test(enabled=false,dataProvider = "getQueryData")
	public void getAllUsersTestWithDataTest(String limit, String name, String title) {
		RestAssured.baseURI ="https://hasura.io";
		String query = "{\"query\":\"{\\n  users(limit: "+limit+", where: {name: {_eq: \\\""+name+"\\\"}}) {\\n    id\\n    name\\n    todos(where: {title: {_eq: \\\""+title+"\\\"}}) {\\n      title\\n    }\\n  }\\n}\\n\",\"variables\":null}";
		
		given().log().all()
			.contentType("application/json")
			.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYwYzYyMzlhNGQ1OTVkMDA2Nzg0NjEzYyJ9LCJuaWNrbmFtZSI6Im5hdmVlbmFuaW1hdGlvbjIwIiwibmFtZSI6Im5hdmVlbmFuaW1hdGlvbjIwQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci82MTVjNTVlNjBiZTU2N2ZmMDRiZTBjYTUwMmM5ZWExMz9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRm5hLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDIxLTA2LTIyVDA3OjAzOjMzLjgyMFoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MGM2MjM5YTRkNTk1ZDAwNjc4NDYxM2MiLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsImlhdCI6MTYyNDM2MTYzOCwiZXhwIjoxNjI0Mzk3NjM4LCJhdF9oYXNoIjoiSnZxVDRlNV9EY1dQNnFSbGpxbEV6USIsIm5vbmNlIjoiZXJVUU90ZkdndUM4RG1VZjh5QmRIbHFEVTByVlAtNjIifQ.FlPaekJ4feBC8xsHFYIzmPU_eC7ZKx47dUs762ztePAQtRcfPxri_Px-1I8IeAdFRQMQVBePjjzD66Zy_0ow_c8IbAghHdBjQHnP9JWtIRMDbAfTtrzJ2z8e2vx7rjY_SJR4pg5fmYtTKeUUnAUnjlDvdmyatnzqlqaXUbW2BAYR0t3irWq0uLZTU6ebRbX3Zu7Psub_APZLN25vcWx-xiJM17MWtch5FoMXQK0VtPZnpWoi5VpsQrzhxrFNGbP_7wd_c0LV3O3mf6VY0ojD5Y3RYrPtuU5HGBC6ypQfkaBszB2ENx-OA8qDLxixFIRADkbIVbLFGhogOTINNQJYsg")
				
				.body(query)
					.when().log().all()
						.post("/learn/graphql")
							.then().log().all()
								.assertThat()
									.statusCode(200);
			
	}
	
	@Test(enabled = false)
	public void getAllUsers_WithPojoTest() {
		RestAssured.baseURI ="https://hasura.io";
		GraphQLQuery query = new GraphQLQuery();
		
		query.setQuery("query ($limit: Int!, $name:String!) {\n"
				+ "  users(limit: $limit, where: {name: {_eq: $name}}) {\n"
				+ "    id\n"
				+ "    name\n"
				+ "  }\n"
				+ "}");
		
		QueryVariable variable = new QueryVariable();
		variable.setLimit(5);
		variable.setName("tui.glen");
		
		query.setVariables(variable);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYwYzYyMzlhNGQ1OTVkMDA2Nzg0NjEzYyJ9LCJuaWNrbmFtZSI6Im5hdmVlbmFuaW1hdGlvbjIwIiwibmFtZSI6Im5hdmVlbmFuaW1hdGlvbjIwQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci82MTVjNTVlNjBiZTU2N2ZmMDRiZTBjYTUwMmM5ZWExMz9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRm5hLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDIxLTA2LTIyVDA3OjAzOjMzLjgyMFoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MGM2MjM5YTRkNTk1ZDAwNjc4NDYxM2MiLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsImlhdCI6MTYyNDY3ODk4MCwiZXhwIjoxNjI0NzE0OTgwLCJhdF9oYXNoIjoiMnA3LXJpT2JJMExZQWM2WXhwV3pXdyIsIm5vbmNlIjoiM0dieDJiTEZ4RTk3QkxuUlhVdEF3Q2VGeW1tOWFDMGcifQ.eYSWINy_dhmcM5WBC8hdjInsF0Am-ed7M2MooGBAPlLvIb265NWFk_eSXv8j7qwaGbNLx7vlDqOCRFYhTqRQPthRhQedGOpU3anHGRdBPMzVKmj7v69EIaq_FlNRJLU2w2o9ZaZHOW6X00x3h4UUiBn7dysEtwTLYw57udGioS-FmrW5eziiXByxeCHC1YXU5Bo2iVT05btdXLcWk3b0Jy8QNSiljpBAn1niHjtokW85OS5a-upV58gKaueXj6p7a1nYEgvLYSg35XkQbtBSlGFo6wcjDe_3Htbe2gihfifWFl5VTMCz9btAvNc6bgG5HgrNVpA-b5SC5Kz8YCVv1g")
			.body(query)
		.when().log().all()
			.post("/learn/graphql")
		.then().log().all()
			.assertThat()
				.statusCode(200)
					.and()
						.body("data.users[0].name", equalTo("tui.glen"));
	
	
	
	}

}
