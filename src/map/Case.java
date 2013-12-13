/*
	Fichier: Case.java
	Auteur: Justine Ventalon
	Version: 1.0
	Date: 11/12/2013

	Package : map
	Contient: class Case

	Description:
	Case repr�sente une case de la map, 
	elle poss�de un biome dont les valeurs sont d�finies dans l'�num�ration Biome 
	et elle peut �tre franchissable ou non par les animaux.
*/

package map;

import objets.Objet;

// Case repr�sente une case de la Map
public class Case 
{
	private Biome biome; // identifiant du biome de la case
	private boolean franchissable; // true si les animaux peuvent franchir cette case quand ils se d�placent
	private Objet [] objetsPresents; // tableau contenant les objets présents sur la case
	private int nbObjetMax = 2; // nombre d'objet max sur une case. si le nombre max est atteint la case deviendra infranchissable même si les objets sont tous franchissable
	private int nbObjet; // le nombre d'objet sur la case
	//---------- Constucteurs ----------
	
	public Case()
	{
		biome = Biome.PLAINE;
		franchissable = true;
		this.objetsPresents = new Objet[this.nbObjetMax];
	}
	
	public Case (Biome biome, boolean franchissable)
	{
		this.biome = biome;
		this.franchissable = franchissable;
		this.objetsPresents = new Objet[this.nbObjetMax];
	}
	
	//---------- Getters ----------
	
	public Biome getBiome()
	{
		return biome;
	}
	
	public boolean isFranchissable()
	{
		return franchissable;
	}
	public Objet[] getObjetsPresents() {
		return objetsPresents;
	}
	public int getNbObjet() {
		return nbObjet;
	}
	
	/* fonction qui renvoie le premier objet non franchissable de la case. Renvoie null s'il n'y en a pas */
	public Objet getObjetNonFranchissable(){
		for(int i = 0; i < this.nbObjet; ++i){
			if(!this.objetsPresents[i].isFranchissable()){
				return this.objetsPresents[i];
			}
		}
		return null;
	}
	
	//---------- Setters ----------
	
	public void setBiome(Biome biome)
	{
		this.biome = biome;
	}
	
	public void setFranchissable(boolean franchissable)
	{
		this.franchissable = franchissable;
	}
	
	/* permet d'ajouter un objet sur une case*/
	public boolean adObjet(Objet objet){
		if(this.franchissable){
			if(this.nbObjet <= this.nbObjetMax){
				++ this.nbObjet;
				this.objetsPresents[this.nbObjet-1] = objet;
				if(!objet.isFranchissable() || this.nbObjet >= this.nbObjetMax){
					this.franchissable = false;
				}
				return true;
			}
		}
		return false;
	}

	
	
}
