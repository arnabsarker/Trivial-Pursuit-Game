import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Set;
import java.util.TreeSet;
/**
 * 
 * @author arnabsarker
 * A class to represent each player and its number of wedges
 * It also provides a method to draw the user's game piece
 */
public class Player {
	
	private Set<QType> wedges;
	private int pos_x;
	private int pos_y;
	private int id;
	
	public static final int RADIUS = 10;

	public Player(int x, int y, int d){
		wedges = new TreeSet<QType>();
		pos_x = x;
		pos_y = y;
		id = d;
	}
	
	public void addWedge(QType c){
		wedges.add(c);
	}
	
	public int numberWedges(){
		return wedges.size();
	}
	
	public void move(int x, int y){
		pos_x = x;
		pos_y = y;
	}
	
	public int getPos(){
		return pos_x;
	}
	
	public int getInnerPos(){
		return pos_y;
	}
	
	public void draw(Graphics g, int boardwidth, Color c){
		g.setColor(c);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.drawOval((int) (boardwidth / 2 + xtransform(pos_x, boardwidth) + 
				                 innerXTransform(pos_x, pos_y, boardwidth)), 
				    (int) (boardwidth / 2 + ytransform(pos_x, boardwidth) + 
						         innerYTransform(pos_x, pos_y, boardwidth)), 
				    RADIUS * 2, RADIUS * 2);
		g2.fillArc((int) (boardwidth / 2 + xtransform(pos_x, boardwidth) - RADIUS / 2 + 
				                 innerXTransform(pos_x, pos_y, boardwidth)), 
				   (int) (boardwidth / 2 + ytransform(pos_x, boardwidth) - RADIUS / 2 + 
						         innerYTransform(pos_x, pos_y, boardwidth)),
				    RADIUS * 3, RADIUS * 3, 0, 60 * numberWedges());
	}
	
	private double xtransform(int xval, int bw){
		return ((bw / 2 - 2 * RADIUS) * Math.cos((double) xval * Math.PI / 21.0));
	}
	
	private double ytransform(int xval, int bw){
		return -((bw / 2 - 2 * RADIUS) * Math.sin((double) xval * Math.PI / 21.0));
	}
	
	private double innerXTransform(int xval, int yval, int bw){
		double radinc = bw * yval / 14.5;
		switch (xval / 7){
		  case 0: return radinc * -1;
		  case 1: return radinc * (-1 / 2.0);
		  case 2: return radinc * (1 / 2.0);
		  case 3: return radinc * (1);
		  case 4: return radinc * (1 / 2.0);
		  case 5: return radinc * (-1 / 2.0);
		  default: return 0.0;
		}
	}
	
    private double innerYTransform(int xval, int yval, int bw){
    	double radinc = bw * yval / 14.5;
		switch (xval / 7){
		  case 0: return 0.0;
		  case 1: return radinc * (Math.sqrt(3.0) / 2.0);
		  case 2: return radinc * (Math.sqrt(3.0) / 2.0);
		  case 3: return 0.0;
		  case 4: return radinc * (-Math.sqrt(3.0) / 2.0);
		  case 5: return radinc * (-Math.sqrt(3.0) / 2.0);
		  default: return 0.0;
		}
	}
    
    /**
     * indicates the the status of the game when it is a players turn
     */
    @Override
    public String toString(){
    	String wlist = "";
    	for(QType c : wedges){
    		wlist += c + " ";
    	}
    	return "Player " + id + "<br>Wedges: " + wlist + "</html>";
    }
