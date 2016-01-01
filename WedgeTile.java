public class WedgeTile extends Tile {

	WedgeTile(QType c){
		super(c);
	}
	
	public void giveWedge(Player p){
		p.addWedge(category);
	}
	
	@Override
	public String toString(){
		return category + " Wedge";
	}
}
