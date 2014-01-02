package IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.io.ObjectInputStream.GetField;

import javax.swing.JPanel;

import objets.Lion;
import objets.Objet;

import map.Case;
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
	private int nbCaseX;
	private int nbCaseY;
	private int Istart;
	private int Jstart;
	private int margeX;
	private int margeY;
	
	public GraphicMap(Map map){
		super();
		this.RectSize = 15;
		this.map = map;
		this.setVariable();
	}
	public void setVariable(){
		nbCaseX = Math.min(this.getWidth()/this.RectSize,this.map.getNbX());
		nbCaseY = Math.min(this.getHeight()/this.RectSize, this.map.getNbY());
		
		Istart = this.xStart;
		Jstart = this.yStart;
		if(nbCaseX == this.map.getNbX()){
			Istart = 0;
		}
		if(nbCaseY == this.map.getNbY()){
			Jstart = 0;
		}
	}
	public void paintComponent(Graphics g){
		int i,j,x,y;
		
		margeX = (this.getWidth() - (nbCaseX*this.RectSize))/2;
		margeY = (this.getHeight() - (nbCaseY*this.RectSize))/2;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getWidth());
		
		
		for(i = 0; i < nbCaseX ; ++i){
			x = i*this.RectSize + margeX;
			for(j = 0; j < nbCaseY ; ++j){
				y = j*this.RectSize + margeY;
				g.setColor(getColor(this.map.getCases()[i + Istart][j + Jstart].getBiome()));
				g.fillRect(x, y, this.RectSize, this.RectSize);
				for(Objet o : this.map.getCases()[i + Istart][j + Jstart].getObjetsPresents()){
					g.setColor(Color.black);
					g.drawString(this.getImage(o), x, y + this.RectSize);
				}
				g.setColor(Color.black);
				g.drawRect(x, y, this.RectSize, this.RectSize);
			}
		}
		
		
	}
	protected Color getColor(Biome b){
		switch(b){
		case PLAINE:
			return new Color(224, 182, 54);
		case MONTAGNE:
			return new Color(143,74,18);
		case OCEAN:
			return new Color(29,34,234);
		case FORET:
			return new Color(27,124,0);
		default:
			break;
		}
		return Color.white;
	}
	protected String getImage(Objet o){
		if(o instanceof Lion){
			return new String("Li");
		}
		/*else if(o instanceof Rocher){
			return new String("Ro");
		}*/
		return null;
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
	public int getRectSize() {
		return RectSize;
	}
	public int getNbCaseX() {
		return nbCaseX;
	}
	public int getNbCaseY() {
		return nbCaseY;
	}
	public Map getMap(){
		return this.map;
	}
	public int getyStart() {
		return yStart;
	}
	public int getxStart() {
		return xStart;
	}
	public int getIstart() {
		return Istart;
	}
	public int getJstart() {
		return Jstart;
	}
	public int getMargeX() {
		return margeX;
	}
	public int getMargeY() {
		return margeY;
	}
}
