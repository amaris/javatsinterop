package com.amaris.javatsinterop.data;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.amaris.javatsinterop.dto.Salary;
import com.opencsv.CSVReader;

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

	public List<Salary> getSalaries() {
		return salaries;
	}


}
