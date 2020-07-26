package edu.mondragon.tracker.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.mondragon.tracker.model.Tracker;


@Repository
public class TrackerDaoImp implements TrackerDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addTracker(Tracker tracker) {
		sessionFactory.getCurrentSession().save(tracker);
		
	}

	
}
