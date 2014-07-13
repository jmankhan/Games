package Chess;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Board extends JPanel
{
	private final int SIZE = 64;
	private final int ROW = 8;
	private final int COL = 8;

	private Player1 p1;
	private Player2 p2;
	private boolean turn=true;//true is turn 1, false is turn 2

	private MouseAdapter mouse;
	private ArrayList<Rectangle> possibleMoves;
	private ArrayList<Rectangle> previousMoves;
	private Piece toMove;
	private Piece[][] grid;
	private int fontSize=20;
	private JTextArea msg;
	private String[] letters = {"A","B","C","D","E","F","H","I"};
	
	/**
	 * Constructor, initialize field variables and add mouseadapter
	 */
	public Board()
	{
		initialize();
		addMouseListener(mouse);
		createMessageBoard();
	}

	/**
	 * draw everything to the screen, updates every mouse click
	 */
	public void paintComponent(Graphics gr)
	{
		super.paintComponent(gr);

		Graphics2D g = (Graphics2D) gr;
		g.translate(35, 25);
		for(int i=0;i<ROW;i++)
		{
			g.drawString(letters[i], i*SIZE+30, -10);
			g.drawString(Integer.toString(i+1), -10, i*SIZE+30);
			for(int j=0;j<COL;j++)
			{
				g.draw(grid[i][j].getLocation());
				if(i%2==0&&j%2==0 || i%2==1&&j%2==1)
					g.fill(grid[i][j].getLocation());
			}
		}

		for(Piece p:p1.getPieces())
			g.drawImage(p.getImage(), p.getX(), p.getY(),null);
		for(Piece p:p2.getPieces())
			g.drawImage(p.getImage(), p.getX(), p.getY(),null);

		if(!possibleMoves.isEmpty())
		{
			for(Rectangle r:possibleMoves)
			{
				g.setColor(Color.green);
				g.setStroke(new BasicStroke(3));
				g.draw(r);
			}
		}
		
	}

	/**
	 * method that handles all field variable instantiation
	 */
	public void initialize()
	{
		p1 = new Player1();
		p2 = new Player2();
		possibleMoves = new ArrayList<Rectangle>();
		previousMoves = new ArrayList<Rectangle>();
		toMove = null;
		grid = new Piece[ROW][COL];
		for(int i=0;i<ROW;i++)
		{
			for(int j=0;j<COL;j++)
			{
				grid[i][j]=new Space(i*SIZE,j*SIZE,SIZE,SIZE); 
			}
		}

		createMouseAdapter();
	}

	/**
	 * determines what was clicked on the screen
	 * @param e mouseevent
	 * @return type of space clicked
	 */
	public Piece getClickedPos(MouseEvent e)
	{
		Piece p = new Space();

		Point click = new Point(e.getX()-35,e.getY()-25);
		if(turn)
		{
			for(Piece a:p1.getPieces())
				if(a.getLocation().contains(click))
					p=a;
		}
		else
		{
			for(Piece b:p2.getPieces())
				if(b.getLocation().contains(click))
					p=b;
		}

		for(Rectangle r:possibleMoves)
		{
			if(r.contains(click))
			{
				for(int i=0;i<ROW;i++)
				{
					for(int j=0;j<COL;j++)
					{
						if(grid[i][j].getLocation().contains(r))
							p=grid[i][j];
					}
				}

			}
		}
		return p;
	}
	
	/**
	 * Guess what this does
	 */
	public void createMouseAdapter()
	{
		mouse = new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				if(!(getClickedPos(e) instanceof Space))
				{
					toMove=getClickedPos(e);
					possibleMoves = toMove.getMovesList(turn, p1.getPieces(), p2.getPieces());
				}
				if(!possibleMoves.isEmpty())
				{
					for(Rectangle r:possibleMoves)
					{
						if(r.contains(getClickedPos(e).getLocation().getLocation()))
						{
							if(turn)
								msg.append("W");
							else
								msg.append("B");
							msg.append(toMove.getName().substring(0,1).toUpperCase() + " " + letters[toMove.getX()/SIZE] + (toMove.getY()/SIZE+1));
							
							toMove.setLocation(r.getLocation());
							
							msg.append(" to " + letters[toMove.getX()/SIZE] + (toMove.getY()/SIZE+1)+"\n");
							if(toMove instanceof Pawn)
							{
								((Pawn)toMove).setTurnCount(((Pawn) toMove).getTurnCount()+1);
								if(turn&&toMove.getY()==0 || !turn&&toMove.getY()==7*SIZE)
									heroPawnDialog();
							}

							if(checkCollision())
								removePiece(r);

							toMove=null;
							previousMoves = possibleMoves;
							possibleMoves.clear();
							turn=!turn;
							break;
						}
					}
				}
				repaint();
			}
		};
	}

	/**
	 * checks if player kills enemy piece
	 * @return true if landed on enemy piece
	 */
	public boolean checkCollision()
	{
		if(turn)
		{
			for(Piece a:p1.getPieces())
			{
				for(Piece b:p2.getPieces())
				{
					if(a.getLocation().contains(b.getLocation()))
						return true;
				}
			}
		}
		else
		{
			for(Piece a:p1.getPieces())
			{
				for(Piece b:p2.getPieces())
				{
					if(a.getLocation().contains(b.getLocation()))
						return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * removes piece on specified location, this is checked after move is made
	 * so the attacking piece needs to be determined here
	 * @param r the location of piece to be removed
	 */
	public void removePiece(Rectangle r)
	{
		if(turn)
		{
			Piece[] p2p=p2.getPieces();
			for(int i=0;i<p2p.length;i++)
			{
				if(r.contains(p2p[i].getLocation()))
				{
					p2p[i].setLocation(new Point(-200,0));
					if(p2p[i] instanceof King)
						gameOver();
				}
			}
		}
		else
		{
			Piece[] p1p=p1.getPieces();
			for(int i=0;i<p1p.length;i++)
			{
				if(r.contains(p1p[i].getLocation()))
				{
					p1p[i].setLocation(new Point(-200,0));
					if(p1p[i] instanceof King)
						gameOver();
				}
			}
		}
	}
	

	/**
	 * Brings up options for evolving pawn upong reaching enemy backline
	 */
	public void heroPawnDialog()
	{
		String[] buttons = { "Queen", "Bishop", "Knight", "Rook" };    
		int returnValue = JOptionPane.showOptionDialog(null, "Hero Pawn", "What would you like hero pawn to become?",
		        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0]);

		if(turn&&toMove.getY()==0)
		{
			Piece[] p1p=p1.getPieces();
			for(int i=0;i<p1p.length;i++)
			{
				if(p1p[i].getLocation().contains(toMove.getLocation()))
				{
					if(returnValue==0)
						p1.setPiece(i, 4);
					else if(returnValue==1)
						p1.setPiece(i, 1);
					else if(returnValue==2)
						p1.setPiece(i, 2);
					else
						p1.setPiece(i, 3);
					
					p1p[i].setLocation(toMove.getLocation().getLocation());
				}
			}
		}
		else if(!turn&&toMove.getY()==7*SIZE)
		{
			Piece[] p2p=p2.getPieces();
			for(int i=0;i<p2p.length;i++)
			{
				if(p2p[i].getLocation().contains(toMove.getLocation()))
				{
					if(returnValue==0)
						p2.setPiece(i, 10);
					else if(returnValue==1)
						p2.setPiece(i, 7);
					else if(returnValue==2)
						p2.setPiece(i, 8);
					else
						p2.setPiece(i, 9);
					
					p2p[i].setLocation(toMove.getLocation().getLocation());
				}
			}
		}
	}
	
	/**
	 * I still can't tell if this is a pun or not
	 * @return true if king is in check
	 */
	public boolean checkKing()
	{
		Piece king = null;
		if(turn)
		{	for(Piece p:p1.getPieces())
				if(p instanceof King)
					king=p;
		}
		else 
		{	for(Piece p:p2.getPieces())
				if(p instanceof King)
					king=p;
		}

		for(Rectangle r:previousMoves)
		{
			if(r.contains(king.getLocation()))
			{
				if(turn)
					p1.setChecked(true);
				else
					p2.setChecked(true);
				return true;
			}
		}
		
		return false;
	}
	
	public void createUncheckingMoves()
	{
		ArrayList<Rectangle> tempList = new ArrayList<Rectangle>();
		Piece tempMove= toMove;
		if(p1.isChecked())
		{
			for(Piece a:p1.getPieces())
			{
				for(Rectangle r:a.getMovesList(turn, p1.getPieces(), p2.getPieces()))
				{
					 toMove.setLocation(r.getLocation());
					 if(!checkKing())
						 tempList.add(r);
				}
			}
		}
		else
		{
			for(Piece b:p2.getPieces())
			{
				for(Rectangle r:b.getMovesList(turn, p1.getPieces(), p2.getPieces()))
				{
					toMove.setLocation(r.getLocation());
					if(!checkKing())
						tempList.add(r);
				}
			}
		}
		toMove = tempMove; //reset
		final ArrayList<Rectangle> finalList = tempList;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				possibleMoves.retainAll(finalList);
			}
		});
	}
	
	public void gameOver()
	{
		if(turn)
			JOptionPane.showMessageDialog(null, "player 1 win");
		else
			JOptionPane.showMessageDialog(null, "player 2 win");
	}
	
	public void playCheckAnimation()
	{
		final Graphics2D g = (Graphics2D) getGraphics();
		Timer timer = new Timer(500, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				g.setFont(new Font("Arial", Font.PLAIN, fontSize+=5));
				g.drawString("Check", getWidth()/2, getHeight()/2);
			}
		});
		timer.start();
	}
	
	public void createMessageBoard()
	{
		setLayout(new BorderLayout());
		msg = new JTextArea("Game Start\n");
		msg.setEditable(false);
		msg.setRows(10);
		msg.setColumns(10);
		JScrollPane scroll = new JScrollPane(msg);
		add(scroll, BorderLayout.EAST);
	}
	
	public static void main(String args[])
	{
		JFrame f=new JFrame();
		f.add(new Board());

		f.setVisible(true);
		f.setSize(700,600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
