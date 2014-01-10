package objets;

import java.util.ArrayList;
import java.util.Random;

import map.Map;
import Action.Action;
import Action.Ajouter;
import Action.Deplacer;
import Action.Mourrir;
import Action.SupprimerObjet;
import Action.Suprimer;

public class Antilope extends Animal{
	public Antilope(Position pos){
		super(pos,5);
		this.attaque = 1;
		this.defense = 1;
	}
	public Antilope(Antilope a){
		super((Animal)a);
	}
	@Override
	public ArrayList<Action> decider(Map map) 
	{
		ArrayList<Action> actions = new ArrayList<Action>();
		System.out.println("DECIDER : Antilope");
		boolean need = false;
		
		if (faim > 0)
		{
			faim--;			
		}
		else
		{
			faim = 0;
		}
		
		if (soif > 0)
		{
			soif--;			
		}
		else
		{
			soif = 0;
		}
		
		if (energie > 0)
		{
			energie--;			
		}
		else
		{
			energie = 0;
		}
		
		if (faim == 0 || soif == 0)
		{
			vie -= 1;
			if (vie == 0)
			{
				actions.add(new Mourrir(map, this.getPos(), this));
				return actions;
			}
		}

		System.out.println("Faim " + faim + ", Soif " + soif + ", Energie " + energie + ", Vie " + vie);
		
		if (compteurRepro > 0)
		{
			compteurRepro--;
		}
		
		
		ArrayList<Objet> objetsVisibles = map.getVision(this);
		trierListe(objetsVisibles);
		if (lionProche != null)
		{
			System.out.println("DECIDER : DANGER");
			Position pos = new Position(this.pos.getX(), this.pos.getY());
			actions.add(new Deplacer(map, this, fuiteAleatoire()));
		}
		else
		{
			Besoin prio = prioriserBesoins();
			switch (prio)
			{
			case MANGER:
				if (faim < 50 )
				{
					System.out.println("DECIDER : J'ai faim");
					need = true;
				}
				break;
			case BOIRE:
				System.out.println("DECIDER : ");
				if (soif < 50 )
				{
					System.out.println("DECIDER : J'ai soif");
					need = true;
				}
				break;
			case REPOS:
				if (energie < 50 )
				{
					System.out.println("DECIDER : J'ai sommeil");
					need = true;
				}
				break;
			}
			ArrayList<Position> listePos;
			if (need)	// Besoin à satisfaire
			{
				switch (prio)
				{
					case MANGER:
						if(herbeProche != null)
						{
							System.out.println("DECIDER : manger");
							if (herbeProche.getPos().getX() == this.getPos().getX() && herbeProche.getPos().getY() == this.getPos().getY()) 
							{
								if (herbeProche.getQuantite() <= 20 && herbeProche.getQuantite() > 0)
								{
									faim += herbeProche.manger();
									actions.add(new SupprimerObjet(map, herbeProche));
								}
								else if (herbeProche.getQuantite() > 20)
								{
									faim += herbeProche.manger();
								}
							}
							else 
							{
								listePos = map.getDirection(this, herbeProche.getPos(),vitesse,false);
								for (Position p : listePos)
								{
									actions.add(new Deplacer(map, this,p));								
								}
							}
						}
						else
						{
							listePos = deplacement(choisirDirectionAleatoire());
							for (Position p : listePos)
							{
								actions.add(new Deplacer(map, this,p));								
							}
						}
						break;
					case BOIRE:
						if (eauProche != null)
						{
							if (eauProche.getPos().getX() == this.getPos().getX() && eauProche.getPos().getY() == this.getPos().getY()) 
							{
								soif += 20;
							}
							else
							{
								listePos = map.getDirection(this, eauProche.getPos(), vitesse, false);
								for (Position p : listePos)
								{
									actions.add(new Deplacer(map, this,p));								
								}
							}
						}
						else
						{
							listePos = deplacement(choisirDirectionAleatoire());
							for (Position p : listePos)
							{
								actions.add(new Deplacer(map, this,p));								
							}
						}
						break;
					case REPOS:
						energie += 10;
						vie += 1;
						break;
				}
			}
			else	// rien à faire
			{
				System.out.println("DECIDER : Rien a faire");
				Random rand = new Random();
				int choix = rand.nextInt(51);
				System.out.println("DECIDER : random est fait");
				if (choix < 40)
				{
					System.out.println("DECIDER : choix < 40");
					if (compteurRepro == 0)
					{
						System.out.println("DECIDER : compteur repro == 0");
						if (antilopeProcheReproduction != null)
						{
							System.out.println("DECIDER : antilopeprocherepro != null");
							if (antilopeProcheReproduction.getPos().getX() == this.getPos().getX()-1 || antilopeProcheReproduction.getPos().getX() == this.getPos().getX() || antilopeProcheReproduction.getPos().getX() == this.getPos().getX() +1) 
							{
								System.out.println("DECIDER : pos x is good");
								if (antilopeProcheReproduction.getPos().getY() == this.getPos().getY() -1 || antilopeProcheReproduction.getPos().getY() == this.getPos().getY() || antilopeProcheReproduction.getPos().getY() == this.getPos().getY() +1)
								{
									System.out.println("DECIDER : pos y is good");
									System.out.println("DECIDER : reproduction");
									//Reproduction
									antilopeProcheReproduction.setCompteurRepro(100);
									antilopeProcheReproduction.setReproduction(false);
									this.setCompteurRepro(100);
									this.setReproduction(false);
									System.out.println("DECIDER : demande une position libre a côté de lui");
									ArrayList<Position> pos = map.getDirection(this, this.pos, 1, true);
									actions.add(new Ajouter(map, new Antilope(pos.get(0))));
								}
							}
						}
					}
				}
				else
				{
					System.out.println("DECIDER : aléatoire");
					listePos = deplacement(choisirDirectionAleatoire());
					actions.add(new Deplacer(map, this,listePos.get(0)));
				}
			}
		}
		// TODO Auto-generated method stub
		return actions;
	}

}
