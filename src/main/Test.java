package main;

import javax.swing.SwingUtilities;

import IHM.Fenetre;

import objets.Animal;
import objets.Objet;
import map.Map;

public class Test {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crée une nouvelle instance de notre JDialog
				Fenetre fenetre = new Fenetre();
				fenetre.setVisible(true);//On la rend visible
			}
		});
		
		
	}

}
