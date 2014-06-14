package Pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener
{
	private final int SIZE = 15;

	private int x, y, dx, dy, score, time;
	private int gdx, gdy;
	private Pacman pac;
	private World world;
	private ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> pills = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> specials = new ArrayList<Rectangle>();
	private Ghost shadow, speedy, bashful, pokey;
	
	private boolean specialTime = false;
	
	private Timer timer;
	
	public Board()
	{
		System.out.println(this.getClass().getCanonicalName());
		setBackground(Color.black);
	
		world = new World();
		walls = world.getWalls();
		pills = world.getPills();
		specials = world.getSpecials();
		
		createKeys();

		dx = 0;
		dy = 0;
		x = 15*SIZE;
		y = 21*SIZE;

		pac = new Pacman(x, y, SIZE, SIZE);
		
		gdx = 0;
		gdy = 0;
		shadow = new Ghost(16*SIZE, 10*SIZE, SIZE, SIZE, Color.red);
		speedy = new Ghost(14*SIZE, 12*SIZE, SIZE, SIZE, Color.pink);
		bashful = new Ghost(15*SIZE, 12*SIZE, SIZE, SIZE, new Color(89,220,222));
		pokey = new Ghost(16*SIZE, 12*SIZE, SIZE, SIZE, Color.green);
		
		timer = new Timer(250, this);
		timer.start();
		
		score = 0;
		time = 20;
	}

	public void paintComponent(Graphics gr)
	{
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;

		for(Rectangle r : walls)
		{
			g.setColor(Color.blue);
			g.fill(r);
		}
		
		for(Rectangle r : pills)
		{
			g.setColor(Color.yellow);
			g.fillOval((int)r.getX()+SIZE/4,(int)r.getY()+SIZE/4, SIZE/2, SIZE/2);
		}
		for(Rectangle r : specials)
		{
			g.fillOval((int)r.getX(), (int)r.getY(), SIZE, SIZE);
		}
		
		g.setColor(pac.getColor());
		g.fill(pac);
		
		g.setColor(shadow.getColor());
		g.fill(shadow);
		
		g.setColor(speedy.getColor());
		g.fill(speedy);
		
		g.setColor(bashful.getColor());
		g.fill(bashful);
		
		g.setColor(pokey.getColor());
		g.fill(pokey);
		
		if(specialTime)
		{
			g.setColor(new Color(102,98,191));
			g.fill(shadow);
			g.fill(speedy);
			g.fill(bashful);
			g.fill(pokey);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(Integer.toString(score), 500, 50);
	}

	public void createKeys()
	{
		InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), "left");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");

		ActionMap am = getActionMap();
		am.put("up", goUp);
		am.put("down", goDown);
		am.put("left", goLeft);
		am.put("right", goRight);
	}

	AbstractAction goUp = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			dy=1;
			dx=0;
		}
	};
	AbstractAction goDown = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			dy=-1;
			dx=0;
		}
	};
	AbstractAction goLeft = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			dx=-1;
			dy=0;
		}
	};
	AbstractAction goRight = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			dx=1;
			dy=0;
		}
	};

	public boolean canMove()
	{
		Rectangle desiredPos = null;
		if(dx==1)
			desiredPos = new Rectangle((int)pac.getX()+SIZE, (int)pac.getY(), SIZE, SIZE);
		if(dx==-1)
			desiredPos = new Rectangle((int)pac.getX()-SIZE, (int)pac.getY(), SIZE, SIZE);
		if(dy==1)
			desiredPos = new Rectangle((int)pac.getX(), (int)pac.getY()-SIZE, SIZE, SIZE);
		if(dy==-1)
			desiredPos = new Rectangle((int)pac.getX(), (int)pac.getY()+SIZE, SIZE, SIZE);

		if(desiredPos != null)
		{
			for(Rectangle r : walls)
			{
				if(desiredPos.contains(r))
				{
					return false;
				}
			}
			if(desiredPos.getX() == -1*SIZE && desiredPos.getY() == 13*SIZE)
			{
				System.out.println("attempting teleport");
			}
		}
		return true;
	}
	
	public boolean ghostCanMove(Ghost g)
	{
		Rectangle desiredPos = null;
		if(gdx==1)
			desiredPos = new Rectangle((int)g.getX()+SIZE, (int)g.getY(), SIZE, SIZE);
		if(gdx==-1)
			desiredPos = new Rectangle((int)g.getX()-SIZE, (int)g.getY(), SIZE, SIZE);
		if(gdy==1)
			desiredPos = new Rectangle((int)g.getX(), (int)g.getY()-SIZE, SIZE, SIZE);
		if(gdy==-1)
			desiredPos = new Rectangle((int)g.getX(), (int)g.getY()+SIZE, SIZE, SIZE);

		if(desiredPos != null)
		{
			for(Rectangle r : walls)
			{
				if(desiredPos.contains(r))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(specialTime)
		{
			time -= 1;
			if(time <= 0)
			{
				specialTime = false;
				time = 20;
			}
		}
		//move pacman as in the facing direction as long as there is no wall
		if(canMove())
		{
			if(dx==1 && pac.getX() == 30*SIZE && pac.getY() == 13*SIZE)
			{
				pac.setLocation(0,13*SIZE);
				x = 0;
				y = 13*SIZE;
			}
			if(dx == -1 && pac.getX() == 0 && pac.getY() == 13*SIZE)
			{
				pac.setLocation(28*SIZE, 13*SIZE);
				x = 28*SIZE;
				y = 13*SIZE;
			}
			else if(dx==1)
				pac.setLocation(x+=SIZE, y);
			else if(dx==-1)
				pac.setLocation(x-=SIZE, y);
			else if(dy==1)
				pac.setLocation(x, y-=SIZE);
			else if(dy==-1)
				pac.setLocation(x, y+=SIZE);
		}
		
		//remove pills upon contact
		for(int i = 0; i < pills.size(); i++)
		{
			if(pac.intersects(pills.get(i)))
			{
				pills.remove(i);
				score+=10;
			}
		}
		//remove specials upon contact
		for(int i = 0; i < specials.size(); i++)
		{
			if(pac.intersects(specials.get(i)))
			{
				specials.remove(i);
				specialTime = true;
				score+=50;
			}
		}
		
		//pacman collision with ghost
		if(pac.intersects(shadow) && !specialTime)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					dx = 0;
					dy = 0;
					gdx = 0;
					gdy = 0;
					pac.setLocation(15*SIZE, 21*SIZE);
					x = (int) pac.getX();
					y = (int) pac.getY();
					shadow.setLocation(16*SIZE, 10*SIZE);
				}
			});
		}
		if(pac.intersects(shadow) && specialTime)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					shadow.setLocation(16*SIZE, 10*SIZE);
				}
			});
		}
		
		//Ghosts 'AI'
		Random rng = new Random();
		int rand = rng.nextInt(10);
		
		if(rand <= 4)
		{
			gdx = 1;
			gdy = 0;
			if(ghostCanMove(shadow))
				shadow.setLocation((int)shadow.getX()+SIZE, (int)shadow.getY());
		}
		if(rand == 5)
		{
			gdx = -1;
			gdy = 0;
			if(ghostCanMove(shadow))
				shadow.setLocation((int)shadow.getX()-SIZE, (int)shadow.getY());
		}
		if(rand == 6)
		{
			gdx = 0;
			gdy = 1;
			if(ghostCanMove(shadow))
				shadow.setLocation((int)shadow.getX(), (int)shadow.getY()-SIZE);
		}
		if(rand >= 7)
		{
			gdx = 0;
			gdy = -1;
			if(ghostCanMove(shadow))
				shadow.setLocation((int)shadow.getX(), (int)shadow.getY()+SIZE);
		}

		//win
		if(score == 2720)
		{
			JOptionPane.showMessageDialog(null, "You won!");
			System.exit(0);
		}
		repaint();
	}

	public static void main(String args[])
	{
		JFrame f = new JFrame();
		f.add(new Board());

		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(550,460);
		f.setLocationRelativeTo(null);
		f.setTitle("Pacman");
	}

}
