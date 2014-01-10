package Action;

import java.util.Random;

import objets.Eau;
import objets.Nourriture;
import objets.Position;
import objets.Rocher;
import map.Biome;
import map.Map;

public class ModifierBiome extends Action{
	private Biome biome;
	private Position position;
	
	public ModifierBiome(Map m,Biome b,Position p){
		super(m);
		this.biome = b;
		this.position = p;
	}

	@Override
	public boolean action(String s) {
		Random rand = new Random();
		if((this.position.getX() >= 0 && this.position.getX() < this.map.getNbX())  && (this.position.getY() >= 0 && this.position.getY() < this.map.getNbY())){
			if(this.map.getCaseAt(this.position).getBiome() == Biome.OCEAN){
				this.map.suprimerObjetsCase(this.position);
			}
			this.map.getCaseAt(this.position).setBiome(this.biome);
			switch(this.biome){
				case OCEAN : 
					this.map.suprimerObjetsCase(this.position);
					this.map.ajouterObjet(new Eau(false,this.position));
					break;
				case FORET:
					if(rand.nextBoolean()){
						this.map.ajouterObjet(new Nourriture(this.position, false, 100, 20));
					}
					break;
				case MONTAGNE :
					if(rand.nextInt(100) < 30){
						this.map.ajouterObjet(new Rocher(this.position));
					}
					break;
			}
			return true;
		}
		return false;
	}
	

}
