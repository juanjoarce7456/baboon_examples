package org.unc.lac.baboon.lavado_botellas;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unc.lac.baboon.exceptions.BadPolicyException;
import org.unc.lac.baboon.exceptions.BadTopicsJsonFormat;
import org.unc.lac.baboon.exceptions.NoTopicsJsonFileException;
import org.unc.lac.baboon.exceptions.NotSubscribableException;
import org.unc.lac.baboon.main.BaboonApplication;
import org.unc.lac.baboon.main.BaboonFramework;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaCerveza;
import org.unc.lac.baboon_examples.lavado_botellas.maquina.MaquinaLavadora;
import org.unc.lac.baboon_examples.lavado_botellas.view.View;
import org.unc.lac.baboon_examples.policy.InsertBottlesFirstPolicy;
import org.unc.lac.javapetriconcurrencymonitor.petrinets.factory.PetriNetFactory.petriNetType;

public class AppSetup implements BaboonApplication {
	private static Logger LOGGER = Logger.getLogger(AppSetup.class.getName());
	private MaquinaLavadora maquina;
	private View vista;

	@Override
	public void declare() {
		maquina = new MaquinaLavadora();
		vista = new View(maquina);
        try {
			BaboonFramework.createPetriCore("/pnml/lavadoBotellas.pnml", petriNetType.PLACE_TRANSITION, InsertBottlesFirstPolicy.class);
		} catch (BadPolicyException e) {
            LOGGER.log(Level.SEVERE, "Error configurando la política de disparos. La aplicación terminará ahora.", e);
            System.exit(1);
		}
        try {
            BaboonFramework.addTopicsFile("/topic/topics.json");
        } catch (BadTopicsJsonFormat | NoTopicsJsonFileException e) {
            LOGGER.log(Level.SEVERE, "Error inicializando Baboon Framework. La aplicación terminará ahora.", e);
            System.exit(1);
        }
	}

	@Override
	public void subscribe() {

		try {
			//BotellaCerveza utilizada solo para definir el metodo correcto a utilizar en el HappeningHandler
			BaboonFramework.subscribeControllerToTopic("recepcion_botella", maquina, "recibirBotella", new BotellaCerveza());
			BaboonFramework.subscribeControllerToTopic("clasificacion_botella", maquina, "clasificarBotella");
			BaboonFramework.subscribeControllerToTopic("expulsion_otra", maquina, "expulsarOtra");
			BaboonFramework.subscribeControllerToTopic("devolucion_botella", maquina, "devolverBotellaLimpia");
			BaboonFramework.createNewComplexTaskController("lavarCerveza", "lavado_cerveza");
			BaboonFramework.createNewComplexTaskController("lavarGaseosa", "lavado_gaseosa");
			BaboonFramework.appendControllerToComplexTaskController("lavarCerveza", maquina, "lavarCerveza");
			BaboonFramework.appendControllerToComplexTaskController("lavarCerveza", maquina, "enjuagarCerveza");
			BaboonFramework.appendControllerToComplexTaskController("lavarCerveza", maquina, "secarCerveza");
			BaboonFramework.appendControllerToComplexTaskController("lavarGaseosa", maquina, "lavarGaseosa");
			BaboonFramework.appendControllerToComplexTaskController("lavarGaseosa", maquina, "secarGaseosa");
			
		} catch (NotSubscribableException e) {
            LOGGER.log(Level.SEVERE, "Error suscribiendo acciones a Baboon Framework. La aplicación terminará ahora.", e);
            System.exit(1);
		}
		
        Thread viewThread = new Thread( new Runnable() {
        
			@Override
			public void run() {
	            while(true){
	            	vista.drawScreen();
		    		try {
		    			Thread.sleep(25);
		    		} catch (InterruptedException e) {
		    			LOGGER.log(Level.INFO, "InterruptedException en thread de GUI", e);
		    		}
	            }
			}
        });
        
       viewThread.start();
		
	}

}