package org.unc.lac.baboon_examples.robots;

import org.unc.lac.baboon.annotations.TaskController;

public class Robot extends Element {

	public Robot(String name){
		super(name);
	}
	
	@TaskController
	public void movePiece(Element from, Element to){
		System.out.println("Robot "+ name + " moving from : " + from.getName() + " To: " + to.getName() );
	}
}
