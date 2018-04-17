/* 
 * MIT License
 * 
 * Copyright (c) 2018 Amaris <rpawlak@amaris.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.amaris.javatsinterop.api;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.amaris.javatsinterop.data.Data;
import com.amaris.javatsinterop.dto.GenericNode;
import com.amaris.javatsinterop.dto.Salary;

/**
 * This is a simple REST API to be used as an example.
 * 
 * @author Renaud Pawlak
 */
@Path("example")
@Singleton
public class ExampleResource {

	private Data data = Data.getInstance();

	@GET
	@Path("tree")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericNode tree() {
		return new GenericNode("Salaries", new GenericNode[] { //
				new GenericNode("Professor", new GenericNode[] { //
						new GenericNode("Economics", salariesToNodes("Prof", "Economics")), //
						new GenericNode("Computer Science", salariesToNodes("Prof", "Computer Science")) }), //
				new GenericNode("Associate Professor", new GenericNode[] { //
						new GenericNode("Economics", salariesToNodes("AssocProf", "Economics")), //
						new GenericNode("Computer Science", salariesToNodes("AssocProf", "Computer Science")) }), //
				new GenericNode("Assistant Professor", new GenericNode[] { //
						new GenericNode("Economics", salariesToNodes("AsstProf", "Economics")), //
						new GenericNode("Computer Science", salariesToNodes("AsstProf", "Computer Science")) //
				}) });
	}

	private GenericNode[] salariesToNodes(String rank, String discipline) {
		return data.filterSalaries(rank, discipline).stream().map(s -> new GenericNode("" + s.salary, s.salary))
				.toArray(size -> new GenericNode[size]);
	}

	@GET
	@Path("salaries")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Salary> salaries(@QueryParam("rank") String rank, @QueryParam("discipline") String discipline) {
		System.out.println("salaries: " + rank + "," + discipline);
		return data.filterSalaries(rank, discipline);
	}

}