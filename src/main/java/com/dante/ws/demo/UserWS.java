package com.dante.ws.demo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.PathVariable;

@Path("/test")
public class UserWS {

	/**
	 * Text
	 */
	@GET
	@Path("/string")
	@Produces({ MediaType.TEXT_PLAIN })
	public String testService() {
		System.out.println("My first WS, config at RestConfig, hava fun!");
		return "This is Dante's ws";
	}

	@GET
	@Path("/xml")
	@Produces({ MediaType.APPLICATION_XML })
	public User getUserByXml() {
		return new User(1, "Irelia", "Warrior");

	}

	@GET
	@Path("/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public User getUserByJson() {
		return new User(1, "Irelia", "Warrior");
	}

	@POST
	@Path("/postMethod")
	@Produces({ MediaType.APPLICATION_JSON })
	public User getUserWithPost(@PathVariable String username) {
		System.out.println(username);
		return new User(1, "Irelia", "Warrior");
	}

//	@POST
//	@Path("/postMethod22")
	// @Consumes(MediaType.TEXT_XML)
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response postStrMsg(String msg) {
		String output = "POST:Jersey say : " + msg;
		return Response.status(200).entity(output).build();
	}
}
