package Action;

import map.Map;
import objets.Animal;
import objets.Nourriture;
import objets.Position;

public class Mourrir extends Action {

	private Position pos;
	private Animal a;
	
	public Mourrir(Map m, Position p, Animal a)
	{
		super(m);
		pos = p;
		this.a = a;
	}
	@Override
	public boolean action(String s) {
		this.map.supprimerObjet(this.a);
		Nourriture n = new Nourriture(this.pos, true, 50, 20);
		if(this.map.ajouterObjet(n)){
			s = new String("erreur loes de l'ajout d'objet");
		}
		return true;
	}
}
