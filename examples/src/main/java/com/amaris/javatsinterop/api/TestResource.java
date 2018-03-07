/* 
 * javatsinterop-examples by Amaris <rpawlak@amaris.com>
 *
 * To the extent possible under law, the person who associated CC0 with
 * javatsinterop-examples has waived all copyright and related or neighboring rights
 * to javatsinterop-examples.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package com.amaris.javatsinterop.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.amaris.javatsinterop.dto.GenericNode;

/**
 * This is a simple REST API to be used as an example.
 * 
 * @author Renaud Pawlak
 */
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