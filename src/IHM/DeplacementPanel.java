package IHM;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DeplacementPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030159058687926068L;

	private JButton haut;
	private JButton bas;
	private JButton droite;
	private JButton gauche;
	private JPanel M;

	private GraphicMap gMap;
	
	public DeplacementPanel(GraphicMap m){
		super();
		this.gMap = m;
		this.haut =new JButton();
		this.bas =new JButton();
		this.droite =new JButton();
		this.gauche =new JButton();
		this.M = new JPanel();
		
		this.haut.addActionListener(this);
		this.bas.addActionListener(this);
		this.gauche.addActionListener(this);
		this.droite.addActionListener(this);
		
		M.setLayout(new BoxLayout(M, BoxLayout.X_AXIS));
		M.add(this.gauche);
		M.add(this.droite);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(this.haut);
		this.add(this.M);
		this.add(this.bas);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.haut){
			this.gMap.addYStart(-1);
		}
		else if(e.getSource() == this.bas){
			this.gMap.addYStart(1);
		}
		else if(e.getSource() == this.gauche){
			this.gMap.addXStart(-1);
		}
		else if(e.getSource() == this.droite){
			this.gMap.addXStart(1);
		}
		this.gMap.repaint();
	}

}
