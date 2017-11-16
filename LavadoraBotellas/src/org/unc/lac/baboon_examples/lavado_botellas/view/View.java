package org.unc.lac.baboon_examples.lavado_botellas.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.imageio.ImageIO;

import javax.swing.JFrame;

import org.unc.lac.baboon_examples.lavado_botellas.botella.Botella;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaCerveza;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaGaseosa;
import org.unc.lac.baboon_examples.lavado_botellas.botella.BotellaOtra;
import org.unc.lac.baboon_examples.lavado_botellas.maquina.MaquinaLavadora;

public class View implements GuiObserver{
	private ArrayList<Botella> botellas = new ArrayList<Botella>();
	private JFrame frame;
	private final String imgDir= "imagenes/";
	private final int windowWidth = 1280, windowHeight = 720;
	private Image fondo = cargarImg("fondo.png");
	Rectangle botonCerveza;
	Rectangle botonGaseosa;
	Rectangle botonOtra;
	MaquinaLavadora maquina;

	public View(MaquinaLavadora maquina) {
		this.maquina = maquina;
		maquina.subscribe(this);
		frame = new JFrame("Lavado Botellas");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth, windowHeight);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.createBufferStrategy(2);
		botonCerveza = new Rectangle(1000,325,150,30);
		botonGaseosa = new Rectangle(1000,275,150,30);
		botonOtra = new Rectangle(1000,375,150,30);
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Thread( new Runnable() {
					@Override
					public void run() {
						if(botonCerveza.contains(arg0.getPoint())){
							maquina.recibirBotella(new BotellaCerveza());
						}
						else if(botonGaseosa.contains(arg0.getPoint())){
							maquina.recibirBotella(new BotellaGaseosa());
						}
						else if(botonOtra.contains(arg0.getPoint())){
							maquina.recibirBotella(new BotellaOtra());
						}
					}
				}).start();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
		});
		this.drawScreen();
	}
	
	public void drawScreen(){
		BufferStrategy bf = frame.getBufferStrategy();
		
		Graphics g = null;
		try {
			g = bf.getDrawGraphics();
			
	        frame.paint(g);
			//g.clearRect(x-MAX_OFFSET_IMG, y-MAX_OFFSET_IMG, s.getWidth()+2*MAX_OFFSET_IMG, s.getHeight()+2*MAX_OFFSET_IMG);
			// It is assumed that mySprite is created somewhere else.
			// This is just an example for passing off the Graphics object.
	        g.drawImage(fondo,0,0,null);
	        try{
				for (Botella b: botellas){
						g.drawImage(b.getImage(),b.getPosition().x,b.getPosition().y,null);
				}
			}
			catch(ConcurrentModificationException e){
					e.printStackTrace();
			}
//			drawColliders(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setFont(new Font("Arial",Font.PLAIN,20));
	        g2d.drawString("Time: ", 100,100);
	        
		} finally {
			// It is best to dispose() a Graphics object when done with it.
			g.dispose();

		}
	 
		// Shows the contents of the backbuffer on the screen.
		bf.show();
	 
	        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until 
	        //Drawing is done which looks very jerky
	        Toolkit.getDefaultToolkit().sync();	
	}
	
	private Image cargarImg(String ref){
		Image sourceImage= null;
			// The ClassLoader.getResource() ensures we get the sprite
			// from the appropriate place, this helps with deploying the game
			// with things like webstart. You could equally do a file look
			// up here.
				try {
					URL url = this.getClass().getClassLoader().getResource(imgDir+ ref);
					if (url == null) {
						//System.out.println("Can't find ref: "+ref);
					}
					// use ImageIO to read the image in
					sourceImage= ImageIO.read(url);
				} catch (IOException e) {
					e.printStackTrace();
				}		
		return  sourceImage;
	}

	@Override
	public void updateInserted(Botella bot) {
		bot.setImage(cargarImg(bot.getTipo().toString().toLowerCase()+".png"));
		botellas.add(bot);
		//System.out.println("Botella  en vista: " + bot.getTipo().name());
		//System.out.println("Cantidad Botellas: " + botellas.size());
	}

	@Override
	public void updateFinished(Botella bot) {
		botellas.remove(bot);
		
	}
	


	
}
