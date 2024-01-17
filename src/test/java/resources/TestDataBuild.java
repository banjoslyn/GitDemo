package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {

	public AddPlace addPlacePayload(String name, String language, String address)
	{
		AddPlace p = new AddPlace();
		
		p.setAccuracy(50);
		p.setAddress(address);
		p.setLanguage(language);
		p.setName(name);
		p.setWebsite("http://rahulshettyacademy.com");
		p.setPhone_number("(+44) 444 444 3333");
		
		List<String> myList = new ArrayList<String>();
		myList.add("Tennis Racket");
		myList.add("Shop");
		p.setTypes(myList);
		
		
		Location l = new Location();
		l.setLat(51.5465223682084);
		l.setLng(-0.815000681438015);
		p.setLocation(l);
		
		return p;

	}
	
	public String deletePlacePayload(String placeId)
	{
		return "{\r\n \"place_id\":\""+placeId+"\"\r\n}";
		
	}
}
