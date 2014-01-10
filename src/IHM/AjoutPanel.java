package IHM;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import map.Biome;

public class AjoutPanel extends JPanel{
	
	private JRadioButton ajoutObjet;
	private JRadioButton modificationBiome;
	private JRadioButton supression;
	private ButtonGroup buttonGroup;
	
	private JComboBox<String> objet;
	private JComboBox<String> biome;
	
	public AjoutPanel(){
		super();
		
		ajoutObjet = new JRadioButton("ajout d'objet :");
		modificationBiome = new JRadioButton("modification du biome : ");
		supression = new JRadioButton("Supression");
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(ajoutObjet);
		buttonGroup.add(modificationBiome);
		buttonGroup.add(supression);
		
		objet = new JComboBox<String>();
		objet.addItem("Lion");
		objet.addItem("Antilope");
		objet.addItem("Rocher");
		objet.addItem("Eau");
		objet.addItem("Herbe");
		objet.addItem("Viande");
		biome = new JComboBox<String>();
		biome.addItem("Plaine");
		biome.addItem("Montagne");
		biome.addItem("Ocean");
		biome.addItem("Fertile");
		
		this.setLayout(new GridLayout(3, 2));
		this.add(ajoutObjet);
		this.add(objet);
		this.add(modificationBiome);
		this.add(biome);
		this.add(supression);
		
	}
	
	public enum Choix{
		MODIFICATION_BIOME,
		AJOUT_OBJET,
		SUPRESSION,
		DEFAULT;
	}
	
	public Choix getChoix(){
		
		if(ajoutObjet.isSelected()){
			return Choix.AJOUT_OBJET;
		}
		else if(modificationBiome.isSelected()){
			return Choix.MODIFICATION_BIOME;
		}
		else if(supression.isSelected()){
			return Choix.SUPRESSION;
		}
		return Choix.DEFAULT;
	}
	public String getObjet(){
		return (String) objet.getSelectedItem();
	}
	public Biome getBiome(){
		if(biome.getSelectedItem().equals("Plaine")){
			return Biome.PLAINE;
		}
		else if(biome.getSelectedItem().equals("Montagne")){
			return Biome.MONTAGNE;
		}
		else if(biome.getSelectedItem().equals("Ocean")){
			return Biome.OCEAN;
		}
		else if(biome.getSelectedItem().equals("Fertile")){
			return Biome.FORET;
		}
		return Biome.PLAINE;
	}
}
