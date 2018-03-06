package com.amaris.javatsinterop.dto;

import jsweet.lang.Interface;

@Interface
public class LeafNode extends Node {

	public double weight;
	
	public LeafNode() {}
	
	public LeafNode(String name, double weight) {
		super(name);
		this.weight = weight;
	}
	
}
