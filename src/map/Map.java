/*
	Fichier: Map.java
	Auteur: Justine Ventalon
	Version: 1.0
	Date: 11/12/2013

	Package : map
	Contient: class Map

	Description:
	Map repr�sente la carte sur laquelle se d�placent les animaux,
	elle est compos�e de cases (tableau de Case � 2 dimensions)
	et elle contient tous les objets (animaux, nourriture...) pr�sents sur la carte (ArrayList d'Objet).
*/

package map;

import java.util.ArrayList;

import objets.Animal;
import objets.Objet;


// Map est la carte sur laquelle �voluent les animaux
public class Map 
{
	private int derniereMeute; // num�ro de la derni�re meute cr��e
	private int nbLignes; // nombre de lignes du tableau de cases
	private int nbColonnes; // nombre de colonnes du tableau de cases
	private Case cases[][]; // tableau de cases � 2 dimensions repr�sentant la map
	private ArrayList<Objet> objets; // liste des objets pr�sents sur la map (animaux, nourriture...)
	
	//---------- Constructeurs ----------
	
	public Map()
	{
		derniereMeute = 0;
		nbLignes = 0;
		nbColonnes = 0;
		cases = new Case[nbLignes][nbColonnes];
		objets = new ArrayList<Objet>();
	}
	
	public Map(int nbLignes, int nbColonnes)
	{
		derniereMeute = 0;
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		cases = new Case[nbLignes][nbColonnes];
		for(int i = 0; i < this.nbLignes; ++i){
			for(int j = 0; j < this.nbColonnes; ++j){
				this.cases[i][j] = new Case();
			}
		}
		objets = new ArrayList<Objet>();
	}
	
	//---------- Getters ----------
	
	public int getDerniereMeute()
	{
		return derniereMeute;
	}
	
	public int getNbLignes()
	{
		return nbLignes;
	}
	
	public int getNbColonnes()
	{
		return nbColonnes;
	}
	
	//---------- Setters ----------
	
	
	
	//----------
	
	public void ajouterMeute()
	{
		derniereMeute++;
	}
	
	public boolean ajouterObjet(Objet objet)
	{
		if(this.cases[objet.getX()][objet.getY()].adObjet(objet)){
			this.objets.add(objet);
			return true;
		}
		return false;
	}
	
	public void supprimerObjet(Objet objet)
	{
		objets.remove(objet);
	}
	
	public void modifierBiome(Biome biome, int x, int y)
	{
		cases[x][y].setBiome(biome);
	}
	
	/* fonction qui renvoie la liste des objets que voit un animal */
	
	public ArrayList<Objet> getVision(Animal animal){
		int i,j,k,positionX,positionY;
		Case tempCase;
		ArrayList<Objet> objetVisible = new ArrayList <Objet>();
		for(i = -animal.getVisionRange();i <= animal.getVisionRange() ; ++i){
			for(j = -animal.getVisionRange();j <= animal.getVisionRange() ; ++j){
				if((Math.abs(i) + Math.abs(j)) <= animal.getVisionRange()){
					positionX = i + animal.getX();
					positionY = j + animal.getY();
					
					if((positionX >= 0 && positionX < this.nbLignes) && (positionY >= 0 && positionY < this.nbColonnes)){
						tempCase = this.cases[positionX][positionY];
						if(tempCase.getNbObjet() != 0){
							for(k = 0; k < tempCase.getNbObjet() ; ++k){
								if(tempCase.getObjetsPresents()[k] != animal){
									objetVisible.add(tempCase.getObjetsPresents()[k]);
							
								}
							}
						}
					}
				}
			}
		}
		return objetVisible;
	}

	
}
