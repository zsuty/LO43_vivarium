package Action;

import map.Map;
import objets.Animal;
import objets.Position;

public class Deplacer extends Action{

	private Animal animal;
	private Position position;
	
	public Deplacer(Map m,Animal a, Position p){
		super(m);
		this.animal = a;
		this.position = p;
	}
	
	@Override
	public boolean action(String s) {
		s = new String("erreur dansle deplacement");
		return this.map.Deplacer(this.animal, this.position);
	}
	
}
