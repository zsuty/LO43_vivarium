package map;

import java.util.ArrayList;

import Action.Action;

import objets.Animal;
import objets.Objet;

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

					System.out.println("prout");
					tempAnimal = (Animal)o;
					actionList.add(tempAnimal.decider(this.map));
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
