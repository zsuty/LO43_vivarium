/*
	Fichier: Noeud.java
	Auteur: Justine Ventalon
	Version: 1.0
	Date: 12/12/2013

	Package : map
	Contient: class Noeud

	Description:
	Noeud représente un ensemble d'informations concernant une Position de la Map étudiée avec 
	l'algorithme A* dans la méthode getDirection de la Map afin d'obtenir le plus court chemin 
	entre deux positions.
*/

package map;

import objets.Position;

public class Noeud 
{
	private double coutG; // coût pour aller du point de départ au noeud considéré
	private double coutH; // coût pour aller du noeud considéré au point de destination
	private double coutF; // somme de coutF et coutH (mémorisée pour ne pas la recalculer à chaque fois)
	private Position parent; // position du parent (noeud par lequel on est arrivé au noeud courant)
	
	//---------- Constructeurs ----------
	
	public Noeud()
	{
		coutG = 0;
		coutH = 0;
		coutF = 0;
		parent = new Position(0, 0);
	}
	
	public Noeud(double coutG, double coutH, double coutF, Position parent)
	{
		this.coutG = coutG;
		this.coutH = coutH;
		this.coutF = coutF;
		this.parent = parent;
	}
	
	//---------- Getters ----------
	
	public double getCoutG()
	{
		return coutG;
	}
	
	public double getCoutH()
	{
		return coutH;
	}
	
	public double getCoutF()
	{
		return coutF;
	}
	
	public Position getParent()
	{
		return parent;
	}
	
	//---------- Setters ----------
	
	public void setCoutG(double coutG)
	{
		this.coutG = coutG;
	}
	
	public void setCoutH(double coutH)
	{
		this.coutH = coutH;
	}
	
	public void setCoutF(double coutF)
	{
		this.coutF = coutF;
	}
	
	public void setParent(Position parent)
	{
		this.parent = parent;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
	    if (!(obj instanceof Noeud)) 
	    {
	        return false;
	    } 
	    else 
	    {
	        Noeud that = (Noeud)obj;
	        return ((this.coutF == that.coutF) && (this.coutG == that.coutG)  && (this.coutH == that.coutH) && this.parent.equals(that.parent));
	    }
	}

	@Override
	public int hashCode() 
	{
	    int hash = Double.valueOf(this.coutF).hashCode() + Double.valueOf(this.coutG).hashCode() + Double.valueOf(this.coutH).hashCode() + this.parent.hashCode();
	    return hash;
	}
}
