package org.unc.lac.baboon_examples.lavado_botellas.botella;

public class BotellaGaseosa extends Botella implements Lavable {
	public BotellaGaseosa() {
		super(TipoBotella.GASEOSA);
	}

	@Override
	public void lavar() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Lavando " + tipo.name());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Fin Lavado " + tipo.name());
	}

	@Override
	public void secar() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Secando " + tipo.name());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Fin Secado " + tipo.name());
	}
}
