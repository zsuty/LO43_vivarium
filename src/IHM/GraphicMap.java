package IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objets.Antilope;
import objets.Eau;
import objets.Lion;
import objets.Nourriture;
import objets.Objet;
import objets.Rocher;

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
	
	/* ---- image ---- */
	private BufferedImage LionImage;
	private BufferedImage AntilopeImage;
	private BufferedImage RocherImage;
	private BufferedImage Herbe;
	private BufferedImage Viande;
	
	public GraphicMap(Map map){
		super();
		this.RectSize = 60;
		this.map = map;
		this.setVariable();
		
		/* initialisation des images */
		
		try {
			this.LionImage = ImageIO.read(new File("Image/Lion_M.png"));
		} catch (IOException e) {
		}
		
		try {
			this.RocherImage = ImageIO.read(new File("Image/Rocher.png"));
		} catch (IOException e) {
		}
		
		try {
			this.AntilopeImage =  ImageIO.read(new File("Image/Antilope.png"));
		} catch (IOException e) {
		}
		try {
			this.Herbe =  ImageIO.read(new File("Image/Herbe.png"));
		} catch (IOException e) {
		}
		try {
			this.Viande =  ImageIO.read(new File("Image/Viande.png"));
		} catch (IOException e) {
		}
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
		BufferedImage img;
		margeX = (this.getWidth() - (nbCaseX*this.RectSize))/2;
		margeY = (this.getHeight() - (nbCaseY*this.RectSize))/2;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getWidth());
		
		
		for(i = 0; i < nbCaseX ; ++i){
			x = i*this.RectSize + margeX;
			for(j = 0; j < nbCaseY ; ++j){
				y = j*this.RectSize + margeY;
				if(i < this.map.getNbX() - this.Istart && j < this.map.getNbY() - this.Jstart){
					g.setColor(getColor(this.map.getCases()[i + Istart][j + Jstart].getBiome()));
					g.fillRect(x, y, this.RectSize, this.RectSize);
					for(Objet o : this.map.getCases()[i + Istart][j + Jstart].getObjetsPresents()){
						img = this.getImageObjet(o);
						if(img != null && this.RectSize >= 40){
							g.drawImage(img, x, y, this.RectSize, this.RectSize, null);
						}
						else {
							g.setColor(this.getColorObjet(o));
							g.fillRect( x, y,this.RectSize,this.RectSize);
						}
					}
				}
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
	protected Color getColorObjet(Objet o){
		if(o instanceof Lion){
			return Color.red;
		}
		if(o instanceof Eau){
			if (o.isFranchissable()){
				return new Color(6,184,195);
			}
			return Color.blue;
		}
		if(o instanceof Rocher){
			return new Color(100,100,100);
		}
		if(o instanceof Antilope){
			return Color.green;
		}
		return null;
	}
	protected BufferedImage getImageObjet(Objet o){
		if(o instanceof Lion){
			return this.LionImage;
		}
		if(o instanceof Rocher){
			return this.RocherImage;
		}
		if(o instanceof Antilope){
			return this.AntilopeImage;
		}
		if(o instanceof Nourriture){
			Nourriture n = (Nourriture) o;
			if(n.isViande()){
				return this.Viande;
			}
			else{
				return this.Herbe;
			}
		}
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
	
	public void setxStart(int xStart) {
		if(xStart >= 0 ){
			this.xStart = xStart;
		}
		else{
			this.xStart = 0;
		}
	}
	public void setyStart(int yStart) {
		if (yStart >= 0){
			this.yStart = yStart;
		}
		else{
			this.yStart = 0;
		}
	}
}
