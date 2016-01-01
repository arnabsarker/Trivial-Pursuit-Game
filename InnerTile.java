
public class InnerTile extends Tile {	
	InnerTile(QType c){
		super(c);
	}
	
	@Override
	public String toString(){
		return "Inner Tile " + category;
	}
}
