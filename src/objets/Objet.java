/*
	Fichier: Objet.java
	Auteurs: Justine Ventalon, Zephyr Suty
	Version: 1.0
	Date: 13/12/2013

	Package : map
	Contient: class Objet

	Description:
	Objet est une classe abstraite qui sera heritee par tous les types d'objets presents sur la Map (Animal, 
	Nourriture...)
	Elle contient uniquement la Position de l'objet sur la Map.
*/

package objets;

// Objet est une classe abstraite qui sera heritee par tous les types d'entitees presentes sur la Map
public abstract class Objet 
{
	protected Position pos;
	protected boolean franchissable;
	
	//---------- Constructeurs ----------
	
	public Objet()
	{
		this.pos = new Position(0,0);
		this.franchissable = true;
	}
	
	public Objet(boolean franchissable)
	{
		this.pos = new Position(0,0);
		this.franchissable = franchissable;
	}
	
	public Objet(boolean franchissable, Position pos)
	{
		this.pos = pos;
		this.franchissable = franchissable;
	}
	public Objet(Objet o){
		this.pos = new Position(o.getPos());
		this.franchissable = o.isFranchissable();
	}
	
	//---------- Getters ----------
	
	public Position getPos()
	{
		return pos;
	}
	
	public boolean isFranchissable() 
	{
		return franchissable;
	}
	
	//---------- Setters ----------
	
	public void setPos(Position pos)
	{
		this.pos = pos;
	}
	
	public void setFranchissable(boolean franchissable)
	{
		this.franchissable = franchissable;
	}
}
