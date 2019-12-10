/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sample.Utils.Threads;

public class OThreadGroup extends ThreadGroup {

	public OThreadGroup(String name) {
		super(name);
	}

	public OThreadGroup(ThreadGroup parent, String name) {
		super(parent, name);
	}
}
