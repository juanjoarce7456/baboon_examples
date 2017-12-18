package org.unc.lac.baboon_examples.lavado_botellas.maquina;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import org.unc.lac.baboon.annotations.GuardProvider;
import org.unc.lac.baboon.annotations.HappeningController;
import org.unc.lac.baboon.annotations.TaskController;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Botella;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaCerveza;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaGaseosa;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaOtra;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Enjuagable;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Expulsable;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Lavable;
import org.unc.lac.baboon_examples.lavado_botellas.botella.TipoBotella;
import org.unc.lac.baboon_examples.lavado_botellas.view.GuiObserver;
import org.unc.lac.baboon_examples.lavado_botellas.view.GuiSubject;

public class MaquinaLavadora implements GuiSubject {
	
	private ArrayList<GuiObserver> observers = new ArrayList<GuiObserver>();
	public LinkedBlockingQueue<Botella> botellasInsertadas = new LinkedBlockingQueue<Botella>();
	public LinkedBlockingQueue<Botella> botellasLimpias = new LinkedBlockingQueue<Botella>();
	public LinkedBlockingQueue<BotellaCerveza> botellasCervezaClasificadas = new LinkedBlockingQueue<BotellaCerveza>();
	public LinkedBlockingQueue<BotellaCerveza> botellasCervezaLavadas = new LinkedBlockingQueue<BotellaCerveza>();
	public LinkedBlockingQueue<BotellaCerveza> botellasCervezaEnjuagadas = new LinkedBlockingQueue<BotellaCerveza>();
	public LinkedBlockingQueue<BotellaGaseosa> botellasGaseosaClasificadas = new LinkedBlockingQueue<BotellaGaseosa>();
	public LinkedBlockingQueue<BotellaGaseosa> botellasGaseosaLavadas = new LinkedBlockingQueue<BotellaGaseosa>();
	public LinkedBlockingQueue<BotellaOtra> botellasOtraClasificadas = new LinkedBlockingQueue<BotellaOtra>();
	private TipoBotella ultimoTipoInsertado;
	
	@TaskController
	public void lavarCerveza(){
		BotellaCerveza botella = botellasCervezaClasificadas.poll();
		botella.setFuturePosition(565, 315);
		botella.move();
		lavar(botella);
		botella.setFuturePosition(565, 415);
		botella.move();
		botellasCervezaLavadas.add(botella);
	}
	
	@TaskController
	public void lavarGaseosa(){
		BotellaGaseosa botella = botellasGaseosaClasificadas.poll();
		botella.setFuturePosition(265, 315);
		botella.move();
		lavar(botella);
		botella.setFuturePosition(265, 415);
		botella.move();
		botellasGaseosaLavadas.add(botella);
	}
	
	@TaskController
	public void enjuagarCerveza(){
		BotellaCerveza botella = botellasCervezaLavadas.poll();
		botella.setFuturePosition(565, 465);
		botella.move();
		enjuagar(botella);
		botella.setFuturePosition(565, 515);
		botella.move();
		botellasCervezaEnjuagadas.add(botella);
	}
	
	@TaskController
	public void secarCerveza(){
		BotellaCerveza botella = botellasCervezaEnjuagadas.poll();
		botella.setFuturePosition(565, 615);
		botella.move();
		secar(botella);
		botella.setFuturePosition(775, 615);
		botella.move();
		botellasLimpias.add(botella);
	}
	
	@TaskController
	public void secarGaseosa(){
		BotellaGaseosa botella = botellasGaseosaLavadas.poll();
		botella.setFuturePosition(265, 615);
		botella.move();
		secar(botella);
		botella.setFuturePosition(775, 615);
		botella.move();
		botellasLimpias.add(botella);
	}
	
	@TaskController
	public void expulsarOtra(){
		BotellaOtra botella = botellasOtraClasificadas.poll();
		botella.setFuturePosition(815, 465);
		botella.move();
		expulsar(botella);
		botella.setFuturePosition(1330, 465);
		botella.move();
	}
	
	@TaskController
	public void devolverBotellaLimpia(){
		Botella botella = botellasLimpias.poll();
		botella.setFuturePosition(1330, 615);
		botella.move();
		notifyFinished(botella);
	}
	
	private void lavar(Lavable lavable) {
		lavable.lavar();
	}
	
	private void enjuagar(Enjuagable enjuagable) {
		enjuagable.enjuagar();
	}
	
	private void secar(Lavable lavable) {
		lavable.secar();
	}
	
	private void expulsar(Expulsable expulsable){
		expulsable.expulsar();
	}
	
	@HappeningController
	public void recibirBotella(Botella botella){
		botella.setPosition(-35-50*botellasInsertadas.size(), 15);
		botella.setFuturePosition(1025-50*botellasInsertadas.size(), 15);
		botellasInsertadas.add(botella);
		System.out.println("Recibi nueva botella : " + botella.getTipo().name());
		notifyInserted(botella);
		botella.move();
	}
	
	@TaskController
	public void clasificarBotella(){
		ultimoTipoInsertado = botellasInsertadas.peek().getTipo();
		Botella ultimaInsertada = botellasInsertadas.poll();
		ultimaInsertada.setFuturePosition(1025, 115);
		ultimaInsertada.move();
		int index=0;
		for (Botella b : botellasInsertadas){
			b.setFuturePosition(1025-50*index, 15);
			b.move();
			index++;
		}
		switch(ultimoTipoInsertado){
		case CERVEZA:
			botellasCervezaClasificadas.add((BotellaCerveza) ultimaInsertada);
			ultimaInsertada.setFuturePosition(565, 215);
			break;
		case GASEOSA: 
			botellasGaseosaClasificadas.add((BotellaGaseosa) ultimaInsertada);
			ultimaInsertada.setFuturePosition(265, 215);
			break;
		default:    
			botellasOtraClasificadas.add((BotellaOtra) ultimaInsertada);
			ultimaInsertada.setFuturePosition(815, 215);
			break;
		}
		ultimaInsertada.move();
	}
	
	@GuardProvider("guardOtra")
	public boolean isOtra(){
		return ultimoTipoInsertado.equals(TipoBotella.OTRA);
	}
	
	@GuardProvider("guardCerveza")
	public boolean isCerveza(){
		return ultimoTipoInsertado.equals(TipoBotella.CERVEZA);
	}
	
	@GuardProvider("guardGaseosa")
	public boolean isGaseosa(){
		return ultimoTipoInsertado.equals(TipoBotella.GASEOSA);
	}

	@Override
	public void subscribe(GuiObserver gO) {
		observers.add(gO);
	}

	@Override
	public void notifyInserted(Botella bot) {
		for(GuiObserver o : observers){
			o.updateInserted(bot);
		}
		
	}

	@Override
	public void notifyFinished(Botella bot) {
		for(GuiObserver o : observers){
			o.updateFinished(bot);
		}
		
	}
}
