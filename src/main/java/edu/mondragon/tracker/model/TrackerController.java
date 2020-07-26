package edu.mondragon.tracker.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import edu.mondragon.tracker.service.TrackerService;

@Controller
@RequestMapping("/tracker")
public class TrackerController{
	
	@Autowired
	private TrackerService trackerService;
	
	
	
	@PostMapping(path = "/saveTrackData")
    public ResponseEntity<String> saveTrackData(@RequestBody String tracker) {
		Gson g = new Gson();
		//Tracker p = g.fromJson(tracker, Tracker.class);
		System.out.println(tracker);
		TrackerList p = g.fromJson(tracker, TrackerList.class);
		for (int i = 0; i < p.size(); i++) {
			
			p.get(i).setRegtimeDated(p.get(i).getRegtime());
			if(p.get(i).getClicked() == null) {
				p.get(i).setClicked("None");
			}
			System.out.println(p.get(i));
			trackerService.addTracker(p.get(i));
		}
		
		//trackerService.addTracker(tracker);
        
        return new ResponseEntity<>("Page events Saved", HttpStatus.OK);
    }
	
	
}

/*class TrackerAdapter extends TypeAdapter<Tracker> { 
	   @Override 
	   public Tracker read(JsonReader reader) throws IOException { 
	      Tracker tracker = new Tracker(null,null,null); 
	      reader.beginObject(); 
	      String fieldname = null; 
	      
	      while (reader.hasNext()) { 
	         JsonToken token = reader.peek();            
	         
	         if (token.equals(JsonToken.NAME)) {     
	            //get the current token 
	            fieldname = reader.nextName(); 
	         } 
	         
	         if ("name".equals(fieldname)) {       
	            //move to next token 
	            token = reader.peek(); 
	            student.setName(reader.nextString()); 
	         } 
	         
	         if("rollNo".equals(fieldname)) { 
	            //move to next token 
	            token = reader.peek(); 
	            student.setRollNo(reader.nextInt()); 
	         }               
	      } 
	      reader.endObject(); 
	      return student; 
	   }  
	   @Override 
	   public void write(JsonWriter writer, Tracker tracker) throws IOException { 
	     
	   } 
}*/
