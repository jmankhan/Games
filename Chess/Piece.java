package Chess;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Piece 
{
	protected String name;
	protected Image image;
	protected int x,y;
	protected final int SIZE = 64;
	protected ImageHandler im;
	protected ArrayList<Rectangle> possibleMoves;
	protected boolean selected;

	public String getName()
	{
		return name;
	}
	public Image getImage()
	{
		return image;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setLocation(Point p)
	{
		x=(int) p.getX();
		y=(int) p.getY();
	}
	public Rectangle getLocation()
	{
		return new Rectangle(x,y,SIZE,SIZE);
	}
	
	public void setX(int sx)
	{
		x=sx;
	}
	public void setY(int sy)
	{
		y=sy;
	}
	public ArrayList<Rectangle> getMovesList(boolean turn, Piece[] p1, Piece[] p2)
	{
		return possibleMoves;
	}
	public void setSelected(boolean s)
	{
		selected=s;
	}
	public boolean isSelected()
	{
		return selected;
	}
	public void removeExtras()
	{
		for(int i=0;i<possibleMoves.size();i++)
		{
			double px=possibleMoves.get(i).getX();
			double py=possibleMoves.get(i).getY();

			if(px<0 || px>7*SIZE || py<0 || py>7*SIZE)
			{
				possibleMoves.remove(i);
				i--;
			}
		}		
	}
}