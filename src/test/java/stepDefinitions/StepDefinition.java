package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utilities;

@SuppressWarnings("unused")
public class StepDefinition extends Utilities {  //'extends Utilities' allows us to use any method within the Utilities class without having to first instantiate it,
	                                             // this is why we can call the method 'requestSpecification' directly from this class.
	
	ResponseSpecification resspec;
	RequestSpecification reqspec;
	Response res;
	static String place_id; //static allows this variable to be shared across test cases (Scenarios) within a run

	
	TestDataBuild data = new TestDataBuild();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException { //All about building an API Request and getting it ready.
		
		reqspec = given().spec(requestSpecification())  //Build the API Request specification using the method requestSpecification() from the Utilities class.
		.body(data.addPlacePayload(name, language, address));              //Body for the API Request created using the TestDataBuild class, which itself uses the pojo package classes.
		
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		APIResources resourceAPI = APIResources.valueOf(resource); //The constructor within the enum class will be used to obtain what is specified by resource
		System.out.println(resourceAPI.getResource());
		
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		//res = req.when().post("/maps/api/place/add/json")  //Replaced below with use of an enum class to obtain the API resource being called by the Feature
		
		if(method.equalsIgnoreCase("POST"))
			res = reqspec.when().post(resourceAPI.getResource()).then().spec(resspec).extract().response();
		else if(method.equalsIgnoreCase("GET"))
			res = reqspec.when().get(resourceAPI.getResource()).then().spec(resspec).extract().response();
		
	}
	
	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(res.getStatusCode(),200);
	}
	
	@And("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(getJsonPath(res, keyValue), expectedValue);
	}
	    
	
	@And("verify place_Id created maps to {string} using {string}")
		public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
		    // Write code here that turns the phrase above into concrete actions
		
		//Create the RequestSpec for the apiCall
		
		place_id = getJsonPath(res, "place_id");  //The place_id from the API call made above to Add a place
		reqspec = given().spec(requestSpecification()).queryParam("place_id", place_id);  //Create the RequestSpec using the method requestSpecification() from the Utilities class
		                                                                                  //and include the query parameter for the place_id
		
		//Execute the RequestSpec using the method 'user_calls_with_http_request()' which will populate the Response into the 'res' Response object.
		
		user_calls_with_http_request(resource, "GET");
		
		String actualName = getJsonPath(res, "name");
		assertEquals(expectedName, actualName);
		
		    
	}



	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    
		reqspec = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id)); //Create the RequestSpec using the method requestSpecification() from the Utilities class
		//and include the payload for the body
		
		
	    
	    
	}


	
	


}
