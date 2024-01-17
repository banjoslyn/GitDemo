package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		//write a code to give you a place id
		//to be executed only when place id is null
		
		StepDefinition m = new StepDefinition();
		if(StepDefinition.place_id == null)  //Because place_id is static, Java recommends use of the class name and not the object name for accessing it.
			                                 //Due to the 'static' keyword being tied to class memory and not object memory
		{
			m.add_place_payload_with("AnyName", "English", "55 Nowhere Street");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_Id_created_maps_to_using("AnyName", "getPlaceAPI");
		}
		
		
	}

}
