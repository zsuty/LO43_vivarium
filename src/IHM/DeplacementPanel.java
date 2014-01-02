package IHM;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class DeplacementPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030159058687926068L;

	private JButton haut;
	private JButton bas;
	private JButton droite;
	private JButton gauche;

	private GraphicMap gMap;
	
	public DeplacementPanel(GraphicMap m){
		super();
		this.gMap = m;
		this.haut =new JButton();
		this.bas =new JButton();
		this.droite =new JButton();
		this.gauche =new JButton();
		
		this.haut.addActionListener(this);
		this.bas.addActionListener(this);
		this.gauche.addActionListener(this);
		this.droite.addActionListener(this);
		
		
		
		this.setLayout(new GridLayout(3, 3));
		this.add(new JLabel());
		this.add(this.haut);
		this.add(new JLabel());
		this.add(this.gauche);
		this.add(new JLabel());
		this.add(this.droite);
		this.add(new JLabel());
		this.add(this.bas);
		this.add(new JLabel());
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
	}

}
