/*
	Fichier: Animal.java
	Auteur: Zephyr Suty
	Version: 1.0
	Date: 13/12/2013

	Package : map
	Contient: class Animal

	Description:
	Animal herite d'Objet et represente un animal qui se deplace sur la Map.
*/

package objets;

public /*abstract*/ class  Animal extends Objet
{
	protected int vie;
	protected int faim;
	protected int energie;
	protected int soif;
	protected int vitesse;
	protected int attaque;
	protected int defense;
	protected int visionRange;
	protected int numeroMeute;
	
	protected boolean male;
	
	public Animal()
	{
		super(false);
		this.visionRange = 1;
	}
	
	public Animal(int r)
	{
		super(false);
		this.visionRange = r;
	}
	
	public Animal(Position p, int r)
	{
		super(false, p);
		this.visionRange = r;
	}
	
	//---------- Getters ----------
	
	public int getVie() 
	{
		return vie;
	}
	
	public int getFaim() 
	{
		return faim;
	}
	
	public int getEnergie() 
	{
		return energie;
	}
	
	public int getSoif() 
	{
		return soif;
	}
	
	public int getVitesse() 
	{
		return vitesse;
	}
	
	public int getAttaque() 
	{
		return attaque;
	}
	
	public int getDefense() 
	{
		return defense;
	}
	
	public int getVisionRange() 
	{
		return visionRange;
	}
	
	public int getNumeroMeute() 
	{
		return numeroMeute;
	}
}
