package map;

import java.util.ArrayList;

import Action.Action;
import Action.Deplacer;

import objets.Animal;
import objets.Objet;
import objets.Position;

public class Scheduler extends Thread{
	private Map map;
	private ArrayList<Action> actionList;
	private int count = 0;
	public Scheduler(Map m){
		super();
		actionList = new ArrayList<Action>();
		this.map = m;
	}
	
	public void run(){
		while(true){
			if(this.count == 0){
				for(Objet o : this.map.getObjets()){
					if(o instanceof Animal){
						ArrayList <Deplacer> d = new ArrayList<Deplacer>();
						System.out.println("2");
						for(Position p : this.map.getDirection((Animal)o , new Position(5,5), 1,true)){
							System.out.println("3");
							d.add(new Deplacer(this.map, (Animal) o, p));
						}
						actionList.addAll(d);
					}
				}
			}
			this.count = (this.count + 1) %100;
			this.map.upDate(actionList);
			actionList = new ArrayList<Action>();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public void addToActionList (Action a){
		this.actionList.add(a);
	}
	
	
}
