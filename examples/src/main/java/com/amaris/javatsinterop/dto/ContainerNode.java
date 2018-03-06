package com.amaris.javatsinterop.dto;

import jsweet.lang.Interface;

@Interface
public class ContainerNode extends Node {

	public Node[] children;
	
	public ContainerNode() {}
	
	public ContainerNode(String name, Node[] children) {
		super(name);
		this.children = children;
	}
	
}
