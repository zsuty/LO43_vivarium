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

import objets.Objet;
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
	
	//---------- Gestion des objets ----------
	
	/* ajoute un objet sur une case 
	retourne false si l'objet n'a pas pu etre ajoute car la case contient deja un objet non franchissable */
	public boolean ajouterObjet(Objet objet)
	{
		if (getObjetNonFranchissable() == null) // si tous les objets deja presents sur la case sont franchissables
		{
				++nbObjet;
				objetsPresents.add(objet);
				return true;
		}
		return false;
	}
	
	/* retourne le premier objet non franchissable de la case
	retourne null s'il n'y en a pas */
	public Objet getObjetNonFranchissable()
	{
		for (int i = 0; i < nbObjet; ++i)
		{
			if (!objetsPresents.get(i).isFranchissable())
			{
				return objetsPresents.get(i);
			}
		}
		return null;
	}
}
