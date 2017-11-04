package com.freesoft.batch.jobs.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.freesoft.business.objects.Person;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("#### Job Finished --------> Go check the results!");
			
			List<Person> results = jdbcTemplate.query("SELECT first_name, last_name from people", new RowMapper<Person>(){
				@Override
				public Person mapRow(ResultSet resultSet, int row) throws SQLException {
					return new Person(resultSet.getString(1), resultSet.getString(2));
				}
			});
			
			for(Person person : results) {
				LOGGER.info("#### Person found: - " + person + " -");
			}
		}
	}
}
