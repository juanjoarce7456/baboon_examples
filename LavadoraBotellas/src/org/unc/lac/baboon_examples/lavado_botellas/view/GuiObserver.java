package org.unc.lac.baboon_examples.lavado_botellas.view;

import org.unc.lac.baboon_examples.lavado_botellas.botella.Botella;

public interface GuiObserver {
	public void updateInserted(Botella bot);
	public void updateFinished(Botella bot);
}
