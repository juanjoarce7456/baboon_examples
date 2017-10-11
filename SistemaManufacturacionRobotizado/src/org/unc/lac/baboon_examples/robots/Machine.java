package org.unc.lac.baboon_examples.robots;

import org.unc.lac.baboon.annotations.TaskController;

public class Machine extends Element {
	
	public Machine(String name){
		super(name);
	}
	
	@TaskController
	public void processPiece(Piece piece){
		System.out.println("Machine "+ name + " Processing: " + piece.getName());
	}
	
	

}
