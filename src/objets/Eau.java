package objets;

public class Eau extends Objet{
	public Eau(boolean franchissable, Position pos){
		super(franchissable,pos);
	}
	public Eau(Eau e){
		super((Objet)e);
	}
}
