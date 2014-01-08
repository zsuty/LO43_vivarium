package Action;

import map.Map;

public abstract class Action {
	protected Map map;
	
	public Action(Map m){
		this.map = m;
	}
	public abstract boolean action(String s);
}
