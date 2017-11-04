package com.freesoft.batch.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.freesoft.business.objects.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonItemProcessor.class);

	@Override
	public Person process(Person item) throws Exception {

		String firstName = item.getFirstName().toUpperCase();
		String lastName = item.getLastName().toUpperCase();

		Person processedPerson = new Person(firstName, lastName);

		LOGGER.info("#### Converting (" + item + ") into (" + processedPerson + ")");

		return processedPerson;
	}

}
