package org.unc.lac.baboon_examples.lavado_botellas.botella;

import java.awt.Image;
import java.awt.Point;

public abstract class Botella {
	TipoBotella tipo;
	Image img;
	Point position = new Point(-35,15);
	Point futurePosition = new Point();
	
	public Botella (TipoBotella tipo){
		this.tipo = tipo;	
	}

	public TipoBotella getTipo() {
		return tipo;
	}
	
	public void setPosition(int x, int y){
		position.setLocation(x, y);
	}
	
	public void setFuturePosition(int x, int y){
		futurePosition.setLocation(x, y);
	}
	
	public void move(){
		while(!isIdle()){
			if(position.x<futurePosition.x){
				position.x+=5;
			}
			else if(position.x>futurePosition.x){
				position.x-=5;
			}
			else if(position.y<futurePosition.y){
				position.y+=5;
			}
			else if(position.y>futurePosition.y){
				position.y-=5;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isIdle(){
		return position.equals(futurePosition);
	}
	
	public void setImage(Image img){
		this.img=img;
	}
	
	public Image getImage(){
		return img;
	}
	
	public Point getPosition(){
		return position;
	}
}
