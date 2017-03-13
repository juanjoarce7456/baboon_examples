package org.unc.lac.baboon_examples.lavado_botellas.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaCerveza;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaGaseosa;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaOtra;
import org.unc.lac.baboon_examples.lavado_botellas.maquina.MaquinaLavadora;

public class View {
	private JFrame frame;
	private final int windowWidth = 640, windowHeight = 240;

	JButton botonCerveza;
	JButton botonGaseosa;
	JButton botonOtra;
	MaquinaLavadora maquina;

	public View(MaquinaLavadora maquina) {
		this.maquina = maquina;
		frame = new JFrame("Lavado Botellas");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth, windowHeight);
		frame.setResizable(false);
		frame.setVisible(true);
		botonCerveza = new JButton("Insertar Cerveza");
		botonGaseosa = new JButton("Insertar Gaseosa");
		botonOtra = new JButton("Insertar Otra");
		botonCerveza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				maquina.recibirBotella(new BotellaCerveza());
			}

		});
		botonGaseosa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				maquina.recibirBotella(new BotellaGaseosa());
			}

		});
		botonOtra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				maquina.recibirBotella(new BotellaOtra());
			}

		});
		frame.getContentPane().add(botonOtra);
		frame.getContentPane().add(botonCerveza);
		frame.getContentPane().add(botonGaseosa);
		botonCerveza.setVisible(true);
		botonCerveza.setEnabled(true);
		botonGaseosa.setVisible(true);
		botonGaseosa.setEnabled(true);
		botonOtra.setVisible(true);
		botonOtra.setEnabled(true);
		botonCerveza.setBounds(frame.getWidth() / 4 - 100, frame.getHeight() / 2, 150, 20);
		botonGaseosa.setBounds(2 * frame.getWidth() / 4 - 100, frame.getHeight() / 2, 150, 20);
		botonOtra.setBounds(3 * frame.getWidth() / 4 - 100,frame.getHeight() / 2, 150, 20);
	}
}
