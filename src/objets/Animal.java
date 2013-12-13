package objets;

public /*abstract*/ class  Animal extends Objet{
	protected int vie;
	protected int faim;
	protected int energie;
	protected int soif;
	protected int vitesse;
	protected int attaque;
	protected int defense;
	protected int visionRange;
	protected int numeroMeute;
	
	protected boolean male;
	
	public Animal(int x, int y, int r){
		super(x,y);
		this.visionRange = r;
	}
	
	//---------- Getters ----------
	public int getVie() {
		return vie;
	}
	public int getFaim() {
		return faim;
	}
	public int getEnergie() {
		return energie;
	}
	public int getSoif() {
		return soif;
	}
	public int getVitesse() {
		return vitesse;
	}
	public int getAttaque() {
		return attaque;
	}
	public int getDefense() {
		return defense;
	}
	public int getVisionRange() {
		return visionRange;
	}
	public int getNumeroMeute() {
		return numeroMeute;
	}
}
