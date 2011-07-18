package com.jeroenreijn.mongodb.example;

import java.util.Iterator;
import java.util.List;

import com.jeroenreijn.mongodb.example.domain.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Jeroen Reijn
 */
@Repository
public class PersonRepository {

    static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    @Autowired
    MongoTemplate mongoTemplate;

    public void logAllPersons() {
        List<Person> results = mongoTemplate.findAll(Person.class);
        logger.info("Total amount of persons: {}", results.size());
        logger.info("Results: {}", results);
    }

    /**
     * Calculates the average age of a {@link Person}.
     * @return the average age
     */
    public int getAvaragePersonAge() {
        List<Person> results = mongoTemplate.findAll(Person.class);
        int age = 0;
        Iterator<Person> personIterator = results.iterator();
        while (personIterator.hasNext()) {
            Person nextPerson = personIterator.next();
            age += nextPerson.getAge();
        }
        return age / results.size();
    }

    public void insertPersonWithRandomAge() {
        //get random age between 1 and 100
        double age = Math.ceil(Math.random() * 100);

        Person p = new Person("John", (int) age);

        mongoTemplate.insert(p);
    }

    /**
     * Create a {@link Person} collection if the collection does not already exists
     */
    public void createPersonCollection() {
        if (!mongoTemplate.collectionExists(Person.class)) {
            mongoTemplate.createCollection(Person.class);
        }
    }

    /**
     * Drops the {@link Person} collection if the collection does already exists
     */
    public void dropPersonCollection() {
        if (mongoTemplate.collectionExists(Person.class)) {
            mongoTemplate.dropCollection(Person.class);
        }
    }
}
