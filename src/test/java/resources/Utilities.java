package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utilities {
	
	public static RequestSpecification reqspec; //public static is required in order to prevent reqspec being initialised for each test case being run
	
	public RequestSpecification requestSpecification() throws IOException 
	{ //This RequestSpec is used by all API requests having the same base URI
		
		if(reqspec==null) //If this is the first API Request run of a test execution.
		{
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));  //Object used by the Filter used for all logs written to the API Request and Response.
		
		reqspec = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
				  .addFilter(RequestLoggingFilter.logRequestTo(log))  //.addFilter is the mechanism for capturing any logs written for the API Request.
				  .addFilter(ResponseLoggingFilter.logResponseTo(log)) //.addFilter is the mechanism for capturing any logs written for the API Response.
		          .setContentType(ContentType.JSON).build();
		 
		return reqspec;
		}
		return reqspec; //API Request already exists for the test execution. 
		
	}
	
	public static String getGlobalValue(String key) throws IOException //'static' in order to call this method without having to instantiate its class.
	{
		Properties prop = new Properties();
		
		FileInputStream fis = new FileInputStream("C:\\Users\\BrianJoslyn\\SeleniumTraining\\APIFramework\\src\\test\\java\\resources\\global.properties");
		
		prop.load(fis);
		return prop.getProperty(key);
		
	}
	
	public String getJsonPath(Response response, String key)
	{
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	    
	}

}
