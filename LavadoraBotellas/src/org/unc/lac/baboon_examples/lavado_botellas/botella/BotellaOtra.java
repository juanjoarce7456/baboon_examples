package org.unc.lac.baboon_examples.lavado_botellas.botella;

public class BotellaOtra extends Botella implements Expulsable {

	public BotellaOtra() {
		super(TipoBotella.OTRA);
		
	}

	@Override
	public void expulsar() {
		//System.out.println("-----------------------------------------------------------------------");
		//System.out.println("Expulsando " + tipo.name());
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Fin expulsion " + tipo.name());
	}

}
