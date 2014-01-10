/*
	Fichier: Animal.java
	Auteur: Zephyr Suty
	Version: 1.0
	Date: 13/12/2013

	Package : map
	Contient: class Animal

	Description:
	Animal herite d'Objet et represente un animal qui se deplace sur la Map.
*/

package objets;


import java.util.ArrayList;
import java.util.Random;

import Action.Action;
import map.Map;

public abstract class  Animal extends Objet

{
	enum Besoin { MANGER, BOIRE, REPOS };
	enum Objectif { ANTILOPE, LION, HERBE, VIANDE, EAU };
	enum Direction { H, HD, D, DB, B, BG, G, GH, RIEN};
	
	protected int vie;
	protected int faim;
	protected int energie;
	protected int soif;
	protected int vitesse;
	protected int attaque;
	protected int defense;
	protected int visionRange;
	protected int numeroMeute;
	protected int compteurRepro;
	protected boolean reproduction;
	protected boolean male;
	
	protected Nourriture herbeProche = null;
	protected Nourriture viandeProche = null;
	protected Eau eauProche = null;
	protected Antilope antilopeProche = null;
	protected Antilope antilopeProcheReproduction = null;
	protected Lion lionProche = null;
	
	Direction direction;
	
	public Animal()
	{
		super(false);
		this.vie = 50;
		this.vitesse = 1;
		this.attaque = 10;
		this.defense = 5;
		this.visionRange = 3;
		this.numeroMeute = 1;
		this.faim = 50;
		this.soif = 100;
		this.energie = 100;
		this.compteurRepro = 50;
		this.direction = Direction.RIEN;
		herbeProche = null;
		viandeProche = null;
		eauProche = null;
		antilopeProche = null;
		antilopeProcheReproduction = null;
		lionProche = null;
	}
	
	public Animal (Position pos, boolean franchissable, int vie, int vitesse, int attaque, int defense, int visionRange, int numeroMeute, int faim, int soif, int energie, int compteurRepro)
	{
		super(franchissable, pos);
		this.vie = vie;
		this.vitesse = vitesse;
		this.attaque = attaque;
		this.defense = defense;
		this.visionRange = visionRange;
		this.numeroMeute = numeroMeute;
		this.faim = faim;
		this.soif = soif;
		this.energie = energie;
		this.compteurRepro = compteurRepro;
		this.direction = Direction.RIEN;
		herbeProche = null;
		viandeProche = null;
		eauProche = null;
		antilopeProche = null;
		antilopeProcheReproduction = null;
		lionProche = null;
	}
	
	public Animal(Position p, int visionRange)
	{
		super(false, p);
		this.vie = 100;
		this.vitesse = 1;
		this.attaque = 10;
		this.defense = 5;
		this.visionRange = visionRange;
		this.numeroMeute = 1;
		this.faim = 50;
		this.soif = 100;
		this.energie = 100;
		this.compteurRepro = 50;
		this.direction = Direction.RIEN;
		herbeProche = null;
		viandeProche = null;
		eauProche = null;
		antilopeProche = null;
		antilopeProcheReproduction = null;
		lionProche = null;
	}
	
	public Animal(Animal a)
	{
		super(a);
		this.vie = a.vie;
		this.vitesse = a.vitesse;
		this.attaque = a.attaque;
		this.defense = a.defense;
		this.visionRange = a.visionRange;
		this.numeroMeute = a.numeroMeute;
		this.faim = a.faim;
		this.soif = a.soif;
		this.energie = a.energie;
		this.compteurRepro = a.compteurRepro;
		this.direction = a.direction;
		herbeProche = null;
		viandeProche = null;
		eauProche = null;
		antilopeProche = null;
		antilopeProcheReproduction = null;
		lionProche = null;
	}
	//---------- Getters ----------
	
		public int getVie() 
		{
			return vie;
		}
		
		public int getFaim() 
		{
			return faim;
		}
		
		public int getEnergie() 
		{
			return energie;
		}
		
		public int getSoif() 
		{
			return soif;
		}
		
		public int getVitesse() 
		{
			return vitesse;
		}
		
		public int getAttaque() 
		{
			return attaque;
		}
		
		public int getDefense() 
		{
			return defense;
		}
		
		public int getVisionRange() 
		{
			return visionRange;
		}
		
		public int getNumeroMeute() 
		{
			return numeroMeute;
		}
		
		public int getCompteurRepro() 
		{
			return compteurRepro;
		}
		
		public boolean getReproduction() 
		{
			return reproduction;
		}
		public boolean isMale() {
			return male;
		}
		public void setReproduction(boolean repro)
		{
			reproduction = repro;
		}
		
		public void setCompteurRepro(int compteur)
		{
			if (compteur < 0)
			{
				compteurRepro = 0;
			}
			else
			{
				compteurRepro = compteur;			
			}
		}
		
		public boolean perdreVie(int degat,Map m){
			this.vie -= degat;
			if(vie <= 0){
				m.supprimerObjet(this);
				Nourriture n = new Nourriture(this.pos, true, 20, 20);
				m.ajouterObjet(n);
				return false;
			}
			return true;
		}
		public Position fuiteAleatoire()
		{
			Random rand = new Random();

			//int randomNum = rand.nextInt((max - min) + 1) + min;
		    int rnd = rand.nextInt(3);
		    int x = this.pos.getX() + rnd-1;
		    rnd = rand.nextInt(3);
			int y = this.pos.getY() + rnd-1;
			return new Position(x,y);
		}
		
		public Besoin prioriserBesoins()
		{
			Besoin prio = Besoin.MANGER; 
			if (faim > soif)
			{
				prio = Besoin.BOIRE;
				if (soif > energie)
				{
					prio = Besoin.REPOS;				
				}
			}
			else if (faim > energie)
			{
				prio = Besoin.REPOS;
				if (soif < energie)
				{
					prio = Besoin.BOIRE;
				}
			}
			return prio;
		}
		
		private boolean plusProche(Objet o1, Objet o2)
		{
			if (o1 != null)
			{
				if (o2 != null)
				{
					int res1;
					int res2;
					int x1 = o1.getPos().getX();
					int y1 = o1.getPos().getY();
					int x2 = o2.getPos().getX();
					int y2 = o2.getPos().getY();
					
					x1 = x1 - this.getPos().getX();
					y1 = y1 - this.getPos().getY();
					res1 = Math.max(x1, y1);
					
					x2 = x2 - this.getPos().getX();
					y2 = y2 - this.getPos().getY();
					res2 = Math.max(x2, y2);
					
					if (res2 < res1)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				if (o2 != null)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		private boolean plusProcheRepro(Objet obj1, Objet obj2)
		{
			Animal o1 = (Animal) obj1;
			Animal o2 = (Animal) obj2;
			if (o1 != null && o1.getReproduction() == true)
			{
				if (o2 != null && o2.getReproduction() == true)
				{
					if(o1.isMale() != o2.isMale()){
						int res1;
						int res2;
						int x1 = o1.getPos().getX();
						int y1 = o1.getPos().getY();
						int x2 = o2.getPos().getX();
						int y2 = o2.getPos().getY();
					
						x1 = x1 - this.getPos().getX();
						y1 = y1 - this.getPos().getY();
						res1 = Math.max(x1, y1);
					
						x2 = x2 - this.getPos().getX();
						y2 = y2 - this.getPos().getY();
						res2 = Math.max(x2, y2);
					
						if (res2 < res1)
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else {
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				if (o2 != null)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		public void trierListe(ArrayList<Objet> objetsVisibles)
		{
			this.antilopeProche = null;
			this.antilopeProcheReproduction = null;
			this.eauProche = null;
			this.viandeProche= null;
			this.herbeProche = null;
			this.lionProche = null;
			for (Objet o : objetsVisibles)
			{
				if (o instanceof Nourriture)
				{
					if (((Nourriture) o).isViande())
					{
						if (plusProche(viandeProche, o))
						{
							viandeProche = (Nourriture)o;
						}
					}
					else
					{
						if (plusProche(herbeProche, o))
						{
							herbeProche = (Nourriture)o;
						}					
					}
				}
				else if (o instanceof Eau)
				{
					if (plusProche(eauProche, o))
					{
						eauProche = (Eau)o;
					}
				}
				else if (o instanceof Antilope)
				{
					if (plusProche(antilopeProche, o))
					{
						antilopeProche = (Antilope)o;
					}
					if (plusProcheRepro(antilopeProcheReproduction, o))
					{
						antilopeProcheReproduction = (Antilope)o;
					}
				}
				else if (o instanceof Lion)
				{
					if (plusProche(lionProche, o))
					{
						System.out.println("YA UN LION LA");
						lionProche = (Lion)o;
					}
				}
			}
		}
		
		public Direction choisirDirectionAleatoire()
		{
			Random rand = new Random();
			switch (rand.nextInt(8))
			{
				case 0 :
					return Direction.H;
				case 1 :
					return Direction.HD;
				case 2 :
					return Direction.D;
				case 3 :
					return Direction.DB;
				case 4 :
					return Direction.B;
				case 5 :
					return Direction.BG;
				case 6 :
					return Direction.G;
				case 7 :
					return Direction.GH;
			}
			return Direction.H;
		}
		
		public ArrayList<Position> deplacement(Direction dir)
		{
			int i;
			ArrayList<Position> listePos = new ArrayList<Position>();
			switch (dir)
			{
				case H :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX(), this.getPos().getY()+i)); 
					}
					break;
				case HD :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX()+i, this.getPos().getY()+i)); 
					}
					break;
				case D :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX()+i, this.getPos().getY())); 
					}
					break;
				case DB :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX()+i, this.getPos().getY()-i)); 
					}
					break;
				case B :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX(), this.getPos().getY()-i)); 
					}
					break;
				case BG :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX()-i, this.getPos().getY()-i)); 
					}
					break;
				case G :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX()-i, this.getPos().getY())); 
					}
					break;
				case GH :
					for (i = 1 ; i <= vitesse ; i++)
					{
						listePos.add(new Position(this.getPos().getX()-i, this.getPos().getY()+i)); 
					}
					break;
			default:
				break;
			}
			return listePos;
		}
		
		
	public ArrayList<Action> decider(Map map) {
		return null;
	}
}
