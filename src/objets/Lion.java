package objets;

import map.Map;
import Action.Action;
import Action.Deplacer;

public class Lion extends Animal{

	public Lion(Position p){
		super();
		this.pos = p;
		this.franchissable = false;
	}

	@Override
	public Action decider(Map map) {
		
		return new Deplacer(map, this, new Position(this.pos.getX() + 1, this.pos.getY()));
	}
	@Override
	public String toString() {
		return new String("Lion");
	}
}
