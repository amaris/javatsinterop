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

@Interface
public class ContainerNode extends Node {

	public Node[] children;
	
	public ContainerNode() {}
	
	public ContainerNode(String name, Node[] children) {
		super(name);
		this.children = children;
	}
	
}
