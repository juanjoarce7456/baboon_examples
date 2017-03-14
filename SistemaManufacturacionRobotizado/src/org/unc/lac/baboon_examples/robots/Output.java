package org.unc.lac.baboon_examples.robots;

import rx.Observer;

public class Output extends Element implements Observer<String> {

	private static int count=0;
	
	public Output(String name) {
		super(name);
	}

	@Override
	public void onCompleted() {
		System.out.printf("OUTPUT %s done\n", name);
		
	}

	@Override
	public void onError(Throwable arg0) {
		
	}

	@Override
	public void onNext(String arg0) {
		count++;
		System.out.println("---------------------------------------------------");
		System.out.println("The Output " + name + " Has "+ count + "Pieces Now");
		System.out.println("---------------------------------------------------");
		
	}
	

}
