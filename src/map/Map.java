/*
	Fichier: Map.java
	Auteur: Justine Ventalon, Zephyr Suty
	Version: 1.0
	Date: 13/12/2013

	Package : map
	Contient: class Map

	Description:
	Map repr�sente la carte sur laquelle se d�placent les animaux,
	elle est compos�e de cases (tableau de Case � 2 dimensions)
	et elle contient tous les objets (animaux, nourriture...) pr�sents sur la carte (ArrayList d'Objet).
*/

package map;

import java.util.*;

import objets.*;


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
		for(int i = 0; i < this.nbLignes; ++i)
		{
			for(int j = 0; j < this.nbColonnes; ++j)
			{
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
	
	public Case[][] getCases()
	{
		return cases;
	}
	
	public ArrayList<Objet> getObjets()
	{
		return objets;
	}
	
	public Case getCaseAt(Position pos)
	{
		if (pos.getX() < nbColonnes && pos.getY() < nbLignes)
		{
			return cases[pos.getX()][pos.getY()];
		}
		return null;
	}
	
	//---------- Gestion des meutes ----------
	
	public void ajouterMeute()
	{
		derniereMeute++;
	}
	
	//---------- Gestion des objets ----------
	
	/* ajoute un objet a la liste des objets
	retourne false si l'objet n'a pas pu etre ajoute car la case n'est pas franchissable */
	public boolean ajouterObjet(Objet objet)
	{
		if(cases[objet.getPos().getX()][objet.getPos().getY()].ajouterObjet(objet))
		{
			objets.add(objet);
			return true;
		}
		return false;
	}
	
	public void supprimerObjet(Objet objet)
	{
		objets.remove(objet);
	}

	//---------- Gestion des cases ----------
	
	public void modifierBiome(Biome biome, Position pos)
	{
		cases[pos.getX()][pos.getY()].setBiome(biome);
	}
	
	//---------- Methodes pour Animal ----------
	
	// renvoie la liste des objets que voit un animal
	
	public ArrayList<Objet> getVision(Animal animal)
	{
		int i,j,k,positionX,positionY;
		Case tempCase;
		ArrayList<Objet> objetVisible = new ArrayList <Objet>();
		for(i = -animal.getVisionRange();i <= animal.getVisionRange() ; ++i)
		{
			for(j = -animal.getVisionRange();j <= animal.getVisionRange() ; ++j)
			{
				if((Math.abs(i) + Math.abs(j)) <= animal.getVisionRange())
				{
					positionX = i + animal.getPos().getX();
					positionY = j + animal.getPos().getY();
					
					if((positionX >= 0 && positionX < this.nbLignes) && (positionY >= 0 && positionY < this.nbColonnes))
					{
						tempCase = this.cases[positionX][positionY];
						if(tempCase.getNbObjet() != 0)
						{
							for(k = 0; k < tempCase.getNbObjet() ; ++k)
							{
								if(tempCase.getObjetsPresents().get(k) != animal)
								{
									objetVisible.add(tempCase.getObjetsPresents().get(k));
							
								}
							}
						}
					}
				}
			}
		}
		return objetVisible;
	}
	
	/* retourne la position de la prochaine case vers laquelle il faut se d�placer pour aller de la position 
	posDep vers la position posArr en empruntant le plus court chemin (calcul� avec l'algorithme A*) */
	
	public Position getDirection(Animal animal, Position pObjectif)
	{
		Position p = new Position();
		Hashtable<Position, Noeud> listeOuverte = new Hashtable<Position, Noeud>();
		Hashtable<Position, Noeud> listeFermee = new Hashtable<Position, Noeud>();
		// TODO
		return p;
	}
	
	//---------- M�thodes priv�es ----------
	
	/* retourne la distance euclidienne entre 2 positions
	utilise dans getDirection */
	private double distance(Position p1, Position p2)
	{
		int x1 = p1.getX();
		int x2 = p2.getX();
		int y1 = p1.getY();
		int y2 = p2.getY();
		return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
	}
	
	private void etudierCasesAdjacentes(Position p)
	{
		Noeud temp = new Noeud();
		
		 for (int i = p.getX()-1; i <= p.getX()+1; i++)
		 {
		        if ((i >= 0) || (i < nbColonnes))  // si la coordonn�e appartient bien � la map
		        {
		        	for (int j = p.getY()-1; j <= p.getY()+1; j++)
		        	{
		        		//TODO
		        		/*
			            if ((j<0) || (j>=s->h))   // en dehors de l'image, on oublie
			                continue;
			            if ((i==n.first) && (j==n.second))  // case actuelle n, on oublie
			                continue;
			 
			            if (*((Uint8 *)s->pixels + j * s->pitch + i * s->format->BytesPerPixel) == NOIR)
			                // obstace, terrain non franchissable, on oublie
			                continue;
			 
			            pair<int,int> it(i,j);
			            */
		        	}    
		       }
		 }
	}
}
