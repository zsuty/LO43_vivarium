package main;

import objets.Animal;
import objets.Objet;
import map.Map;

public class Test {
	public static void main(String[] args) {
		Map map = new Map(50,50);
		
		map.ajouterObjet(new Objet(3,2));
		map.ajouterObjet(new Objet(4,2));
		map.ajouterObjet(new Objet(3,2));
		
		Animal a = new Animal(4,2,1);
		map.ajouterObjet(a);
		System.out.println(map.getVision(a));
		
		
	}

}
