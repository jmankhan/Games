package Chess;

import java.awt.Rectangle;
import java.util.ArrayList;

public class King extends Piece
{
	private boolean check=false;
	
	public King(int i)
	{
		im = new ImageHandler();
		possibleMoves=new ArrayList<Rectangle>();

		name = "king";
		image = im.getImg(i);
		x=0;
		y=0;
	}

	public ArrayList<Rectangle> getMovesList(boolean turn, Piece[] p1, Piece[] p2)
	{
		possibleMoves.add(new Rectangle(x, y-SIZE, SIZE, SIZE));
		possibleMoves.add(new Rectangle(x+SIZE, y-SIZE, SIZE, SIZE));
		possibleMoves.add(new Rectangle(x+SIZE, y, SIZE, SIZE));
		possibleMoves.add(new Rectangle(x+SIZE, y+SIZE, SIZE, SIZE));
		possibleMoves.add(new Rectangle(x, y+SIZE, SIZE, SIZE));
		possibleMoves.add(new Rectangle(x-SIZE, y+SIZE, SIZE, SIZE));
		possibleMoves.add(new Rectangle(x-SIZE, y, SIZE, SIZE));
		possibleMoves.add(new Rectangle(x-SIZE, y-SIZE, SIZE, SIZE));

		if(turn)
		{
			for(Piece a:p1)
			{
				for(int i=0;i<possibleMoves.size();i++)
				{
					if(!possibleMoves.isEmpty()&&a.getLocation().contains(possibleMoves.get(i)))
						possibleMoves.remove(i--);
				}
			}
		}
		else
		{
			for(Piece b:p2)
			{
				for(int i=0;i<possibleMoves.size();i++)
				{
					if(!possibleMoves.isEmpty()&&b.getLocation().contains(possibleMoves.get(i)))
						possibleMoves.remove(i--);
				}
			}
		}
		removeExtras();
		return possibleMoves;
	}
}
