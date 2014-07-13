package Chess;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Rook extends Piece
{
	private Piece[] p1p, p2p;
	private int count=0;
	
	public Rook(int i)
	{
		im = new ImageHandler();
		possibleMoves = new ArrayList<Rectangle>();

		name = "rook";
		image = im.getImg(i);
		x=0;
		y=0;
		
		p1p=new Piece[16];
		p2p=new Piece[16];
	}

	/**
	 * why are recursive methods actually useful
	 * @param turn
	 * @param p1
	 * @param p2
	 * @return
	 */
	public ArrayList<Rectangle> getMovesList(boolean turn, Piece[] p1, Piece[] p2)
	{
		p1p=p1;
		p2p=p2;
		
		loop(0,turn,x,y);
		loop(2,turn,x,y);
		loop(4,turn,x,y);
		loop(6,turn,x,y);
		return possibleMoves;
	}
	
	public void loop(int dir, boolean turn, int x, int y)
	{
		Rectangle toAdd=new Rectangle(-100,-100,SIZE,SIZE);
		switch(dir)
		{
		case 0:toAdd.setLocation(x, y-SIZE);break;
		case 1:toAdd.setLocation(x+SIZE, y-SIZE);break;
		case 2:toAdd.setLocation(x+SIZE, y);break;
		case 3:toAdd.setLocation(x+SIZE, y+SIZE);break;
		case 4:toAdd.setLocation(x, y+SIZE);break;
		case 5:toAdd.setLocation(x-SIZE, y+SIZE);break;
		case 6:toAdd.setLocation(x-SIZE, y);break;
		case 7:toAdd.setLocation(x-SIZE, y-SIZE);break;
		}

		possibleMoves.add(toAdd);
		if(toAdd.getX()<0||toAdd.getX()>7*SIZE||toAdd.getY()<0||toAdd.getY()>7*SIZE)
		{
			possibleMoves.remove(possibleMoves.size()-1);
			return;
		}
		if(turn)
		{
			for(Piece a:p1p)
			{
				if(a.getLocation().contains(toAdd))
				{
					possibleMoves.remove(possibleMoves.size()-1);
					return;
				}
			}
			for(Piece b:p2p)
			{
				if(b.getLocation().contains(toAdd))
					return;
			}
		}
		else
		{
			for(Piece a:p2p)
			{
				if(a.getLocation().contains(toAdd))
				{
					possibleMoves.remove(possibleMoves.size()-1);
					return;
				}
			}
			for(Piece b:p1p)
			{
				if(b.getLocation().contains(toAdd))
					return;
			}
		}
		
		loop(dir,turn, toAdd.x, toAdd.y);
	}
}
