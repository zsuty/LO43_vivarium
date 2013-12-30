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

import objets.*;


// Map est la carte sur laquelle évoluent les animaux
public class Map 
{
	private int derniereMeute; // numéro de la dernière meute créée
	private int nbLignes; // nombre de lignes du tableau de cases
	private int nbColonnes; // nombre de colonnes du tableau de cases
	private Case cases[][]; // tableau de cases à 2 dimensions représentant la map
	private ArrayList<Objet> objets; // liste des objets présents sur la map (animaux, nourriture...)
	
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
	
	/* retourne la position de la prochaine case vers laquelle il faut se déplacer pour aller de la position 
	posDep vers la position posArr en empruntant le plus court chemin (calculé avec l'algorithme A*) */
	public Position getDirection(Animal animal, Position pObjectif)
	{
		Position pDepart = animal.getPos();
		Hashtable<Position, Noeud> listeOuverte = new Hashtable<Position, Noeud>();
		Hashtable<Position, Noeud> listeFermee = new Hashtable<Position, Noeud>();
		Noeud nCourant = new Noeud();
	 
	    /* déroulement de l'algo A* */
	 
	    /* initialisation du noeud courant */
		Position pCourant = pDepart;
		
		/* ajout de courant dans la liste ouverte */
		listeOuverte.put(pCourant, nCourant);
	    ajouterListeFermee(listeOuverte, listeFermee, pCourant);
	    etudierCasesAdjacentes(pCourant, pObjectif, listeOuverte, listeFermee);
	 
	    /* tant que la destination n'a pas ete atteinte et qu'il reste des noeuds a explorer dans la liste ouverte */
	    while (!(pCourant.getX() == pObjectif.getX() && pCourant.getY() == pObjectif.getY()) && (!listeOuverte.isEmpty()))
	    {
	        /* on cherche le meilleur noeud de la liste ouverte (elle n'est pas vide donc il existe) */
	        pCourant = meilleurNoeud(listeOuverte);
	        
	        /* on le passe dans la liste fermee, il ne peut pas déjà y être */
	        ajouterListeFermee(listeOuverte, listeFermee, pCourant);
	 
	        /* on recommence la recherche des noeuds adjacents */
	        etudierCasesAdjacentes(pCourant, pObjectif, listeOuverte, listeFermee);
	    }
	    
	    /* si la destination est atteinte, on remonte le chemin */
	    if (pCourant.getX() == pObjectif.getX() && pCourant.getY() == pObjectif.getY())
	    {
	    	
	        ArrayList<Position> chemin = retrouverChemin(listeFermee, pDepart, pObjectif);
	        if (!chemin.isEmpty())
	        {
		        Position pDirection = chemin.get(chemin.size()-1);
		        if (!(pDirection.getX() == pObjectif.getX() && pDirection.getY() == pObjectif.getY()))
		        {
		        	return pDirection;
		        }
	        }
	    }
	    
	    return null;
	}
	
	//---------- Méthodes privées ----------
	
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
		        if ((i >= 0) && (i < nbColonnes))  
		        	// si la coordonnée appartient bien à la map
		        {
		        	for (int j = p.getY()-1; j <= p.getY()+1; j++)
		        	{
			            if ((j >= 0) && (j < nbLignes))   
			            	// si la coordonnée appartient bien à la map
			            {
			            	if (!((i == p.getX()) && (j == p.getY())))  // si ce n'est pas la case courante
			            	{
			            		Position posTemp = new Position(i,j);
			            		if (getCaseAt(posTemp).getObjetNonFranchissable() == null) 
			            			// si la case est franchissable
				                {        
			            			 if (!dejaDansListe(posTemp, listeFermee))
			            				 // si le noeud n'est pas deja present dans la liste fermee
			            			 {
			            				 temp.setParent(p);
			            				 
			            	             // calcul du cout G du noeud en cours d'étude : cout du parent + distance jusqu'au parent
			            				 double coutG = listeFermee.get(p).getCoutG() + distance(posTemp, p);
			            				 temp.setCoutG(coutG);  
			            				 
			            				 // calcul du cout H du noeud en cours d'etude : distance jusqu'a la destination
			            				 double coutH = distance(posTemp, pDest);
			            				 temp.setCoutH(coutH);
			            				 
			            				 // calcul du cout F
			            	             temp.setCoutF(coutG + coutH);
			            	            
			            	             if (dejaDansListe(posTemp, listeOuverte))
			            	            	 // si le noeud est deja dans la liste ouverte 
			            	             {
			            	            	 	
			            	            	 	Noeud noeud = listeOuverte.get(posTemp);
			            	                    if (temp.getCoutF() < noeud.getCoutF())
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
	
	/* retourne la Position du meilleur Noeud de la liste passée en paramètres */
	private Position meilleurNoeud(Hashtable<Position, Noeud> liste)
	{
	    Enumeration<Noeud> noeuds = liste.elements();
	    Enumeration<Position> positions = liste.keys();
	    if (noeuds.hasMoreElements() && positions.hasMoreElements())
	    {
	    	double meilleurCoutF = noeuds.nextElement().getCoutF();
	    	Position meilleurePos = positions.nextElement();
	    	
	    	while(noeuds.hasMoreElements() && positions.hasMoreElements())
		    {
	    		double coutF = noeuds.nextElement().getCoutF();
	    		Position pos = positions.nextElement();
		        if (coutF < meilleurCoutF)
		        {
		            meilleurCoutF = coutF;
		            meilleurePos = pos;
		        }
		    }
	    	
	    	return meilleurePos;
	    }
	    
	    return null;
	}
	
	/* ajoute le noeud a la liste fermee et le supprime de la liste ouverte */
	void ajouterListeFermee(Hashtable<Position, Noeud> listeOuverte, Hashtable<Position, Noeud> listeFermee, Position pos)
	{
	    Noeud n = listeOuverte.get(pos);
	    listeFermee.put(pos, n);
	    listeOuverte.remove(pos);
	    return;
	}
	
	ArrayList<Position> retrouverChemin(Hashtable<Position, Noeud> listeFermee, Position posDepart, Position posArrivee)
	{
	    /* l'arrivee est le dernier element de la liste fermee */
	    Noeud temp = listeFermee.get(posArrivee);
	    ArrayList<Position> chemin = new ArrayList<Position>();
	 
	    Position posCourante = posArrivee;
	    Position posPrecedente = temp.getParent();
	    chemin.add(posCourante);
	 
	    while (!(posPrecedente.getX() == posDepart.getX() && posPrecedente.getY() == posDepart.getY()))
	    {
	    	posCourante = posPrecedente;
	    	chemin.add(posCourante);
	 
	        temp = listeFermee.get(temp.getParent());
	        posPrecedente = temp.getParent();
	    }
	    return chemin;
	}
	
	boolean dejaDansListe(Position pos, Hashtable<Position, Noeud> liste)
	{
	    Enumeration<Position> keys = liste.keys();
	    while (keys.hasMoreElements())
	    {
	    	Position pCourant = keys.nextElement();
	    	if (pCourant.getX() == pos.getX() && pCourant.getY() == pos.getY())
	    	{
	    		return true;
	    	}
	    }
	    return false;
	}
}
