package map;

import java.util.ArrayList;

import Action.Action;
import Action.Deplacer;

import objets.Animal;
import objets.Objet;
import objets.Position;

public class Scheduler extends Thread{
	private Map map;
	
	public Scheduler(Map m){
		super();
		this.map = m;
	}
	
	public void run(){
		
		Animal tempAnimal;
		ArrayList <Action> actionList = new ArrayList <Action>();
		while(true){
			for(Objet o : this.map.getObjets()){
				if(o instanceof Animal){
					ArrayList <Deplacer> d = new ArrayList<Deplacer>();
					System.out.println("2");
					System.out.println(this.map.getDirection((Animal)o , new Position(5,5), 1));
					for(Position p : this.map.getDirection((Animal)o , new Position(5,5), 1)){
						System.out.println("3");
						d.add(new Deplacer(this.map, (Animal) o, p));
					}
					actionList.addAll(d);
				}
			}
			this.map.upDate(actionList);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
