package org.unc.lac.baboon_examples.lavado_botellas.maquina;
import org.unc.lac.baboon.annotations.GuardProvider;
import org.unc.lac.baboon.annotations.HappeningHandler;
import org.unc.lac.baboon.annotations.Task;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Botella;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Enjuagable;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Expulsable;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Lavable;
import org.unc.lac.baboon_examples.lavado_botellas.botella.TipoBotella;

public class MaquinaLavadora {
	
	TipoBotella tipoUltimaInsercion;
	
	@Task
	public void lavar(Lavable lavable) {
		lavable.lavar();
	}
	
	@Task
	public void enjuagar(Enjuagable enjuagable) {
		enjuagable.enjuagar();
	}
	
	@Task
	public void secar(Lavable lavable) {
		lavable.secar();
	}
	
	@Task
	public void expulsar(Expulsable expulsable){
		expulsable.expulsar();
	}
	
	@HappeningHandler
	public void recibirBotella(Botella botella){
		System.out.println("Recibi nueva botella : " + botella.getTipo().name());
		tipoUltimaInsercion = botella.getTipo();
		
	}
	
	@Task
	public void clasificarBotella(){
		System.out.println("Clasificando botella");
	}
	
	@GuardProvider("isOtra")
	public boolean isOtra(){
		return tipoUltimaInsercion.equals(TipoBotella.OTRA);
	}
	
	@GuardProvider("isCerveza")
	public boolean isCerveza(){
		return tipoUltimaInsercion.equals(TipoBotella.CERVEZA);
	}
	
	@GuardProvider("isGaseosa")
	public boolean isGaseosa(){
		return tipoUltimaInsercion.equals(TipoBotella.GASEOSA);
	}
	
}
