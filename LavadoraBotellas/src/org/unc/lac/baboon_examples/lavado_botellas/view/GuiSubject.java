package org.unc.lac.baboon_examples.lavado_botellas.view;
import org.unc.lac.baboon_examples.lavado_botellas.botella.Botella;

public interface GuiSubject {
	public void subscribe(GuiObserver gO);
	public void notifyInserted(Botella bot);
	public void notifyFinished(Botella bot);
}
