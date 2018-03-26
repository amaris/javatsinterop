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
 * This DTO represents a generic tree node. It can be used as an object to
 * transfer tree structures in REST APIs (typically, to be serialized in JSON).
 * 
 * @author Renaud Pawlak
 */
@Interface
public class GenericNode {

	public String name;
	public double weight;
	public GenericNode[] children;
	public String uid;

	public GenericNode() {
	}

	public GenericNode(String name, double weight) {
		super();
		this.name = name;
		this.weight = weight;
	}

	public GenericNode(String name, GenericNode[] children) {
		super();
		this.name = name;
		this.children = children;
	}

}
