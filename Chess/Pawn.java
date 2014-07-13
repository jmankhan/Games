package Chess;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Pawn extends Piece
{
	private int turnCount;

	public Pawn(int i)
	{
		im = new ImageHandler();
		possibleMoves = new ArrayList<Rectangle>();

		name = "pawn";
		image = im.getImg(i);
		x=0;
		y=0;

		turnCount=0;
	}

	public ArrayList<Rectangle> getMovesList(boolean turn, Piece[] p1, Piece[] p2)
	{
		if(turn&&turnCount==0)
		{
			possibleMoves.add(new Rectangle(x,y-SIZE,SIZE,SIZE));
			possibleMoves.add(new Rectangle(x,y-2*SIZE,SIZE,SIZE));
		}
		else if(turn&&turnCount>0)
			possibleMoves.add(new Rectangle(x,y-SIZE,SIZE,SIZE));
		if(!turn&&turnCount==0)
		{
			possibleMoves.add(new Rectangle(x,y+SIZE,SIZE,SIZE));
			possibleMoves.add(new Rectangle(x,y+2*SIZE,SIZE,SIZE));
		}
		else if(!turn&&turnCount>0)
			possibleMoves.add(new Rectangle(x,y+SIZE,SIZE,SIZE));


		if(turn)
		{
			Rectangle attackPos = new Rectangle(x-SIZE, y-SIZE, SIZE, SIZE);
			Rectangle attackPos2 = new Rectangle(x+SIZE, y-SIZE, SIZE, SIZE);
			//if enemy is available for attacking or is blocking path
			for(Piece a:p2)
			{
				if(!possibleMoves.isEmpty() && a.getLocation().contains(possibleMoves.get(0)))
					possibleMoves.remove(0);
				if(a.getLocation().contains(attackPos))
					possibleMoves.add(attackPos);
				if(a.getLocation().contains(attackPos2))
					possibleMoves.add(attackPos2);
			}
			//if friendly is blocking path
			for(Piece b:p1)
			{
				if(!possibleMoves.isEmpty() && b.getLocation().contains(possibleMoves.get(0)))
					possibleMoves.remove(0);
			}
		}
		else
		{
			Rectangle attackPos = new Rectangle(x-SIZE, y+SIZE, SIZE, SIZE);
			Rectangle attackPos2 = new Rectangle(x+SIZE, y+SIZE, SIZE, SIZE);
			for(Piece a:p1)
			{
				if(!possibleMoves.isEmpty() && a.getLocation().contains(possibleMoves.get(0)))
					possibleMoves.remove(0);
				if(a.getLocation().contains(attackPos))
					possibleMoves.add(attackPos);
				if(a.getLocation().contains(attackPos2))
					possibleMoves.add(attackPos2);
			}
			for(Piece b:p2)
			{
				if(!possibleMoves.isEmpty() && b.getLocation().contains(possibleMoves.get(0)))
					possibleMoves.remove(0);
			}
		}
		removeExtras();
		return possibleMoves;
	}

	public void setTurnCount(int c)
	{
		turnCount=c;
	}
	public int getTurnCount()
	{
		return turnCount;
	}
}
