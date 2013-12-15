package IHM;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import map.Map;
import map.Biome;

public class GraphicMap extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -513186854840568736L;
	private Map map;
	private int RectSize;
	private int xStart = 0;
	private int yStart = 0;
	
	public GraphicMap(Map map){
		super();
		this.RectSize = 15;
		this.map = map;
	}
	
	public void paintComponent(Graphics g){
		int i,j,x,y,nbCaseX,nbCaseY, margeX, margeY;
		nbCaseX = Math.min(this.getWidth()/this.RectSize,this.map.getNbColonnes());
		nbCaseY = Math.min(this.getHeight()/this.RectSize, this.map.getNbLignes());
		
		int Istart = this.yStart;
		int Jstart = this.xStart;
		if(nbCaseX == this.map.getNbColonnes()){
			Jstart = 0;
		}
		if(nbCaseY == this.map.getNbLignes()){
			Istart = 0;
		}
		margeX = (this.getWidth() - (nbCaseX*this.RectSize))/2;
		margeY = (this.getHeight() - (nbCaseY*this.RectSize))/2;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getWidth());
		
		
		for(i = 0; i < nbCaseY ; ++i){
			y = i*this.RectSize + margeY;
			for(j = 0; j < nbCaseX ; ++j){
				x = j*this.RectSize + margeX;
				g.setColor(getColor(this.map.getCases()[i + Istart][j + Jstart].getBiome()));
				g.fillRect(x, y, this.RectSize, this.RectSize);
				g.setColor(Color.black);
				g.drawRect(x, y, this.RectSize, this.RectSize);
			}
		}
		
		
	}
	private Color getColor(Biome b){
		switch(b){
		case PLAINE:
			return new Color(224, 182, 54);
		case MONTAGNE:
			return new Color(143,74,18);
		default:
			break;
		}
		return Color.white;
	}
	
	public void zoomPlus(){
		this.RectSize += 5;
	}
	public void zoomMoins(){
		if(this.RectSize > 5){
			this.RectSize -= 5;
		}
	}
	public void addXStart(int x){
		if(x >= -this.xStart){
			this.xStart += x;
		}
	}
	public void addYStart(int y){
		if(y >= -this.yStart){
			this.yStart += y;
		}
	}

}
