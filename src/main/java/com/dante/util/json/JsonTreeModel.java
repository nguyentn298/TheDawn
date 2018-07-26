package com.dante.util.json;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonTreeModel {
	
	ObjectMapper mapper;
	@Before
	public void setup() {
		mapper = new ObjectMapper();
	}

	@Test
	public void crudByTreemodel() {

		try {

			/**
			 * Get Object to test: 	getJsonStringObject()
			 * Get List to test:	getJsonStringList()
			 */
//			JsonNode root = mapper.readTree(new File("D:\\Nguyen\\test\\user.json")); // mapper can readTree File
			JsonNode root = mapper.readTree(getJsonStringObject());

			String resultOriginal = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			System.out.println("Before Update " + resultOriginal);

			((ObjectNode) root).put("id", 1000L); 					// 1. Update id to 1000

			JsonNode nameNode = root.path("name"); 					// 2. If middle name is empty , update to M
			if ("".equals(nameNode.path("middle").asText())) {
				((ObjectNode) nameNode).put("middle", "M");
			}

			((ObjectNode) nameNode).put("nickname", "mkyong"); 		// 3. Create a new field in nameNode

			((ObjectNode) nameNode).remove("last"); 				// 4. Remove last field in nameNode

			ObjectNode positionNode = mapper.createObjectNode(); 	// 5. Create a new ObjectNode and add to root
			positionNode.put("name", "Developer");
			positionNode.put("years", 10);
			((ObjectNode) root).set("position", positionNode);

			ArrayNode gamesNode = mapper.createArrayNode(); 		// 6. Create a new ArrayNode and add to root

			ObjectNode game1 = mapper.createObjectNode();
			game1.put("name", "Fall Out 4");
			game1.put("price", 49.9);

			ObjectNode game2 = mapper.createObjectNode();
			game2.put("name", "Dark Soul 3");
			game2.put("price", 59.9);

			gamesNode.add(game1);
			gamesNode.add(game2);
			((ObjectNode) root).set("games", gamesNode);

			ObjectNode email = mapper.createObjectNode(); 			// 7. Append a new Node to ArrayNode
			email.put("type", "email");
			email.put("ref", "abc@mkyong.com");

			JsonNode contactNode = root.path("contact");
			((ArrayNode) contactNode).add(email);

			String resultUpdate = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			System.out.println("After Update " + resultUpdate);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void readObjectByTreemodel() {

		try {

			/**
			 * Get Object to test: 	getJsonStringObject()
			 * Get List to test:	getJsonStringList()
			 */
//			JsonNode root = mapper.readTree(new File("D:\\Nguyen\\test\\user.json"));	 // mapper can readTree File
			JsonNode jsonNode = mapper.readTree(getJsonStringObject()); // Can get List by method b
			if(jsonNode.isArray()) {
				for (JsonNode node : jsonNode) {
					printJsonText(node);
				}
			} else {
				printJsonText(jsonNode);
			}

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void printJsonText(JsonNode jsonNode) {

		long id;
		String firstName = "";
		String middleName = "";
		String lastName = "";

		id = jsonNode.path("id").asLong(); 			// Get id
		System.out.println("id : " + id);

		JsonNode nameNode = jsonNode.path("name"); 	// Get Name
		if (nameNode.isMissingNode()) { 			// if "name" node is missing

		} else {

			firstName = nameNode.path("first").asText();
			// missing node, just return empty string
			middleName = nameNode.path("middle").asText();
			lastName = nameNode.path("last").asText();

			System.out.println("firstName : " + firstName);
			System.out.println("middleName : " + middleName);
			System.out.println("lastName : " + lastName);

		}

		JsonNode contactNode = jsonNode.path("contact"); 	// Get Contact
		if (contactNode.isArray()) { 						// If this node an Arrray?

			for (JsonNode node : contactNode) {

				String type = node.path("type").asText();
				String ref = node.path("ref").asText();
				System.out.println("type : " + type);
				System.out.println("ref : " + ref);

			}

		} else {

			String type = contactNode.path("type").asText();
			String ref = contactNode.path("ref").asText();
			System.out.println("type : " + type);
			System.out.println("ref : " + ref);

		}

	}

	public String getJsonStringObject() {

		String str = "{\r\n" + 
				"  \"id\"   : 1,\r\n" + 
				"  \"name\" : {\r\n" + 
				"    \"first\" : \"Yong\",\r\n" + 
				"    \"last\" : \"Mook Kim\" \r\n" + 
				"  },\r\n" + 
				"  \"contact\" : [\r\n" + 
				"    { \"type\" : \"phone/home\", \"ref\" : \"111-111-1234\"},\r\n" + 
				"    { \"type\" : \"phone/work\", \"ref\" : \"222-222-2222\"}\r\n" + 
				"  ]\r\n" + 
				"}";

		return str;

	}

	public String getJsonStringList() {

		String str = "[\r\n" + 
				" {\r\n" + 
				"  \"id\"   : 1,\r\n" + 
				"  \"name\" : {\r\n" + 
				"    \"first\" : \"Yong\",\r\n" + 
				"    \"last\" : \"Mook Kim\" \r\n" + 
				"  },\r\n" + 
				"  \"contact\" : [\r\n" + 
				"    { \"type\" : \"phone/home\", \"ref\" : \"111-111-1234\"},\r\n" + 
				"    { \"type\" : \"phone/work\", \"ref\" : \"222-222-2222\"}\r\n" + 
				"  ]\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"  \"id\"   : 2,\r\n" + 
				"  \"name\" : {\r\n" + 
				"    \"first\" : \"Yong\",\r\n" + 
				"    \"last\" : \"Zi Lap\" \r\n" + 
				"  },\r\n" + 
				"  \"contact\" : [\r\n" + 
				"    { \"type\" : \"phone/home\", \"ref\" : \"333-333-1234\"},\r\n" + 
				"    { \"type\" : \"phone/work\", \"ref\" : \"444-444-4444\"}\r\n" + 
				"  ]\r\n" + 
				"}\r\n" + 
				"]";

		return str;

	}

}
