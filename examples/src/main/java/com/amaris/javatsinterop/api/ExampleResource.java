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

	private Data data = new Data();

	@GET
	@Path("tree")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericNode tree() {
		return new GenericNode(1, "Salaries", new GenericNode[] { //
				new GenericNode(11, "Professor", new GenericNode[] { //
						new GenericNode(111, "Male", salariesToNodes("Prof", "Male")), //
						new GenericNode(112, "Female", salariesToNodes("Prof", "Female")) }), //
				new GenericNode(12, "Associate Professor", new GenericNode[] { //
						new GenericNode(121, "Male", salariesToNodes("AssocProf", "Male")), //
						new GenericNode(122, "Female", salariesToNodes("AssocProf", "Female")) }), //
				new GenericNode(12, "Assistant Professor", new GenericNode[] { //
						new GenericNode(121, "Male", salariesToNodes("AsstProf", "Male")), //
						new GenericNode(122, "Female", salariesToNodes("AsstProf", "Female")) //
				}) });
	}

	private GenericNode[] salariesToNodes(String rank, String gender) {
		return data.filterSalaries(rank, gender).stream().map(s -> new GenericNode(1, "" + s.salary, s.salary))
				.toArray(size -> new GenericNode[size]);
	}

	@GET
	@Path("salaries")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Salary> salaries() {
		return data.getSalaries();
	}

}