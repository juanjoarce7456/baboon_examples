package org.unc.lac.baboon_examples.robots;

import org.unc.lac.baboon.annotations.TaskController;

public class Machine extends Element {
	
	public Machine(String name){
		super(name);
	}
	
	@TaskController
	public void processPiece(Piece piece) throws InterruptedException {
		System.out.printf("Machine %s Processing: %s\n", name, piece.getName());
		Thread.sleep(1000);
		System.out.printf("Piece %s successfully processed\n", piece.getName());
	}



}
