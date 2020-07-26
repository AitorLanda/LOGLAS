package edu.mondragon.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mondragon.tracker.dao.TrackerDao;
import edu.mondragon.tracker.model.Tracker;



@Service
@Transactional
public class TrackerServiceImp implements TrackerService {

	@Autowired
	private TrackerDao trackerDao;
	
	//------------ SETTERS --------------
	
		/**
		 * @brief	Inserts a lobby regtime into the table lobby_regtime
		 * @param 	LobbyRegtime
		 */
	@Transactional
	@Override
	public void addTracker(Tracker tracker) {
		trackerDao.addTracker(tracker);
		
	}
}