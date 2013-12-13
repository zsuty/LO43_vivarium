/*
	Fichier: Objet.java
	Auteur: Justine Ventalon
	Version: 1.0
	Date: 11/12/2013

	Package : map
	Contient: class Objet

	Description:
	Objet est une classe abstraite qui sera h�rit�e par tous les types d'objets pr�sents sur la Map (Animal, 
	Nourriture...)
	Elle contient uniquement les coordonn�es indiquant la position de l'objet sur la Map (c'est-�-dire
	l'index de la Case sur laquelle il se trouve).
*/

package objets;

// Objet est une classe abstraite qui sera h�rit�e par tous les types d'objets pr�sents sur la Map
public /*abstract*/ class Objet 
{
	protected int x;
	protected int y;
	protected boolean franchissable;
	
	public Objet(int x, int y){
		this.x = x;
		this.y= y;
		this.franchissable = true;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isFranchissable() {
		return franchissable;
	};
	
	public String toString(){
		return new String(x + " : " + y);
	}
}
