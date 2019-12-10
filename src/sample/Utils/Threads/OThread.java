/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sample.Utils.Threads;

public class OThread extends Thread {
	public OThread() {
	}

	public OThread(Runnable target) {
		super(target);
	}

	public OThread(ThreadGroup group, Runnable target) {
		super(group, target);
	}

	public OThread(Runnable target, String name) {
		super(target, name);
	}

	public OThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
	}

	public OThread(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
	}


}
