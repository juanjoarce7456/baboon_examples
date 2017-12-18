package org.unc.lac.baboon_examples.robots;

import org.unc.lac.baboon.annotations.TaskController;

public class Robot extends Element {

	public Robot(String name){
		super(name);
	}
	
	@TaskController
	public void movePiece(Element from, Element to) throws InterruptedException {
		System.out.printf("Robot %s moving piece from %s to %s \n", name, from.getName(), to.getName());
		Thread.sleep(750);
		System.out.println("Robot moved successfully");
	}
}
