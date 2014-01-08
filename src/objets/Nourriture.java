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
}
