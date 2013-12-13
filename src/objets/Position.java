/*
	Fichier: Position.java
	Auteur: Justine Ventalon
	Version: 1.0
	Date: 12/12/2013

	Package : objets
	Contient: class Position

	Description:
	Position est une classe représentant une position correspondant à une Case sur la Map.
	Elle contient les coordonnées de cette position.
*/

package objets;

// Position est un couple de coordonnées indiquant une Case de la Map.
public class Position 
{
	private int x;
	private int y;
	
	//---------- Constructeurs ----------
	
	public Position()
	{
		x = 0;
		y = 0;
	}
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//---------- Getters ----------
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	//---------- Setters ----------
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
}
