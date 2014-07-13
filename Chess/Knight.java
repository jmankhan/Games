package Chess;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Knight extends Piece 
{
	
	public Knight(int i)
	{
		im = new ImageHandler();
		possibleMoves = new ArrayList<Rectangle>();
		
		name = "knight";
		image = im.getImg(i);
		x=0;
		y=0;
		
	}
	
	public ArrayList<Rectangle> getMovesList(boolean turn, Piece[] p1, Piece[] p2)
	{
		if(turn)
		{
			possibleMoves.add(new Rectangle(x+SIZE, y-2*SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x-SIZE, y-2*SIZE, SIZE, SIZE));
			
			possibleMoves.add(new Rectangle(x+SIZE, y+2*SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x-SIZE, y+2*SIZE, SIZE, SIZE));
			
			possibleMoves.add(new Rectangle(x+2*SIZE, y+SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x+2*SIZE, y-SIZE, SIZE, SIZE));
			
			possibleMoves.add(new Rectangle(x-2*SIZE, y+SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x-2*SIZE, y-SIZE, SIZE, SIZE));
			
			for(Piece a:p1)
			{
				for(int i=0;i<possibleMoves.size();i++)
				{
					if(a.getLocation().contains(possibleMoves.get(i)))
						possibleMoves.remove(i--);
				}
			}
		}
		else
		{
			possibleMoves.add(new Rectangle(x+SIZE, y-2*SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x-SIZE, y-2*SIZE, SIZE, SIZE));
			
			possibleMoves.add(new Rectangle(x+SIZE, y+2*SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x-SIZE, y+2*SIZE, SIZE, SIZE));
			
			possibleMoves.add(new Rectangle(x+2*SIZE, y+SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x+2*SIZE, y-SIZE, SIZE, SIZE));
			
			possibleMoves.add(new Rectangle(x-2*SIZE, y+SIZE, SIZE, SIZE));
			possibleMoves.add(new Rectangle(x-2*SIZE, y-SIZE, SIZE, SIZE));
			
			for(Piece a:p2)
			{
				for(int i=0;i<possibleMoves.size();i++)
				{
					if(a.getLocation().contains(possibleMoves.get(i)))
						possibleMoves.remove(i--);
				}
			}
		}
		
		removeExtras();
		return possibleMoves;
	}
}
