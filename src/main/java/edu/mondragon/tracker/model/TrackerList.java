package edu.mondragon.tracker.model;

import java.util.List;

public class TrackerList {
	
	List<Tracker> trackerList;
	
	
	public TrackerList() {
		
	}
	
	public List<Tracker> getTrackerList() {
		return trackerList;
	}

	public void setTrackerList(List<Tracker> trackerList) {
		this.trackerList = trackerList;
	}

	public int size() {
		// TODO Auto-generated method stub
		return trackerList.size();
	}

	public Tracker get(int i) {
		// TODO Auto-generated method stub
		return trackerList.get(i);
	}
}
