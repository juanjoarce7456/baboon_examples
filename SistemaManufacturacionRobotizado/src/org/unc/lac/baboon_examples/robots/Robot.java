package org.unc.lac.baboon_examples.robots;

import org.unc.lac.baboon.annotations.Task;

public class Robot extends Element {

	public Robot(String name){
		super(name);
	}
	
	@Task
	public void movePiece(Element from, Element to){
		System.out.println("Robot "+ name + " moving from : " + from.getName() + " To: " + to.getName() );
	}
}
