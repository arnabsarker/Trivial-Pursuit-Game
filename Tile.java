
public class Tile implements Comparable<Tile>{
	protected QType category;
	protected Player player;
	protected boolean used;
	
	public Tile (QType c){
		if(c == QType.WIN && !(this instanceof WinningTile)){
			throw new IllegalArgumentException();
		}
		category = c;
		used = false;
		player = null;
	}
	
	public boolean hasUser(){
		return used;
	}
	
	public void landedOn(Player p){
		player = p;
		used = true;
	}
	
	public void left(){
		player = null;
		used = false;
	}
	
	public QType getType(){
		return category;
	}
	
	@Override
	public String toString(){
		return (category + "");
	}

	@Override
	public int compareTo(Tile o) {
		return this.toString().compareTo(o.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (category != other.category)
			return false;
		return true;
	}
	
	
}
