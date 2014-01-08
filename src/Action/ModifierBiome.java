package Action;

import objets.Eau;
import objets.Position;
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
		if(this.map.getCaseAt(this.position).getBiome() == Biome.OCEAN){
			this.map.suprimerObjetsCase(this.position);
		}
		this.map.getCaseAt(this.position).setBiome(this.biome);
		if(this.biome == Biome.OCEAN){
			this.map.suprimerObjetsCase(this.position);
			this.map.ajouterObjet(new Eau(false,this.position));
		}
		return true;
	}
	

}
