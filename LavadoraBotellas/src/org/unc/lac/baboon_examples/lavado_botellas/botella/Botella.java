package org.unc.lac.baboon_examples.lavado_botellas.botella;

public abstract class Botella {
	TipoBotella tipo;
	
	public Botella (TipoBotella tipo){
		this.tipo = tipo;	
	}

	public TipoBotella getTipo() {
		return tipo;
	}
}
