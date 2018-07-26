package com.dante.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil extends JsonTreeModel{

	ObjectMapper objectMapper;
	List<Person> personList;
	String jsonString;

	/**
	 * Set pretty printting: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(myObject): 
	 */
	@Before
	public void setUp() {

		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Set pretty printing of json

		personList = getPersons();
		jsonString = getJsonString();

	}

	public static void main(String[] args) throws IOException {

	}

	@Test
	public void convertObjectToJson() throws IOException {

		String arrayToJson = objectMapper.writeValueAsString(personList);

		System.out.format("Initial Object: %s%n", personList);
		System.out.format("Convert Object to Json String: %s%n", arrayToJson);

	}

	@Test
	public void convertJsonToObject() throws JsonParseException, JsonMappingException, IOException {

		TypeReference<List<Person>> mapType = new TypeReference<List<Person>>() {};
		List<Person> jsonToPersonList = objectMapper.readValue(jsonString, mapType);

		System.out.format("Initial Json String: %s%n", jsonString);
		System.out.format("Convert Json String to Object: %s%n", jsonToPersonList);

	}

	@SuppressWarnings("serial")
	public List<Person> getPersons() {

		List<Person> personList = new ArrayList<Person>() {
			{
				add(new Person("Mike", "harvey", 34));
				add(new Person("Nick", "young", 75));
			}
		};

		return personList;

	}

	public String getJsonString() {

		String str = "[ {\r\n" + 
				"  \"firstName\" : \"Mike\",\r\n" + 
				"  \"lastName\" : \"harvey\",\r\n" + 
				"  \"age\" : 34\r\n" + 
				"}, {\r\n" + 
				"  \"firstName\" : \"Nick\",\r\n" + 
				"  \"lastName\" : \"young\",\r\n" + 
				"  \"age\" : 75\r\n" + 
				"} ]";

		return str;

	}

}
