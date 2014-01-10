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
	private ArrayList<ArrayList<Action>> animauxActionListe = new ArrayList<ArrayList <Action>>();
	private boolean finTour = true;
	private int count = 0;
	private boolean pause = false;
	public Scheduler(Map m){
		super();
		actionList = new ArrayList<Action>();
		this.map = m;
	}
	
	public void run(){
		while(true){
			if(this.count == 0 && !pause ){
				if(this.finTour){
				
					for(Objet o : this.map.getObjets()){
						if(o instanceof Animal){
							ArrayList <Action> a = new ArrayList<Action>();
							a = ((Animal) o).decider(this.map);
							if(a != null){
								this.animauxActionListe.add(a);
							}
						}
					}
				}
				this.finTour = true;
				for(ArrayList <Action> tempList : this.animauxActionListe){
					if(!tempList.isEmpty()){
						this.finTour = false;
						this.actionList.add(tempList.get(0));
						tempList.remove(0);
					}
				}
				if(this.finTour){
					this.animauxActionListe = new ArrayList<ArrayList<Action>>();
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
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public boolean isPause() {
		return pause;
	}
	
}
