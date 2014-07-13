package Chess;

import java.awt.Color;

public class Player1 
{
	private final int SIZE = 64;
	private final int ROW = 7*SIZE;
	
	private final int PAWN=0;
	private final int BISHOP=1;
	private final int KNIGHT=2;
	private final int ROOK=3;
	private final int QUEEN=4;
	private final int KING=5;

	private boolean check=false;
	private Piece[] pieces = new Piece[16];
	/**
	 * this is the white player
	 */
	public Player1()
	{
		for(int i = 0; i < 8; i++)
		{
			pieces[i] = new Pawn(PAWN);
			pieces[i].setX(i*SIZE);
			pieces[i].setY(6*SIZE);
		}
		
		pieces[8] = new Rook(ROOK);
		pieces[8].setX(0);
		pieces[8].setY(7*SIZE);
		
		pieces[9] = new Rook(ROOK);
		pieces[9].setX(7*SIZE);
		pieces[9].setY(ROW);
		
		pieces[10] = new Knight(KNIGHT);
		pieces[10].setX(SIZE);
		pieces[10].setY(ROW);
		
		pieces[11] = new Knight(KNIGHT);
		pieces[11].setX(6*SIZE);
		pieces[11].setY(ROW);
		
		pieces[12] = new Bishop(BISHOP);
		pieces[12].setX(2*SIZE);
		pieces[12].setY(ROW);
		
		pieces[13] = new Bishop(BISHOP);
		pieces[13].setX(5*SIZE);
		pieces[13].setY(ROW);
		
		pieces[14] = new Queen(QUEEN);
		pieces[14].setX(4*SIZE);
		pieces[14].setY(ROW);
		
		pieces[15] = new King(KING);
		pieces[15].setX(3*SIZE);
		pieces[15].setY(ROW);
	}
	
	public Piece[] getPieces()
	{
		return pieces;
	}
	
	public void setPiece(int index, int toPiece)
	{
		switch(toPiece)
		{
		case ROOK:pieces[index]=new Rook(ROOK);break;
		case BISHOP:pieces[index]=new Bishop(BISHOP);break;
		case KNIGHT:pieces[index]=new Knight(KNIGHT);break;
		case QUEEN:pieces[index]=new Queen(QUEEN);break;
		}
	}
	
	public boolean isChecked()
	{
		return check;
	}
	
	public void setChecked(boolean b)
	{
		check=b;
	}
}
