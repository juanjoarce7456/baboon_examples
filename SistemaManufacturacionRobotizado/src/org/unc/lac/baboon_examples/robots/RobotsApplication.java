package org.unc.lac.baboon_examples.robots;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unc.lac.baboon.exceptions.BadPolicyException;
import org.unc.lac.baboon.exceptions.BadTopicsJsonFormat;
import org.unc.lac.baboon.exceptions.NoTopicsJsonFileException;
import org.unc.lac.baboon.exceptions.NotSubscribableException;
import org.unc.lac.baboon.main.BaboonApplication;
import org.unc.lac.baboon.main.BaboonFramework;
import org.unc.lac.javapetriconcurrencymonitor.petrinets.factory.PetriNetFactory.petriNetType;

public class RobotsApplication implements BaboonApplication {
	Logger LOGGER = Logger.getLogger(RobotsApplication.class.getName());
	Machine m1 = new Machine("M1");
	Machine m2 = new Machine("M2");
	Machine m3 = new Machine("M3");
	Machine m4 = new Machine("M4");
	Robot r1 = new Robot("R1");
	Robot r2 = new Robot("R2");
	Robot r3 = new Robot("R3");
	Piece i1= new Piece("I1");
	Piece i2= new Piece("I2");
	Piece i3= new Piece("I3");
	Output o1 = new Output("O1");
	Output o2 = new Output("O2");
	Output o3 = new Output("O3");
	
	@Override
	public void declare() {
        try {
			BaboonFramework.createPetriCore("/pnml/RobotsPetriNet.pnml", petriNetType.PLACE_TRANSITION, null);
		} catch (BadPolicyException e) {
            LOGGER.log(Level.SEVERE, "Error configurando la política de disparos. La aplicación terminará ahora.", e);
            System.exit(1);
		}
        try {
            BaboonFramework.addTopicsFile("/topic/robotsTopic.json");
        } catch (BadTopicsJsonFormat | NoTopicsJsonFileException e) {
            LOGGER.log(Level.SEVERE, "Error inicializando Baboon Framework. La aplicación terminará ahora.", e);
            System.exit(1);
        }
	}

	@Override
	public void subscribe() {
		try {
			BaboonFramework.subscribeControllerToTopic("M4.processFromI3", m4, "processPiece", i3);
			BaboonFramework.subscribeControllerToTopic("M3.processFromI3", m3, "processPiece", i3);
			
			BaboonFramework.subscribeControllerToTopic("M3.processFromI1", m3, "processPiece", i1);
			BaboonFramework.subscribeControllerToTopic("M4.processFromI1", m4, "processPiece", i1);
			
			BaboonFramework.subscribeControllerToTopic("M1.processFromI1", m1, "processPiece", i1);
			BaboonFramework.subscribeControllerToTopic("M2.processFromI1", m2, "processPiece", i1);
			
			BaboonFramework.subscribeControllerToTopic("M2.processFromI2", m2, "processPiece", i2);
			
			BaboonFramework.subscribeControllerToTopic("R3.moveFromI3ToM4", r3, "movePiece", i3,m4);
			BaboonFramework.subscribeControllerToTopic("R2.moveFromM4ToM3", r2, "movePiece", m4,m3);
			BaboonFramework.subscribeControllerToTopic("R1.moveFromM3ToO3", r1, "movePiece", m3,o3);
			
			BaboonFramework.subscribeControllerToTopic("R1.moveFromI1ToM3", r1, "movePiece", i1,m3);
			BaboonFramework.subscribeControllerToTopic("R2.moveFromM3ToM4", r2, "movePiece", m3,m4);
			BaboonFramework.subscribeControllerToTopic("R3.moveFromM4ToO1", r3, "movePiece", m4,o1);
			
			BaboonFramework.subscribeControllerToTopic("R1.moveFromI1ToM1", r1, "movePiece", i1,m1);
			BaboonFramework.subscribeControllerToTopic("R2.moveFromM1ToM2", r2, "movePiece", m1,m2);
			BaboonFramework.subscribeControllerToTopic("R3.moveFromM2ToO1", r3, "movePiece", m2,o1);
			
			BaboonFramework.subscribeControllerToTopic("R2.moveFromI2ToM2", r2, "movePiece", i2,m2);
			BaboonFramework.subscribeControllerToTopic("R2.moveFromM2ToO2", r2, "movePiece", m2,o2);
		} catch (NotSubscribableException e) {
            LOGGER.log(Level.SEVERE, "Error suscribiendo acciones a Baboon Framework. La aplicaci�n terminar� ahora.", e);
            System.exit(1);
		}
		
		BaboonFramework.listenToTransitionInforms("R3.moveFromM4ToO1Done", o1);
		BaboonFramework.listenToTransitionInforms("R3.moveFromM2ToO1Done", o1);
		BaboonFramework.listenToTransitionInforms("R2.moveFromM2ToO2Done", o2);
		BaboonFramework.listenToTransitionInforms("R1.moveFromM3ToO3Done", o3);
		
		
	}

}
