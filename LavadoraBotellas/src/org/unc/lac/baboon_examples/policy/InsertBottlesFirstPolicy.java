package org.unc.lac.baboon_examples.policy;

import org.unc.lac.javapetriconcurrencymonitor.monitor.policies.TransitionsPolicy;
import org.unc.lac.javapetriconcurrencymonitor.petrinets.PetriNet;
import org.unc.lac.javapetriconcurrencymonitor.petrinets.components.Place;

public class InsertBottlesFirstPolicy extends TransitionsPolicy{

	public InsertBottlesFirstPolicy(PetriNet _petri) {
		super(_petri);
	}

	@Override
	public int which(boolean[] enabled) {
		Integer[] marking  = petri.getCurrentMarking();
		Place [] places = petri.getPlaces();
		for (int i=0 ; i <marking.length ; i++ ){
			System.out.println(places[i].getName() + ": " + marking[i]);
		}
		
		int index = petri.getTransition("recibirBotella").getIndex();
		if(enabled[index]){ //Si recibirBotella está habilitada.
			System.out.println(petri.getTransitions()[index].getName());
			System.out.println("------------------------------------------");
			return index; //Disparar recibirBotella.
		}
		else {
			for(int i = 0; i < enabled.length; i++){
				if(enabled[i]){
					System.out.println(petri.getTransitions()[i].getName());
					System.out.println("------------------------------------------");
					return i; //Si no, disparar la primer transición habilitada que encuentre.
				}
			}
		}
		return -1;
	}

}
