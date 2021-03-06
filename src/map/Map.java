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

import Action.*;
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
	
	public synchronized Case[][] getCases()
	{
		return cases;
	}
	
	public synchronized ArrayList<Objet> getObjets()
	{
		return objets;
	}
	
	public synchronized Case getCaseAt(Position pos)
	{
		if (pos.getX() < nbX && pos.getY() < nbY)
		{
			return cases[pos.getX()][pos.getY()];
		}
		return null;
	}
	
	public Case[][] getCopieCases(){
		int i,j;
		Case [][] newCases = new Case [this.nbX][this.nbY];
		for(i = 0; i < this.nbX; ++i){
			for(j = 0; j < this.nbY; ++j){
				newCases[i][j] = new Case(this.cases[i][j]);
			}
		}
		return newCases;
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
		int i,j,positionX,positionY;
		Case tempCase;
		ArrayList<Objet> objetVisible = new ArrayList <Objet>();
		for(i = -animal.getVisionRange();i <= animal.getVisionRange() ; ++i)
		{
			for(j = -animal.getVisionRange();j <= animal.getVisionRange() ; ++j)
			{
				if(Math.max(Math.abs(i),  Math.abs(j)) <= animal.getVisionRange())
				{
					positionX = i + animal.getPos().getX();
					positionY = j + animal.getPos().getY();
					
					if((positionX >= 0 && positionX < this.nbX) && (positionY >= 0 && positionY < this.nbY))
					{
						tempCase = this.cases[positionX][positionY];
						if(tempCase.getNbObjet() != 0)
						{
							for(Objet o : tempCase.getObjetsPresents()){
								if(o != animal){
									objetVisible.add(o);
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
	public ArrayList<Position> getDirection(Animal animal, Position pObjectif, int nbPas, boolean acote)
	{
		ArrayList<Position> direction = new ArrayList<Position>();
		Position pDepart = animal.getPos();
		Hashtable<Position, Noeud> listeOuverte = new Hashtable<Position, Noeud>();
		Hashtable<Position, Noeud> listeFermee = new Hashtable<Position, Noeud>();
		Noeud nCourant = new Noeud();
		ArrayList<Position> chemin = null;
		
		if (pDepart.getX() == pObjectif.getX() && pDepart.getY() == pObjectif.getY())
		{
			return direction;
		}
		else
		{
		    /* d�roulement de l'algo A* */
		 
		    /* initialisation du noeud courant */
			Position pCourant = pDepart;
			
			/* ajout de courant dans la liste ouverte */
			listeOuverte.put(pCourant, nCourant);
		 
		    /* tant que la destination n'a pas ete atteinte et qu'il reste des noeuds a explorer dans la liste ouverte */
		    do
		    {   
		        /* on le passe dans la liste fermee, il ne peut pas deja y etre */
		        ajouterListeFermee(listeOuverte, listeFermee, pCourant);
		        
		        /* on recommence la recherche des noeuds adjacents */
		        etudierCasesAdjacentes(pCourant, pObjectif, listeOuverte, listeFermee);
		        
		        /* on cherche le meilleur noeud de la liste ouverte (elle n'est pas vide donc il existe) */
		        pCourant = meilleurNoeud(listeOuverte);
		    }
		    while ((pCourant.getX() != pObjectif.getX() || pCourant.getY() != pObjectif.getY()));
		    
		    if (acote)
		    {
		    	chemin = retrouverChemin(listeFermee, pDepart, listeOuverte.get(pCourant).getParent());
		    }
		    else
		    {
		        ajouterListeFermee(listeOuverte, listeFermee, pCourant);
		        chemin = retrouverChemin(listeFermee, pDepart, pObjectif);
		    }
		    
        	for (int i = 1; i <= nbPas && i < chemin.size(); i++)
        	{
		        direction.add(chemin.get(i));
        	}
		    return direction;
		}
	}
	
	//---------- M�thodes priv�es ----------
	
	/* retourne le carre de la distance euclidienne entre 2 positions
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
		 for (int i = p.getX()-1; i <= p.getX()+1; i++)
		 {
		        if ((i >= 0) && (i < nbX))
		        	// si la coordonn�e appartient bien � la map
		        {
		        	for (int j = p.getY()-1; j <= p.getY()+1; j++)
		        	{
		        		Noeud temp = new Noeud();
			            if ((j >= 0) && (j < nbY))   
			            	// si la coordonn�e appartient bien � la map
			            {
			            	if ((i != p.getX()) || (j != p.getY()))  // si ce n'est pas la case courante
			            	{
			            		//System.out.println("- etudie case : " + i + ", " + j);
			            		Position posTemp = new Position(i,j);
			            		if (getCaseAt(posTemp).getObjetNonFranchissable() == null || (posTemp.getX() == pDest.getX() && posTemp.getY() == pDest.getY())) 
			            			// si la case est franchissable ou que c'est la destination
				                {        
			            			//System.out.println("case " + i + ", " + j + " franchissable");
			            			 if (!dejaDansListe(posTemp, listeFermee))
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
	
	/* retourne la Position du meilleur Noeud de la liste passee en parametres */
	private Position meilleurNoeud(Hashtable<Position, Noeud> liste)
	{
	    Enumeration<Position> positions = liste.keys();
	    if (positions.hasMoreElements())
	    {
	    	Position meilleurePos = positions.nextElement();
	    	while(positions.hasMoreElements())
		    {
	    		double meilleurCoutF = liste.get(meilleurePos).getCoutF();
	    		Position pos = positions.nextElement();
	    		double coutF = liste.get(pos).getCoutF();
		    	
		        if (coutF < meilleurCoutF)
		        {
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
	}
	
	ArrayList<Position> retrouverChemin(Hashtable<Position, Noeud> listeFermee, Position posDepart, Position posArrivee)
	{
	    /* l'arrivee est le dernier element de la liste fermee */
	    ArrayList<Position> chemin = new ArrayList<Position>();
	    
	    Position posCourante = posArrivee;
	    Noeud noeudCourant = listeFermee.get(posCourante);
	    if (noeudCourant == null)
	    {
	    	System.out.println("null");
	    }
	    Position posPrecedente = noeudCourant.getParent();
	    chemin.add(0, posCourante);
	 
	    while ((posCourante.getX() != posDepart.getX()) || (posCourante.getY() != posDepart.getY()))
	    {
	    	posCourante = posPrecedente;
	    	chemin.add(0, posCourante);
	 
	    	noeudCourant = listeFermee.get(posCourante);
	        posPrecedente = noeudCourant.getParent();
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

	public boolean Deplacer(Animal animal, Position position){
		if((position.getX() >= 0 && position.getX() < this.nbX) && (position.getY() >= 0 && position.getY() < this.nbY)){
			if(Math.abs(position.getX() - animal.getPos().getX()) <= 1 &&  Math.abs(position.getY() - animal.getPos().getY()) <= 1){
				Objet tempObjet = this.cases [position.getX()][position.getY()].getObjetNonFranchissable();
				boolean gagnant = true;
				if(tempObjet != null){
					if(tempObjet instanceof Lion){
						if(animal instanceof Antilope){
							gagnant = conflit(animal, (Animal)tempObjet);
						}
						else if(animal instanceof Lion ){
							if(((Lion) tempObjet).isAgressif() || ((Lion)animal).isAgressif()){
								gagnant = conflit(animal, (Animal)tempObjet);
							}
						}
					}
					else if(tempObjet instanceof Antilope){
						if(animal instanceof Lion){
							gagnant = conflit(animal, (Animal)tempObjet);
						}
					}
				}
				if(gagnant){
					if(this.cases [position.getX()][position.getY()].ajouterObjet(animal)){
				
						if(this.cases[animal.getPos().getX()][animal.getPos().getY()].suprimerObjet(animal)){
							animal.setPos(new Position(position.getX(), position.getY()));
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	private boolean conflit(Animal a1, Animal a2){
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("COMBAT");
		System.out.println();System.out.println();
		Random rand = new Random();
		int attaque1 = a1.getAttaque() * rand.nextInt(10);
		int defense1 = a1.getDefense() * rand.nextInt(10);
		int attaque2 = a2.getAttaque() * rand.nextInt(10);
		int defense2 = a2.getDefense() * rand.nextInt(10);
		int degat1;
		int degat2;
		if(defense2 < attaque1){
			degat1 = attaque1 - defense2;
		}
		else {
			degat1 = 0;
		}
		if(defense1 < attaque2){
			degat2 = attaque2 - defense1;
		}
		else{
			degat2 = 0;
		}
		
		if(a2.perdreVie(degat1, this)){
			if(a1.perdreVie(degat2, this)){
				if(degat1 > degat2){
					while(this.Deplacer(a2, new Position(a2.getPos().getX() + (rand.nextInt(2) - 1),a2.getPos().getY() + (rand.nextInt(2) - 1)))){}
					return true;
				}
				return false;
			}
		}
		return true;
	}
	public synchronized void upDate(ArrayList <Action> actionList){
		String s = new String();
		for(Action action : actionList){
			if(!action.action(s)){
				System.out.println(s);
			}
		}
	}
}
