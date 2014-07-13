package Chess;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Space extends Piece
{
	private Rectangle area;
	
	public Space()
	{
		name = "space";
		possibleMoves = new ArrayList<Rectangle>();
	}
	
	public Space(int x, int y, int w, int h)
	{
		name = "space";
		area = new Rectangle(x,y,w,h);
	}
	
	public Rectangle getLocation()
	{
		return area;
	}
	
}
