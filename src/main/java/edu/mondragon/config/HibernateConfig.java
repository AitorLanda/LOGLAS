package edu.mondragon.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import edu.mondragon.controller.SpringController;
import edu.mondragon.lobby.model.Lobby;
import edu.mondragon.lobbyregtime.model.LobbyRegtime;
import edu.mondragon.playerscore.model.PlayerScore;
import edu.mondragon.tracker.model.Tracker;
import edu.mondragon.user.model.User;

/**
 * @version v0.01
 * @brief  	Hibernate Configuration class. 
 * 			For database management.
 * 			(check on MUdle: Spring + Hibernate)
 * @see		SpringController and WebInitializer
 * @date	Creation: 12/02/19
 * @date	Update: "Tiles" 12/04/19
 * @author 	Loredi Altzibar
 *
 */

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class HibernateConfig {
	
	@Autowired
	private Environment env;
 
	
	/**
	 * @brief	Reads database.properties located in classpath (src/main/resources/...)
	 * @return	BasicDataSource: data "read" from database.properties
	 */
	@Bean
	public DataSource getDataSource() {	    
	    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
	    dataSourceBuilder.driverClassName(env.getProperty("database.driver"));
	    dataSourceBuilder.url(env.getProperty("database.url"));
	    dataSourceBuilder.username(env.getProperty("database.username"));
	    dataSourceBuilder.password(env.getProperty("database.password"));
	    
	    return dataSourceBuilder.build();
	 }
	
	/**
	 * @brief	Creates a Hibernate SessionFactory. 
	 * 			This is the usual way to set up a shared Hibernate SessionFactory 
	 * 			in a Spring application context.
	 * @return	LocalSessionFactoryBean 
	 */
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());
        
        Properties props = new Properties();
        props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
	    props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
	    props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        
	    factoryBean.setHibernateProperties(props);
	    
	    // IMPORTANT - set Repositories BEAN !!!
        Class<?>[] allAnnotatedClasses = new Class<?>[]{User.class, Lobby.class, PlayerScore.class, LobbyRegtime.class, Tracker.class};
        factoryBean.setAnnotatedClasses(allAnnotatedClasses);
        
        
        return factoryBean;
    }
 
    /**
	 * @brief	Binds a Hibernate Session from the specified factory to the thread, 
	 * 			allowing for one thread-bound Session per factory. 
	 * 			This transaction manager is used for applications that use 
	 * 			a single Hibernate SessionFactory for transactional data access
	 * @return	LocalSessionFactoryBean 
	 */
    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
	
}
