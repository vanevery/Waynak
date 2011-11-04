package org.waynak.hackathon;

public class Event {
	String eventName;
	//String imageUrl;
	int imageId;
	String description;
	
	Event (String _eventName, int _imageId, String _description) {
		eventName = _eventName;
		//imageUrl = _imageUrl;
		imageId = _imageId;
		description = _description;
	}
}	