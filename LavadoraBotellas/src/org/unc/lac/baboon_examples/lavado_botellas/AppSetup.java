package org.unc.lac.baboon_examples.lavado_botellas;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unc.lac.baboon.exceptions.BadTopicsJsonFormat;
import org.unc.lac.baboon.exceptions.NoTopicsJsonFileException;
import org.unc.lac.baboon.exceptions.NotSubscribableException;
import org.unc.lac.baboon.main.BaboonApplication;
import org.unc.lac.baboon.main.BaboonFramework;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaCerveza;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaGaseosa;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaOtra;
import org.unc.lac.baboon_examples.lavado_botellas.maquina.MaquinaLavadora;
import org.unc.lac.baboon_examples.lavado_botellas.view.View;
import org.unc.lac.javapetriconcurrencymonitor.petrinets.factory.PetriNetFactory.petriNetType;

public class AppSetup implements BaboonApplication {
	Logger LOGGER = Logger.getLogger(AppSetup.class.getName());
	MaquinaLavadora maquina;
	View vista;

	@Override
	public void declare() {
		maquina = new MaquinaLavadora();
		vista = new View(maquina);
        BaboonFramework.createPetriCore("pnml/lavadoBotellas.pnml", petriNetType.PLACE_TRANSITION, null);
        try {
            BaboonFramework.addTopicsFile("topic/topics.json");
        } catch (BadTopicsJsonFormat | NoTopicsJsonFileException e) {
            LOGGER.log(Level.SEVERE, "Error inicializando Baboon Framework. La aplicación terminará ahora.", e);
            System.exit(1);
        }
		
	}

	@Override
	public void subscribe() {
		try {
			//BotellaCerveza utilizada solo para definir el metodo correcto a utilizar en el HappeningHandler
			BaboonFramework.subscribeToTopic("recepcion_botella", maquina, "recibirBotella", new BotellaCerveza());
			BaboonFramework.subscribeToTopic("clasificacion_botella", maquina, "clasificarBotella");
			BaboonFramework.subscribeToTopic("expulsion_otra", maquina, "expulsar", new BotellaOtra());
			BaboonFramework.createNewComplexTask("lavarCerveza", "lavado_cerveza");
			BaboonFramework.createNewComplexTask("lavarGaseosa", "lavado_gaseosa");
			BaboonFramework.appendTaskToComplexTask("lavarCerveza", maquina, "lavar", new BotellaCerveza());
			BaboonFramework.appendTaskToComplexTask("lavarCerveza", maquina, "enjuagar", new BotellaCerveza());
			BaboonFramework.appendTaskToComplexTask("lavarCerveza", maquina, "secar", new BotellaCerveza());
			BaboonFramework.appendTaskToComplexTask("lavarGaseosa", maquina, "lavar", new BotellaGaseosa());
			BaboonFramework.appendTaskToComplexTask("lavarGaseosa", maquina, "secar", new BotellaGaseosa());
			
		} catch (NotSubscribableException e) {
            LOGGER.log(Level.SEVERE, "Error suscribiendo acciones a Baboon Framework. La aplicación terminará ahora.", e);
            System.exit(1);
		}
		
	}

}
