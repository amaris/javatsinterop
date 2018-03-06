package com.amaris.javatsinterop.dto;

import jsweet.lang.Interface;

@Interface
public class GenericNode {

	public String name;
	public double weight;
	public GenericNode[] children;
	public int test;

	public GenericNode() {
	}

	public GenericNode(int test, String name, double weight) {
		super();
		this.test = test;
		this.name = name;
		this.weight = weight;
	}

	public GenericNode(int test, String name, GenericNode[] children) {
		super();
		this.test = test;
		this.name = name;
		this.children = children;
	}

}
