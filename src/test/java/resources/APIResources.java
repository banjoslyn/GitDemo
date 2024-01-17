package resources;

//enum is a special class in Java which has a collection of constants or methods

public enum APIResources {
	
	AddPlaceAPI("/maps/api/place/add/json"),  //Allows each of these method to be called to return their string
	getPlaceAPI("/maps/api/place/get/json"),
	deletePlaceAPI("/maps/api/place/delete/json");
	private String resource;

	APIResources(String resource) {
		// TODO Auto-generated constructor stub
		
		this.resource = resource;
	}

	public String getResource() {
		// TODO Auto-generated method stub
		return this.resource;
	}
	
	

}
