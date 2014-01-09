/*
	Fichier: Case.java
	Auteur: Justine Ventalon, Zephyr Suty
	Version: 1.0
	Date: 13/12/2013

	Package : map
	Contient: class Case

	Description:
	Case represente une case de la map, 
	elle possede un biome dont les valeurs sont definies dans l'enumeration Biome.
*/

package map;

import objets.Antilope;
import objets.Eau;
import objets.Lion;
import objets.Nourriture;
import objets.Objet;
import objets.Rocher;

import java.util.ArrayList;

// Case represente une case de la Map
public class Case 
{
	private Biome biome; // biome present sur la case (plaine, montagne...)
	private ArrayList<Objet> objetsPresents; // liste des objets presents sur la case
	private int nbObjet; // nombre d'objets sur la case
	
	//---------- Constucteurs ----------
	
	public Case()
	{
		biome = Biome.PLAINE;
		this.objetsPresents = new ArrayList<Objet>();
	}
	
	public Case(Biome biome)
	{
		this.biome = biome;
		this.objetsPresents = new ArrayList<Objet>();
	}
	public Case(Case c){
		this.biome = c.getBiome();
		this.nbObjet = c.getNbObjet();
		this.objetsPresents = new ArrayList<Objet>();
		for(Objet o : c.getObjetsPresents()){
			if(o instanceof Antilope){
				this.objetsPresents.add(new Antilope((Antilope)o));
			}
			else if(o instanceof Eau){
				this.objetsPresents.add(new Eau((Eau)o));
			}
			else if(o instanceof Lion){
				this.objetsPresents.add(new Lion((Lion)o));
			}
			else if(o instanceof Nourriture){
				this.objetsPresents.add(new Nourriture((Nourriture)o));
			}
			else if(o instanceof Rocher){
				this.objetsPresents.add(new Rocher((Rocher)o));
			}
		}
	}
	
	//---------- Getters ----------
	
	public Biome getBiome()
	{
		return biome;
	}
	
	public ArrayList<Objet> getObjetsPresents() 
	{
		return objetsPresents;
	}
	
	public int getNbObjet() 
	{
		return nbObjet;
	}
	
	//---------- Setters ----------
	
	public void setBiome(Biome biome)
	{
		this.biome = biome;
	}
	public void setObjetsPresents(ArrayList <Objet> o){
		this.objetsPresents = o;
	}
	
	//---------- Gestion des objets ----------
	
	/* ajoute un objet sur une case 
	retourne false si l'objet n'a pas pu etre ajoute car la case contient deja un objet non franchissable */
	public boolean ajouterObjet(Objet objet)
	{
		if (getObjetNonFranchissable() == null || objet.isFranchissable()) // si tous les objets deja presents sur la case sont franchissables
		{
				++nbObjet;
				if(objet.isFranchissable()){
					objetsPresents.add(0,objet);
				}
				else{
					objetsPresents.add(objet);
				}
				return true;
		}
		return false;
	}
	
	/* retourne le premier objet non franchissable de la case
	retourne null s'il n'y en a pas */
	public Objet getObjetNonFranchissable()
	{
		for(Objet o : this.objetsPresents){
			if(!o.isFranchissable()){
				return o;
			}
		}
		return null;
	}
	public boolean suprimerObjet (Objet o){
		return this.objetsPresents.remove(o);
	}
}
