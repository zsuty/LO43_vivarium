/*
	Fichier: Noeud.java
	Auteur: Justine Ventalon
	Version: 1.0
	Date: 12/12/2013

	Package : map
	Contient: class Noeud

	Description:
	Noeud repr�sente un ensemble d'informations concernant une Position de la Map �tudi�e avec 
	l'algorithme A* dans la m�thode getDirection de la Map afin d'obtenir le plus court chemin 
	entre deux positions.
*/

package map;

import objets.Position;

public class Noeud 
{
	double coutG; // co�t pour aller du point de d�part au noeud consid�r�
	double coutH; // co�t pour aller du noeud consid�r� au point de destination
	double coutF; // somme de coutF et coutH (m�moris�e pour ne pas la recalculer � chaque fois)
	Position parent; // position du parent (noeud par lequel on est arriv� au noeud courant)
	
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
}
