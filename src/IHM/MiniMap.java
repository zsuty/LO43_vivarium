package IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import objets.Objet;

public class MiniMap extends JPanel implements MouseListener{
	private GraphicMap gMap;
	private int rectSize;
	private int margeX;
	private int margeY;
	private int xVue;
	private int yVue;
	private int xVueTaille;
	private int yVueTaille;
	
	
	public MiniMap(GraphicMap gm){
		super();
		this.gMap = gm;
		this.addMouseListener(this);
	}
	public void SetVariable(){
		rectSize = Math.min((this.getWidth()/(this.gMap.getMap().getNbX())),(this.getHeight()/(this.gMap.getMap().getNbY())));
		margeX =(this.getWidth() - (rectSize * this.gMap.getMap().getNbX()))/2;
		margeY = (this.getHeight() - (rectSize * this.gMap.getMap().getNbY()))/2;
		xVue = this.gMap.getIstart() * rectSize + margeX;
		yVue = this.gMap.getJstart() * rectSize + margeY;
		xVueTaille = this.gMap.getNbCaseX() * rectSize;
		yVueTaille = this.gMap.getNbCaseY() * rectSize;
	}
	public void paintComponent(Graphics g){
		
		int i,j;
		g.setColor(new Color(101,77,48));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(i = 0; i < this.gMap.getMap().getNbX(); ++i){
			for(j = 0; j < this.gMap.getMap().getNbY(); ++j){
				g.setColor(this.gMap.getColor(this.gMap.getMap().getCases()[i][j].getBiome()));
				g.fillRect((i*rectSize) + margeX, (j*rectSize) + margeY, rectSize, rectSize);
				for(Objet o : this.gMap.getMap().getCases()[i][j].getObjetsPresents()){
					g.setColor(this.gMap.getColorObjet(o));
					g.fillRect( i * this.rectSize + this.margeX, j * this.rectSize + this.margeY,this.rectSize,this.rectSize);
				}
			}
		}
		g.setColor(Color.black);
		g.drawRect(this.xVue, this.yVue , this.xVueTaille ,this.yVueTaille );
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		this.gMap.setxStart(((e.getX() - this.margeX)/this.rectSize)  - (this.xVueTaille/(2*this.rectSize) ));
		this.gMap.setyStart(((e.getY() - this.margeY)/this.rectSize) - (this.yVueTaille/(2*this.rectSize) ));
		
	}
	
}