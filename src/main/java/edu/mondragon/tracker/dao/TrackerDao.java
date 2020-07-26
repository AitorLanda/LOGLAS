package edu.mondragon.tracker.dao;

import org.springframework.stereotype.Repository;

import edu.mondragon.tracker.model.Tracker;



@Repository
public interface TrackerDao {
	public void addTracker(Tracker tracker);
	
	
	
}
