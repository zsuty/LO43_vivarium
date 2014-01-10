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
	
	public Nourriture (Nourriture n)
	{
		super (n);
		this.quantite = n.quantite;
		this.avarie = n.avarie;
		this.viande = n.viande;
	}
	
	public boolean isViande() {
		return viande;
	}
	
	public int getQuantite()
	{
		return quantite;
	}
	
	public int getAvarie()
	{
		return avarie;
	}
	
	public void setQuantite(int nb)
	{
		if (nb < 0)
		{
			quantite = 0;
		}
		else
		{
			quantite = nb;
		}
	}
	
	public void setAvarie(int nb)
	{
		if (nb < 0)
		{
			avarie = 0;
		}
		else
		{
			avarie = nb;
		}
	}
	
	public int manger()
	{
		if (quantite > 20)
		{
			quantite -= 20;
			return 20; 
		}
		else
		{
			int res = quantite;
			quantite = 0;
			return res;
		}
	}
}
