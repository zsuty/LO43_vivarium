package IHM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import Action.Action;
import Action.Ajouter;
import Action.ModifierBiome;
import Action.Suprimer;

import objets.Antilope;
import objets.Eau;
import objets.Lion;
import objets.Nourriture;
import objets.Position;
import objets.Rocher;

import map.Biome;
import map.Map;
import map.Scheduler;

public class Fenetre extends JFrame implements ActionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -585608148684182937L;
	private GraphicMap gMap;
	
	private Thread t;
	private Scheduler s;
	private Map m;
	private ArrayList<Action> actionList;
	private boolean animated = true;
	
	/* option de lecture (zoom deplacement carte play pause etc...)*/
	
	private JPanel zoom;
	private JLabel zoomLabel ;
	private JButton zoomPlus;
	private JButton zoomMoins;
	
	private JButton play;
	
	private JPanel lectureOption;
	
	private DeplacementPanel dp;
	
	private MiniMap miniMap;
	/*--------------------------------------------------------------*/
	
	/* option d'ajout (ajout d'animaux et d'objet) */
	
	private JPanel meute;
	private JLabel meuteLabel;
	private JSpinner meuteSpinner;
	private AjoutPanel ajoutOption;
	
	/* --------------------------------------------------------------*/
	
	private JPanel buttonPanel;
	private JPanel contentPanel;
	
	public Fenetre (){
		super();
		
		
		this.setTitle("LO43 Vivarium");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.actionList = new ArrayList<Action>();
		m = new Map(50,50);
		
		this.gMap = new GraphicMap(m);
		gMap.addMouseListener(this);
		gMap.setPreferredSize(new Dimension(this.getWidth(), 1000));
		
		/* option de lecture (zoom deplacement carte play pause etc...)*/
		
		zoom = new JPanel();
		zoomLabel = new JLabel("zoom : ");
		zoomPlus = new JButton("+");
		zoomMoins = new JButton("-");
		zoomPlus.addActionListener(this);
		zoomMoins.addActionListener(this);
		zoom.add(zoomLabel);
		zoom.add(zoomPlus);
		zoom.add(zoomMoins);
		
		
		try {
			BufferedImage playImg = (ImageIO.read(new File("Image/BouttonIcon/Play.png")));
			play = new JButton(new ImageIcon(playImg));
			play.setBorder(BorderFactory.createEmptyBorder());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			play = new JButton();
		}
		
		dp = new DeplacementPanel(this.gMap);
		dp.setBackground(new Color(101,77,48));
		dp.setPreferredSize(new Dimension(150,150));
			/* panel des options de lecture */
		
		lectureOption = new JPanel();
		lectureOption.setBackground(new Color(101,77,48));
		lectureOption.add(zoom);
		lectureOption.add(play);
		lectureOption.add(dp);
		
		/*--------------------------------------------------------------*/
		
		/* option d'ajout (ajout d'animaux et d'objet) */
		
		meute = new JPanel();
		meuteLabel = new JLabel("Meute : ");
		meuteSpinner = new JSpinner();
		meute.add(meuteLabel);
		meute.add(meuteSpinner);	
		
			/* panel des option d'ajout */
		
		ajoutOption = new AjoutPanel();
		ajoutOption.setBackground(new Color(101,77,48));
		//ajoutOption.add(meute);
		
		/* --------------------------------------------------------------*/
		miniMap = new MiniMap(this.gMap);
		miniMap.setPreferredSize(new Dimension(500,500));
		
		buttonPanel= new JPanel();
		buttonPanel.setBackground(new Color(101,77,48));
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.setPreferredSize(new Dimension(this.getWidth(),500));
		buttonPanel.add(miniMap);
		buttonPanel.add(lectureOption);
		buttonPanel.add(ajoutOption);
		
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		
		
		contentPanel.add(gMap);
		contentPanel.add(buttonPanel);
		this.setContentPane(contentPanel);
		
		t = new Thread(new Animation());
		s = new Scheduler(m);
		t.start();
		s.start();
	}
		
	
	
	private void go(){
		while(this.animated){
			this.m.upDate(this.actionList);
			this.actionList.removeAll(this.actionList);
			this.gMap.setVariable();
			this.gMap.repaint();
			try {
				this.miniMap.SetVariable();
				this.miniMap.repaint();
			} catch (Exception e) {
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == zoomPlus){
			this.gMap.zoomPlus();
		}
		else if(arg0.getSource() == zoomMoins){
			this.gMap.zoomMoins();
		}
	}
	
	class Animation implements Runnable {
		public void run() {
			go();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
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
		Position p = new Position( ((e.getX() - this.gMap.getMargeX()) / this.gMap.getRectSize())  + this.gMap.getIstart() , ((e.getY() - (this.gMap.getMargeY())) / this.gMap.getRectSize())  + this.gMap.getJstart());	
		Action action;
		System.out.println(this.ajoutOption.getChoix());
		switch (this.ajoutOption.getChoix()) {
		case AJOUT_OBJET:
			if(ajoutOption.getObjet().equals("Lion")){
				action = new Ajouter(this.gMap.getMap(), new Lion(p));
				this.actionList.add(action);
				
			}
			else if(ajoutOption.getObjet().equals("Antilope")){
				action = new Ajouter(this.gMap.getMap(), new Antilope(p));
				this.actionList.add(action);
			}
			else if(ajoutOption.getObjet().equals("Eau")){
				action = new Ajouter(this.gMap.getMap(), new Eau(true,p));
				this.actionList.add(action);
			}
			else if(ajoutOption.getObjet().equals("Rocher")){
				action = new Ajouter(this.gMap.getMap(), new Rocher(p));
				this.actionList.add(action);
			}
			else if(ajoutOption.getObjet().equals("Herbe")){
				action = new Ajouter(this.gMap.getMap(), new Nourriture(p, false, 10, 20));
				this.actionList.add(action);
			}
			else if(ajoutOption.getObjet().equals("Viande")){
				action = new Ajouter(this.gMap.getMap(), new Nourriture(p, true, 10, 20));
				this.actionList.add(action);
			}
			
			break;
		
		case MODIFICATION_BIOME:
			action = new ModifierBiome(this.gMap.getMap(),this.ajoutOption.getBiome(),p);
			this.actionList.add(action);
			break;
		case SUPRESSION:
			action = new Suprimer(this.gMap.getMap(),p);
			this.actionList.add(action);
			break;
		default:
			break;
		}
		
	}
}

	


