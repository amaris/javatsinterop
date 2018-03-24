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
package com.amaris.javatsinterop.dto;

import jsweet.lang.Interface;

/**
 * This DTO represents a professor salary.
 * 
 * @author Renaud Pawlak
 */
@Interface
public class Salary {

	public int id;
	public String rank;
	public String discipline;
	public int yearsSince;
	public int yearsService;
	public String sex;
	public int salary;
	
	public Salary(int id, String rank, String discipline, int yearsSince, int yearsService, String sex, int salary) {
		super();
		this.id = id;
		this.rank = rank;
		this.discipline = discipline;
		this.yearsSince = yearsSince;
		this.yearsService = yearsService;
		this.sex = sex;
		this.salary = salary;
	}
	

}
