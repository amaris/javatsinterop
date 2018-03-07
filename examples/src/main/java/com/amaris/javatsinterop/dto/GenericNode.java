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
package com.amaris.javatsinterop.dto;

import jsweet.lang.Interface;

/**
 * This DTO represents generic tree node. It can be used as an object to
 * transfer tree structures in REST APIs (typically, to be serialized in JSON).
 * 
 * @author Renaud Pawlak
 */
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
