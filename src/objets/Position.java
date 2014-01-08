/*
	Fichier: Position.java
	Auteur: Justine Ventalon
	Version: 1.0
	Date: 12/12/2013

	Package : objets
	Contient: class Position

	Description:
	Position est une classe repr�sentant une position correspondant � une Case sur la Map.
	Elle contient les coordonn�es de cette position.
*/

package objets;

// Position est un couple de coordonn�es indiquant une Case de la Map.
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
	
	//---------- Methodes pour l'utilisation comme clef dans Hashtable ----------
	
	public boolean equals(Object obj) 
	{
	    if (!(obj instanceof Position)) 
	    {
	        return false;
	    } 
	    else 
	    {
	        Position that = (Position)obj;
	        return (this.x == that.x) && (this.y == that.y);
	    }
	}

	public int hashCode() 
	{
	    int hash = this.x + this.y;
	    return hash;
	}
	@Override
	public String toString() {
		return new String("x = " + x + "y= " + y);
	}
}
