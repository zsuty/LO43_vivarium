package objets;

public class Rocher extends Objet{
	public Rocher(Position pos){
		super(false,pos);
	}
	public Rocher(Rocher r){
		super((Objet)r);
	}

}
