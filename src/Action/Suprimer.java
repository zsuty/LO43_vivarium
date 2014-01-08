package Action;

import map.Map;
import objets.Objet;
import objets.Position;

public class Suprimer extends Action{
	private Position position;
	
	public Suprimer(Map m,Position p){
		super(m);
		this.position = p;
	}

	@Override
	public boolean action(String s) {
		this.map.suprimerObjetsCase(this.position);
		return true;
	}
}
