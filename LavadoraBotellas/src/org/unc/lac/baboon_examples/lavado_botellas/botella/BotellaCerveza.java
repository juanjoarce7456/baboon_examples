package org.unc.lac.baboon_examples.lavado_botellas.botella;

public class BotellaCerveza extends Botella implements Lavable, Enjuagable {

	public BotellaCerveza() {
		super(TipoBotella.CERVEZA);
	}

	@Override
	public void lavar() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Lavando " + tipo.name());
		try {
			Thread.sleep(1300);
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
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Fin Secado " + tipo.name());
	}

	@Override
	public void enjuagar() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Enjuagando " + tipo.name());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Fin Enjuagado " + tipo.name());
		
	}

}
