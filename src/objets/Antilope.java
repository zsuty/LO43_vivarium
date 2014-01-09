package objets;

import map.Map;
import Action.Action;

public class Antilope extends Animal{
	public Antilope(Position pos){
		super(pos,3);
	}
	public Antilope(Antilope a){
		super((Animal)a);
	}
	@Override
	public Action decider(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

}
