package com.amaris.javatsinterop.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.amaris.javatsinterop.dto.GenericNode;

@Path("home")
public class TestResource {
	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloWorld() {
		return "Hello, world!";
	}

	@GET
	@Path("tree")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericNode tree() {
		return new GenericNode(1, "test1", new GenericNode[] { //
				new GenericNode(2, "test11", new GenericNode[] { //
						new GenericNode(3, "test111", 4), //
						new GenericNode(4, "test112", 2), //
						new GenericNode(5, "test113", 3) }),
				new GenericNode(6, "test12", new GenericNode[] { //
						new GenericNode(7, "test121", 5), //
						new GenericNode(8, "test122", 1), //
						new GenericNode(9, "test123", 3) }) });
	}

}