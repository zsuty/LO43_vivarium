package objets;

public class Nourriture extends Objet{
	private int quantite;
	private int avarie;
	private boolean viande;
	
	public Nourriture (Position pos,boolean type,int quantite,int avarie){
		super(true,pos);
		this.avarie = avarie;
		this.quantite = quantite;
		this.viande = type;
	}
	
	public Nourriture(Nourriture n){
		super((Objet)n);
		this.avarie = n.getAvarie();
		this.viande = n.isViande();
		this.quantite = n.getQuantite();
	}
	
	public boolean isViande() {
		return viande;
	}
	public int getAvarie() {
		return avarie;
	}
	public int getQuantite() {
		return quantite;
	}
}
