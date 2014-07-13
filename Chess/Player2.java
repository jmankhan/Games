package Chess;

public class Player2 
{

	private final int SIZE = 64;
	private final int ROW = 0;
	
	private final int PAWN=6;
	private final int BISHOP=7;
	private final int KNIGHT=8;
	private final int ROOK=9;
	private final int QUEEN=10;
	private final int KING=11;
	
	private boolean check=false;
	private Piece[] pieces = new Piece[16];
	/**
	 * this is the black player
	 */
	public Player2()
	{
		//pawns
		for(int i = 0; i < 8; i++)
		{
			pieces[i] = new Pawn(PAWN);
			pieces[i].setX(i*SIZE);
			pieces[i].setY(SIZE);
		}
		
		pieces[8] = new Rook(ROOK);
		pieces[8].setX(0);
		pieces[8].setY(ROW);
		
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
	
	public void setChecked(boolean b)
	{
		check=b;
	}
	public boolean isChecked()
	{
		return check;
	}
}
