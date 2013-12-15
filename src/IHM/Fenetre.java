package IHM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import map.Biome;
import map.Map;

public class Fenetre extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -585608148684182937L;
	private GraphicMap gMap;
	
	private int xDirection = 0;
	private int yDirection = 0;
	
	/* option de lecture (zoom deplacement carte play pause etc...)*/
	
	private JPanel zoom;
	private JLabel zoomLabel ;
	private JButton zoomPlus;
	private JButton zoomMoins;
	
	private JButton play;
	
	private JPanel lectureOption;
	
	private DeplacementPanel dp;
	/*--------------------------------------------------------------*/
	
	/* option d'ajout (ajout d'animaux et d'objet) */
	
	private JPanel meute;
	private JLabel meuteLabel;
	private JSpinner meuteSpinner;
	private JPanel ajoutOption;
	
	/* --------------------------------------------------------------*/
	
	private JPanel buttonPanel;
	private JPanel contentPanel;
	
	public Fenetre (){
		super();
		
		
		this.setTitle("LO43 Vivarium");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Map m = new Map(50,50);
		m.getCases()[3][3].setBiome(Biome.MONTAGNE);
		
		this.gMap = new GraphicMap(m);
		gMap.setPreferredSize(new Dimension(this.getWidth(), 10000));
		
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
			/* panel des options de lecture */
		
		lectureOption = new JPanel();
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
		
		ajoutOption = new JPanel ();
		ajoutOption.add(meute);
		
		/* --------------------------------------------------------------*/
		
		buttonPanel= new JPanel();
		buttonPanel.setBackground(Color.red);
		buttonPanel.setPreferredSize(new Dimension(this.getWidth(),100));
		buttonPanel.add(lectureOption);
		buttonPanel.add(ajoutOption);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		
		
		contentPanel.add(gMap);
		contentPanel.add(buttonPanel);
		this.setContentPane(contentPanel);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == zoomPlus){
			this.gMap.zoomPlus();
		}
		else if(arg0.getSource() == zoomMoins){
			this.gMap.zoomMoins();
		}
		this.gMap.repaint();
	}

}

