/*
	Fichier: Map.java
	Auteur: Justine Ventalon, Zephyr Suty
	Version: 1.0
	Date: 13/12/2013

	Package : map
	Contient: class Map

	Description:
	Map represente la carte sur laquelle se deplacent les animaux,
	elle est composee de cases (tableau de Case a 2 dimensions)
	et elle contient tous les objets (animaux, nourriture...) presents sur la carte (ArrayList d'Objet).
*/

package map;

import java.util.*;

import Action.Action;

import objets.*;


// Map est la carte sur laquelle �voluent les animaux
public class Map 
{
	private int derniereMeute; // num�ro de la derni�re meute cr��e
	private int nbX; // nombre de lignes du tableau de cases
	private int nbY; // nombre de colonnes du tableau de cases
	private Case cases[][]; // tableau de cases � 2 dimensions repr�sentant la map
	private ArrayList<Objet> objets; // liste des objets pr�sents sur la map (animaux, nourriture...)
	
	//---------- Constructeurs ----------
	
	public Map()
	{
		derniereMeute = 0;
		nbX = 0;
		nbY = 0;
		cases = new Case[nbX][nbY];
		objets = new ArrayList<Objet>();
	}
	
	public Map(int nbLignes, int nbColonnes)
	{
		derniereMeute = 0;
		this.nbX = nbLignes;
		this.nbY = nbColonnes;
		cases = new Case[nbLignes][nbColonnes];
		for(int i = 0; i < this.nbX; ++i)
		{
			for(int j = 0; j < this.nbY; ++j)
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
	
	public int getNbX()
	{
		return nbX;
	}
	
	public int getNbY()
	{
		return nbY;
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
		if (pos.getX() < nbX && pos.getY() < nbY)
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
		if((objet.getPos().getX() >= 0 && objet.getPos().getX() < this.nbX) && (objet.getPos().getY() >= 0 && objet.getPos().getY() < this.nbY)){
			if(cases[objet.getPos().getX()][objet.getPos().getY()].ajouterObjet(objet))
			{
				objets.add(objet);
				return true;
			}
		}
		return false;
	}
	
	public void supprimerObjet(Objet objet)
	{
		this.getCaseAt(objet.getPos()).getObjetsPresents().remove(objet);
		objets.remove(objet);
	}
	public void suprimerObjetsCase(Position p){
		for(Objet o : this.getCaseAt(p).getObjetsPresents()){
			objets.remove(o);
		}
		this.getCaseAt(p).setObjetsPresents(new ArrayList <Objet>());
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
					
					if((positionX >= 0 && positionX < this.nbX) && (positionY >= 0 && positionY < this.nbY))
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
		Position p = animal.getPos();
		Hashtable<Position, Noeud> listeOuverte = new Hashtable<Position, Noeud>();
		Hashtable<Position, Noeud> listeFermee = new Hashtable<Position, Noeud>();
		etudierCasesAdjacentes(p, pObjectif, listeOuverte, listeFermee);
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
	
	private void etudierCasesAdjacentes(Position p, Position pDest, Hashtable<Position, Noeud> listeOuverte, Hashtable<Position, Noeud> listeFermee)
	{
		Noeud temp = new Noeud();
		
		 for (int i = p.getX()-1; i <= p.getX()+1; i++)
		 {
		        if ((i >= 0) && (i < nbX))  
		        	// si la coordonn�e appartient bien � la map
		        {
		        	for (int j = p.getY()-1; j <= p.getY()+1; j++)
		        	{
			            if ((j >= 0) && (j < nbY))   
			            	// si la coordonn�e appartient bien � la map
			            {
			            	if (!((i == p.getX()) && (j == p.getY())))  // si ce n'est pas la case courante
			            	{
			            		Position posTemp = new Position(i,j);
			            		if (getCaseAt(posTemp).getObjetNonFranchissable() == null) 
			            			// si la case est franchissable
				                {		            			
			            			 if (!listeFermee.contains(posTemp)) 
			            				 // si le noeud n'est pas deja present dans la liste fermee
			            			 {
			            				 temp.setParent(p);
			            				 
			            	             // calcul du cout G du noeud en cours d'�tude : cout du parent + distance jusqu'au parent
			            				 double coutG = listeFermee.get(p).getCoutG() + distance(posTemp, p);
			            				 temp.setCoutG(coutG);  
			            				 
			            				 // calcul du cout H du noeud en cours d'etude : distance jusqu'a la destination
			            				 double coutH = distance(posTemp, pDest);
			            				 temp.setCoutH(coutH);
			            				 
			            				 // calcul du cout F
			            	             temp.setCoutF(coutG + coutH);
			            	 
			            	             if (listeOuverte.contains(temp))
			            	            	 // si le noeud est deja dans la liste ouverte 
			            	             {
			            	                    if (temp.getCoutF() < listeOuverte.get(posTemp).getCoutF())
			            	                    	// si le nouveau chemin est meilleur
			            	                    {
			            	                        listeOuverte.put(posTemp, temp);
			            	                    }
			            	             }
			            	             else
			            	            	 // si le noeud n'est pas encore present dans la liste ouverte
			            	             {
			            	            	 listeOuverte.put(posTemp, temp);
			            	             }
			            			 }
				                }
			            	}
			            }
		        	}    
		       }
		 }
	}
	
	public boolean Deplacer(Animal animal, Position position){
		if((position.getX() >= 0 && position.getX() < this.nbX) && (position.getY() >= 0 && position.getY() < this.nbY)){
			if(this.cases[animal.getPos().getX()][animal.getPos().getY()].suprimerObjet(animal)){
				if(this.cases [position.getX()][position.getY()].ajouterObjet(animal)){
					animal.setPos(new Position(position.getX(), position.getY()));
					return true;
				}
			}
		}
		return false;
	}
	
	public void upDate(ArrayList <Action> actionList){
		String s = new String();
		for(Action action : actionList){
			if(!action.action(s)){
				System.out.println(s);
			}
		}
	}
}
