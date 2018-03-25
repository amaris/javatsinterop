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
package com.amaris.javatsinterop.data;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.amaris.javatsinterop.dto.Salary;
import com.opencsv.CSVReader;

/**
 * This class implements the data layer of this example.
 * 
 * @author Renaud Pawlak
 */
public class Data {

	private List<Salary> salaries;

	{
		if (salaries == null) {
			salaries = new ArrayList<>();
			File file = new File("salaries.csv");
			if (!file.exists()) {
				file = new File("examples/salaries.csv");
			}
			System.out.println("reading data from " + file);
			try (CSVReader reader = new CSVReader(new FileReader(file))) {
				String[] nextLine;
				// skipping headers
				reader.readNext();
				while ((nextLine = reader.readNext()) != null) {
					salaries.add(new Salary(NumberUtils.toInt(nextLine[0], 0), nextLine[1], nextLine[2],
							NumberUtils.toInt(nextLine[3], 0), NumberUtils.toInt(nextLine[4], 0), nextLine[5],
							NumberUtils.toInt(nextLine[6], 0)));
				}
			} catch (Exception e) {
				System.err.println("cannot read data");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns a list of salaries read from the salaries.csv file.
	 */
	public List<Salary> getSalaries() {
		return salaries;
	}

	/**
	 * Returns a list of salaries filtered according to the given criterias.
	 */
	public List<Salary> filterSalaries(String rank, String gender) {
		return salaries.stream().filter(s -> {
			return StringUtils.equals(rank, s.rank) && StringUtils.equals(gender, s.sex);
		}).collect(Collectors.toList());
	}

	
}
