import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
/*
 * discarding a glyph needs to spark an event like prophecy
 */
public class Onirim extends JFrame
{	
	private Image tk = ResourceLoader.getImage("TanKey.png");
	private Image bk = ResourceLoader.getImage("BlueKey.png");
	private Image rk = ResourceLoader.getImage("RedKey.png");
	private Image gk = ResourceLoader.getImage("GreenKey.png");
	private Image ts = ResourceLoader.getImage("TanSun.png");
	private Image bs = ResourceLoader.getImage("BlueSun.png");
	private Image rs = ResourceLoader.getImage("RedSun.png");
	private Image gs = ResourceLoader.getImage("GreenSun.png");
	private Image tm = ResourceLoader.getImage("TanMoon.png");
	private Image bm = ResourceLoader.getImage("BlueMoon.png");
	private Image rm = ResourceLoader.getImage("RedMoon.png");
	private Image gm = ResourceLoader.getImage("GreenMoon.png");

	private Image cardBack = ResourceLoader.getImage("Onirim.png");
	
	private Image nightmare = ResourceLoader.getImage("Nightmare.png");
	private Image td = ResourceLoader.getImage("TanDoor.png");
	private Image bd = ResourceLoader.getImage("BlueDoor.png");
	private Image rd = ResourceLoader.getImage("RedDoor.png");
	private Image gd = ResourceLoader.getImage("GreenDoor.png");
	
//	private Image tdb = ResourceLoader.getImage("TanDoorBlurred.png");
//	private Image bdb = ResourceLoader.getImage("BlueDoorBlurred.png");
//	private Image rdb = ResourceLoader.getImage("RedDoorBlurred.png");
//	private Image gdb = ResourceLoader.getImage("GreenDoorBlurred.png");
	
	private Image xs = ResourceLoader.getImage("CrossroadSun.png");
	private Image xm = ResourceLoader.getImage("CrossroadMoon.png");
	private Image xk = ResourceLoader.getImage("CrossroadKey.png");
	private Image deadEnd = ResourceLoader.getImage("DeadEnd.png");
	
//	private Image rg = ResourceLoader.getImage("RedGlyph.png");
//	private Image bg = ResourceLoader.getImage("BlueGlyph.png");
//	private Image gg = ResourceLoader.getImage("GreenGlyph.png");
//	private Image tg = ResourceLoader.getImage("TanGlyph.png");
//	private Image spellCard = ResourceLoader.getImage("SpellCard.png");
	
//	private Image r3 = ResourceLoader.getImage("RedTowerThree.png");
//	private Image g3 = ResourceLoader.getImage("GreenTowerThree.png");
//	private Image t5 = ResourceLoader.getImage("TanTowerFive.png");
//	private Image b4 = ResourceLoader.getImage("BlueTowerFour.png");
	
	private int cardWidth = 100, cardHeight = 140, cardX, cardY;
	private boolean hasBeenPlayed = false;
	private int startCardX = cardX, startCardY= cardY;
	private int anchorX, anchorY;

	private int drawMinX = 10, drawMaxX = drawMinX + cardWidth;
	private int screenWidth,screenHeight;

	private int drawMinY;
	private int drawMaxY;
	private int numLocationsPlayed;
	private int fudgeX = 8, fudgeY = 28;
	private int maxInRow = 21;
	private int deltaDragX, oldX; // for shifting prophecy left or right;
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> limbo = new ArrayList<Card>();
	private ArrayList<Card> toDraw = new ArrayList<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> discard = new ArrayList<Card>();
	private ArrayList<Card> playedLocations = new ArrayList<Card>();
	private ArrayList<Card> playedDoors = new ArrayList<Card>();
	private ArrayList<Card> prophecyCards = new ArrayList<Card>();
//	private ArrayList<Card> incantationCards = new ArrayList<Card>();
//	private ArrayList<Card> blurredDoors = new ArrayList<Card>();
//	private ArrayList<Card> highlightedDiscards = new ArrayList<Card>();
//	private ArrayList<Card> paradoxicalProphecyCards = new ArrayList<Card>();
//	private ArrayList<Tower> towerCards;
	
//	private Card[] twoDoorsToBeSwapped = {null, null};
	
	private Font font = new Font("Dialog", Font.PLAIN, 20);
	private Font endFont = new Font("Castellar", Font.PLAIN, 100);
	private Font titleFont = new Font("Elephant", Font.PLAIN, 40);
	
	private Card doorFromSet, doorFromKey; // is null used for scoring doors
	
	private boolean firstDraw = true, prophecyInPlay, movedProphecyCard; 
//	private boolean incantationInPlay, movedIncantationCard;
//	private boolean paradoxicalProphecyInPlay, movedParadoxicalProphecyCard;
	private boolean fillingHandSoIgnoreNightmares;
	
	private boolean  nightMareInPlay;

	private boolean gameOver;
	private String result = "YOU WIN";
	private int maxCardsInDiscardColumn;
//	private int doorsToWin = 8;  // each time a door is added to the deck ++
	
	private int indexOfLastSet = 0;
	private Color highlight = new Color(255, 255, 0, 120);
//	private Color highlight2 = new Color(0, 255, 0, 120);
	private double scaleFactor = .87;
	
	public Random randy = new Random(); 

	private boolean crossroadExpansion = false;
//	private boolean glyphsExpansion = false;
//	private boolean bookOfStepsLostAndFoundExpansion = false;
//	private boolean towersExpansion = false;
	
//	private int spellX, spellY1, spellY2, spellY3, spellWidth, spellHeight;
//	private int spell1Cost = 5, spell2Cost = 7, spell3Cost = 10;
	private int discardChange = 25;
//	private boolean activatedSpellOne, activatedSpellTwo, activatedSpellThree, completeSpellTwo;
	
//	private boolean canPlayTowerLeft, canPlayTowerRight, towersComplete, mustDiscardTower;
	private int numProphecyCardsToDiscard, numProphecyCardsToDisplay;
	
//	private int tanTowerDiscardCount, redTowerDiscardCount, blueTowerDiscardCount, greenTowerDiscardCount;
	
	private boolean titleScreen = true;

	public Onirim()
	{
		makeEnvironment();
	}
	

	public void populateCards()
	{
		//add doors
		limbo.add(new Card(rd, "door", "red"));
		limbo.add(new Card(rd, "door", "red"));
				
		limbo.add(new Card(bd, "door", "blue"));
		limbo.add(new Card(bd, "door", "blue"));
				
		limbo.add(new Card(gd, "door", "green"));
		limbo.add(new Card(gd, "door", "green"));
				
		limbo.add(new Card(td, "door", "tan"));
		limbo.add(new Card(td, "door", "tan"));

		
		
		
		// if playing with the crossroads expansion
		if(crossroadExpansion)
		{
			//add in crossroad keys suns moons and deadends
			//10 dead ends
			// 1 key
			// 3 suns
			// 2 moons
			for (int i = 0; i < 10; i++) 
				limbo.add(new Card(deadEnd, "locationDeadEnd", "deadEnd"));
			for (int i = 0; i < 3; i++) 
				limbo.add(new Card(xs, "locationSun", "wildCard"));
			for (int i = 0; i < 2; i++) 
				limbo.add(new Card(xm, "locationMoon", "wildCard"));
			limbo.add(new Card(xk, "locationKey", "wildCard"));
			
		}
		
		for(int i = 0; i < 10; i++) 
			limbo.add(new Card(nightmare, "dream", "")); // add nightmares
		
		// add suns
		for(int i = 0; i < 9; i++)
			limbo.add(new Card(rs, "locationSun", "red"));
		for(int i = 0; i < 8; i++)
			limbo.add(new Card(bs, "locationSun", "blue"));
		for(int i = 0; i < 7; i++)
			limbo.add(new Card(gs, "locationSun", "green"));
		for(int i = 0; i < 6; i++)
			limbo.add(new Card(ts, "locationSun", "tan"));
		
		
		//add moons
		for(int i = 0; i < 4; i++)
			limbo.add(new Card(rm, "locationMoon", "red"));
		for(int i = 0; i < 4; i++)
			limbo.add(new Card(bm, "locationMoon", "blue"));
		for(int i = 0; i < 4; i++)
			limbo.add(new Card(gm, "locationMoon", "green"));
		for(int i = 0; i < 4; i++)
			limbo.add(new Card(tm, "locationMoon", "tan"));
		
		//add keys
		for(int i = 0; i < 3; i++)
			limbo.add(new Card(rk, "locationKey", "red"));
		for(int i = 0; i < 3; i++)
			limbo.add(new Card(bk, "locationKey", "blue"));
		for(int i = 0; i < 3; i++)
			limbo.add(new Card(gk, "locationKey", "green"));
		for(int i = 0; i < 3; i++)
			limbo.add(new Card(tk, "locationKey", "tan"));	
		for(Card c : limbo)
			toDraw.add(c);
		
	}
	
	
	//add limbo to deck and shuffle deck
	public void shuffleDeck()
	{
		while(!limbo.isEmpty())
			deck.add(limbo.remove(0));
		while(!deck.isEmpty()) // here is the shuffle.
			limbo.add(deck.remove(randy.nextInt(deck.size())));
		while(!limbo.isEmpty())
			deck.add(limbo.remove(0));
	}

	public void makeEnvironment()
	{
		setTitle("ONIRIM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//fullscreen
		Toolkit tk = Toolkit.getDefaultToolkit();
		screenWidth = ((int) tk.getScreenSize().getWidth());
		screenHeight = ((int) tk.getScreenSize().getHeight()) /*- 230*/; //the 230 is to see the console for debug
		setSize(screenWidth ,screenHeight);
		drawMaxY = screenHeight - 100;
		drawMinY = screenHeight - cardHeight - 100;
		maxCardsInDiscardColumn = (screenHeight - cardHeight)/((cardHeight)/5);
	
		MyPanel pan = new MyPanel();
		addMouseListener(new Mousey());
		addMouseMotionListener(new Mousey());
		pan.setBackground(Color.DARK_GRAY);
		getContentPane().add(pan);
		setResizable(true);
		setVisible(true);
	}
	
	private class MyPanel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setFont(font);
			drawCards(g);
		}
	}

	public void drawCards(Graphics g)
	{
		if(titleScreen)
		{
			int yStart = 120 - cardHeight/2 + 10;
			int yChange = cardHeight + 10;
			int xChange = 10 + cardWidth;
			g.setColor(Color.white);
			
			g.drawImage(cardBack, 750, yStart + 5*yChange/3, 2*cardWidth, 2*cardHeight, this);
			g.setFont(titleFont);
			g.drawString("ONIRIM", 755, yStart + 5*yChange/3 + 2*cardHeight + 15);
			g.setFont(font);
			g.drawString("Click To Start", 795, yStart + 5*yChange/3 + 2*cardHeight + 35);

			g.fillRect(20, yStart + 3*cardHeight/2 /*+ yChange*/, 20, 20);
			g.drawString("Crossroads and Deadends" , 500, yStart + 3*cardHeight/2 /*+ yChange*/ + 5);
			g.drawImage(deadEnd, 50, yStart + 1*yChange, cardWidth, cardHeight, this);
			g.drawImage(xs, 50 + 10 + cardWidth, yStart + 1*yChange, cardWidth, cardHeight, this);
			g.drawImage(xm, 50 + 40 + cardWidth, yStart + 1*yChange, cardWidth, cardHeight, this);
			g.drawImage(xk, 50 + 70 + cardWidth, yStart + 1*yChange, cardWidth, cardHeight, this);
			
			g.setColor(Color.yellow);
			
			if(crossroadExpansion)
			{
				g.fillRect(20, yStart + 3*cardHeight/2 /*+ yChange*/, 20, 20);
			}
			
			g.setColor(Color.black);
			
			g.drawRect(20, yStart + 3*cardHeight/2 /*+ yChange*/, 20, 20);
		
			g.setColor(Color.white);
		}
		else
		{
			for(int i = toDraw.size() - 1; i >= 0; i--)
			{
				Card temp = toDraw.get(i);
				if(temp.getVisible())
				{
					if(playedDoors.contains(temp))  // scaleFactor = .8 for now
						g.drawImage(toDraw.get(i).getImage(), toDraw.get(i).getX(), toDraw.get(i).getY(), 
							(	int)(cardWidth*scaleFactor), (int)(cardHeight*scaleFactor), this);
					else
						g.drawImage(toDraw.get(i).getImage(), toDraw.get(i).getX(), toDraw.get(i).getY(), 
								cardWidth, cardHeight, this);
				}
			}
			
			g.drawImage(cardBack, 10, screenHeight - cardHeight -100, cardWidth, cardHeight, this);
			g.drawImage(cardBack,screenWidth-cardWidth-25, screenHeight - cardHeight -100, cardWidth, cardHeight, this);
			g.setColor(Color.white);
			g.drawString("DECK: " + deck.size() + "                                      HAND"
					+ "                                         LIMBO", 
					16, screenHeight -78);
		
			g.drawLine(drawMinX, 0, drawMinX, screenHeight);
			g.drawLine(drawMaxX, 0, drawMaxX, screenHeight);
		
			g.drawLine(0, drawMinY, screenWidth, drawMinY);
			g.drawLine(0, drawMaxY, screenWidth, drawMaxY); 
		
			// for left discard when needed
			g.drawLine(screenWidth - cardWidth - 2*fudgeX-10, 10 + cardHeight, screenWidth - cardWidth - 2*fudgeX-10,screenHeight);
			g.drawLine(screenWidth - 2*fudgeX-10, 0, screenWidth  - 2*fudgeX-10,screenHeight);
		
			g.drawLine(drawMaxX, 10 + cardHeight, screenWidth, 10 + cardHeight); 
			if(gameOver)
			{
				g.setFont(endFont);
				g.drawString("GAME OVER", drawMaxX + 10, screenHeight / 3);
				g.drawString(result, drawMaxX + 10, 3 *screenHeight / 5);
			}
		
			if(doorFromSet != null)
			{
				g.setColor(highlight);
				g.fillRect(doorFromSet.getX(), doorFromSet.getY(), cardWidth, cardHeight);
				g.setColor(Color.WHITE);
			}
		
			if(doorFromKey != null)
			{
				g.setColor(highlight);
				for(Card c : hand)
				{
					if(c.getType().contains("Key") && (c.getColor().contains(doorFromKey.getColor())||c.getColor().contains("wildCard")))
						g.fillRect(c.getX(), c.getY(), cardWidth, cardHeight);
				}
				g.setColor(Color.WHITE);
			}
		}
	}// end drawing method
	
	public static void main(String [] args)
	{
		Onirim obj = new Onirim();
	}
	
	public void moveCard(int x, int y)
	{
		for(int i = 0; i < toDraw.size(); i++)
		{
			Card c = toDraw.get(i);
			if(x >= c.getX() && x <= c.getX() + cardWidth &&
					y >= c.getY() && y <= c.getY() + cardHeight)
			{
				if(c.getMovable())
				{
				
					if(!playedLocations.isEmpty() && c.equals(playedLocations.get(playedLocations.size()-1)))
						hasBeenPlayed = true;
				
					c.setSelected(true);
					anchorY = y;
					anchorX = x;
					startCardX = c.getX();
					startCardY = c.getY();
					toDraw.add(0,toDraw.remove(i));
					break;
				}
			}
		}
	}
	//  returns the index of a card
	public int getIndexOfCard(ArrayList<Card> list, Card c)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).equals(c))
				return i;
		}
		return -1;
	}
	
	public void drawHand(int x, int y)
	{
		if(firstDraw)
		{
			if(clickedDeck(x, y))
			{
				Card temp = deck.get(0);
				if(temp.getType().contains("location"))
				{
					hand.add(deck.remove(0));
					temp.setX(drawMaxX + cardWidth*(hand.size()-1));
					temp.setY(drawMinY);
					temp.setVisible(true);
				}
				else// if(temp.getType().contains("door") || temp.getType().contains("dream"))
				{
					limbo.add(deck.remove(0));
					temp.setX(drawMaxX + cardWidth*(limbo.size()-1) + 6*cardWidth);
					temp.setY(drawMinY);
					temp.setVisible(true);
				}
			}
			if(hand.size()==5)
			{
				firstDraw = false;
				for(Card c : hand)
				{
					c.setMovable(true);
				}
				shuffleLimbo();
			}
		}
	}
	public void escape()//escapes the current hand discarding it and refilling it
	{
		fillingHandSoIgnoreNightmares=true;
		discardAndFillHand();
	}
	public void shuffleLimbo()
	{
		if(limbo.size() != 0)
		{	
			for(Card c : limbo)
			{
				c.setX(-200);
				c.setY(0);
				c.setVisible(false);
			}
			emptyProphecy();
			shuffleDeck();
		}
	}
	
	public void emptyProphecy()
	{
		for(Card c : prophecyCards)
		{
			c.setX(-200);
			c.setY(0);
			c.setVisible(false);
		}
		prophecyCards.clear();
	}
	
	public boolean clickedDeck(int x, int y)
	{
		return(x > drawMinX + fudgeX && x < drawMaxX + fudgeX
				&& y > drawMinY + fudgeY && y < drawMaxY + fudgeY);	
	}
	public boolean clickedEscape(int x, int y)
	{
		return(x > screenWidth-cardWidth-25 && x < screenWidth-25
				&& y > drawMinY + fudgeY && y < drawMaxY + fudgeY);	
	}
	public void freeHand()
	{
		for(Card c : hand)
		{
			c.setMovable(true);
		}
	}
	
	public void lockLastLocationPlay()
	{
		playedLocations.get(playedLocations.size()-1).setMovable(false); // this went oob -1 when there was one card after prophecy
	}
	
	public void lockHand()
	{
		for(Card c : hand)
		{
			c.setMovable(false);
		}
	}
	// locks prophecy once you have set the order
	public void lockProphecyAndSetProphecyOrder()
	{
		for(Card c : prophecyCards)
			c.setMovable(false);
		// Now reorder the deck in the same order that you left the prophecy
		for(int i = 0; i < prophecyCards.size(); i++) 
			deck.remove(0);
		// NOW add THEM BACK
		for(int i = prophecyCards.size() - 1; i >=0 ; i--)
			deck.add(0, prophecyCards.get(i));
	}
	
	public void drawCard()
	{
		if(deck.isEmpty())
		{
			gameOver = true;
			result = "YOU LOSE";
			return;
		}
		
		Card temp = deck.get(0);
		doorFromKey = null;
		
		if(!playedLocations.isEmpty())
		{
			lockLastLocationPlay();
		}
		if(temp.getType().contains("location"))
		{
			hand.add(deck.remove(0));
			temp.setX(drawMaxX + cardWidth*(hand.size()-1));
			temp.setY(drawMinY);
			temp.setVisible(true);
		}
		// if you draw a door, you can discard the same colored key in order to play the door
		else if(temp.getType().contains("door"))
		{
			if(!nightMareInPlay && searchHandForCorrectKey(temp))
				doorFromKey = temp;
			limbo.add(deck.remove(0));
			temp.setX(drawMaxX + cardWidth*(limbo.size()-1) + 6*cardWidth);
			temp.setY(drawMinY);
			temp.setVisible(true);
		}
		/*
		 * Currently, there is one type of dream.  It is a nightmare.
		 * 
		 * If you have any key, you can discard it along with the nightmare.
		 * 
		 * If you have already played/ scored a door, you can place it in limbo to discard the nightmare.
		 * 
		 * Otherwise, you draw five cards.  Any location goes to discard.  Any dream or door goes to limbo. 
		 */
		else if(temp.getType().contains("dream"))
		{
			limbo.add(deck.remove(0));
			if(!fillingHandSoIgnoreNightmares)
			{
				nightMareInPlay = true; 
				temp.setX(drawMaxX + cardWidth*(limbo.size()-1) + 6*cardWidth);
				temp.setY(drawMinY);
				temp.setVisible(true);
			}
		}
		if(hand.size() == 5)
			freeHand();
		if(prophecyCards.contains(temp))
		{
			prophecyCards.remove(temp);
			adjustProphecy();
		}
	}
	
	public boolean searchHandForCorrectKey(Card door)
	{
		for(Card c : hand)
		{
			if((c.getType().contains("Key") && (c.getColor().contains(door.getColor()))||(c.getType().contains("Key")&&c.getColor().contains("wildCard")))) //fine
				return true;
		}
		return false;
	}
	
//	public boolean searchHandForKey()
//	{
//		for(Card c : hand)
//		{
//			if(c.getType().contains("Key"))
//				return true;
//		}
//		return false;
//	}
	
	public void searchDeckForDoor(Card c)
	{
		for(int j = 0; j < deck.size(); j++)
		{
			if(deck.get(j).getType().equals("door")  &&
					c.getColor().contains(deck.get(j).getColor())) // not fine
			{
				//play the card to limbo for now
				Card door = deck.get(j);
				limbo.add(door);
				door.setX(drawMaxX + cardWidth*(limbo.size()-1) + 6*cardWidth);
				door.setY(drawMinY);
				door.setVisible(true);
				doorFromSet = door;
				break;  // as to not get two (multiple) doors
				//we still need the opportunity to play this: in fact it is currently still in the deck.
			}
		}
	}
	
	public boolean isValidLocationPlay(Card c)
	{
		if(c.getType().contains("door") || c.getType().contains("dream")||c.getType().contains("DeadEnd")) 
			return false;
		String type = "";
		if(numLocationsPlayed != 0)
			type = playedLocations.get(playedLocations.size()-1).getType();
		boolean toRet = ((c.getY() >= drawMinY-50 || c.getX() <= drawMaxX) //subtracted a bit from draw min y so that it dosnt acidently go over
				|| (numLocationsPlayed != 0 
				&& (c.getType().contains(type) || type.contains(c.getType()))));
		return !toRet;
	}
	
	public boolean isDiscard(Card c)
	{
		return c.getY() <= drawMinY && c.getX() < drawMaxX - 5 && !(c.getType().contains("door"));
	}
	
	
	
	/*
	 * You can only play one crossroads card in a set of three
	 */
	// precondition:  c has been played and is a valid location
	// postcondition.  if we complete a set, we look for a door and update the indexOfLastSet
	
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	public boolean compareCards(Card a, Card b)//compares two cards and returns true if they are both the same or only contain one wildCard
	{
		
		if(a.getColor().equals("wildCard"))
			if(b.getColor().equals("wildCard"))
				return false;
			else 
				return true;
		
		if(b.getColor().equals("wildCard"))
			if(a.getColor().equals("wildCard"))
				return false;
			else 
				return true;
		
		if(a.getColor().equals(b.getColor()))
			return true;
		return false;
	}
	public boolean validSet(Card a, Card b, Card c)// checks to see if the sum of a set is less than or equal to four
	{
		boolean checkOne = compareCards(a,b);
		boolean checkTwo =compareCards(a,c);
		boolean checkThree = compareCards(c,b);
		if(checkOne&&checkTwo&&checkThree)
		{
			return true;
		}
		return false;
	}
	public void playLocation(Card c)
	{
		numLocationsPlayed++; 
		hand.remove(c);
		playedLocations.add(c);
		// there have been at least three cards added to playedLocations since the last set
		// was completed
		if(playedLocations.size() >= indexOfLastSet + 3)
		{
			String col = playedLocations.get(playedLocations.size()-1).getColor();
//			if((col.equals(playedLocations.get(playedLocations.size() -2).getColor())) &&
//					col.equals(playedLocations.get(playedLocations.size() -3).getColor()))
			if(validSet(playedLocations.get(playedLocations.size()-1), playedLocations.get(playedLocations.size()-2), playedLocations.get(playedLocations.size()-3)))
			{
				
				lockLastLocationPlay();
				indexOfLastSet = playedLocations.size() /*-1*/;
				if(playedLocations.get(playedLocations.size()-1).getColor().equals("wildCard"))
				{
					if(playedLocations.get(playedLocations.size()-2).equals("wildCard"))
					{
						searchDeckForDoor(playedLocations.get(playedLocations.size()-3));
					}
					else
					{
						searchDeckForDoor(playedLocations.get(playedLocations.size()-2));
					}
				}
				else
				{
					searchDeckForDoor(playedLocations.get(playedLocations.size()-1));
				}
			}
		}
		
	}
	
	public void takeBackLocation(Card c)
	{
		numLocationsPlayed--;  
		playedLocations.remove(c);
		hand.add(c); 
		c.setX(drawMaxX + cardWidth*(hand.size()-1));
		c.setY(drawMinY);
		if(hand.size()==5)
			freeHand();
	}
	
	public void adjustHand()
	{
		for(int k = 0; k < hand.size(); k++) // move cards in hand
		{
			hand.get(k).setX(drawMaxX + cardWidth*(k));
			hand.get(k).setMovable(false);
		}
	}
	
	public void adjustProphecy()
	{
		for(int k = 0; k < prophecyCards.size(); k++) // move cards in prophecy
		{
			prophecyCards.get(k).setX(drawMaxX + cardWidth*(k+1));
		}
	}
	
	public void adjustProphecy(Card c)
	{
		for(int k = 0; k < prophecyCards.size(); k++) // move cards in prophecy
		{
			if(!prophecyCards.get(k).equals(c))
				prophecyCards.get(k).setX(drawMaxX + cardWidth*(k+1));
		}
	}
	
	public void setPlayedLocationCoordinates(Card c)
	{
		if(/*towersExpansion*/false)
		{
			if(numLocationsPlayed <= maxInRow)
			{
				c.setX(drawMaxX + 35*numLocationsPlayed);
				c.setY(35 + 2*cardHeight);	
			}
			else
			{
				c.setX(drawMaxX + 35*(numLocationsPlayed - maxInRow));
				c.setY(40 + 3*cardHeight);
			}
		}
		else
		{
			if(numLocationsPlayed <= maxInRow)
			{
				c.setX(drawMaxX + 35*numLocationsPlayed);
				c.setY(30 + 1*cardHeight);	
			}
			else
			{
				c.setX(drawMaxX + 35*(numLocationsPlayed - maxInRow));
				c.setY(35 + 2*cardHeight);
			}
		}
	}
	
	public boolean clickedDoorInLimbo(int x, int y, Card d)
	{
		
		return(d != null && x > d.getX() + fudgeX && x < d.getX() + cardWidth + fudgeX
				&& y > d.getY() + fudgeY && y < d.getY() + cardHeight + fudgeY);	
	}
	//if doorFromKey is not null then it is d
	// if you click a key in your hand and it matchs the color of d then return that key
	// otherwise return null
	public Card clickedKeyToPlayDoor(int x, int y, Card d)
	{
		if(d != null)
		{
			for(Card c : hand)
			{
				if(x > c.getX() + fudgeX && x < c.getX() + cardWidth + fudgeX
						&& y > c.getY() + fudgeY && y < c.getY() + cardHeight + fudgeY)
				{
					if(c.getType().contains("Key") && (c.getColor().contains(d.getColor())||c.getColor().contains("wildCard"))) //fine
						return c;
				}
			}
		}
		return null;
	}
	
	public void removeDoorOnHold()// this is only used when you get a set and you would like to play it
	{
		doorFromSet.setX(-200);
		doorFromSet.setY(0);
		doorFromSet.setVisible(false);
		limbo.remove(doorFromSet); // what if there are 2 copies.  shouldn't happen
		doorFromSet = null;
	}
	/*
	 * If this happens while there are cards in prophecy, you have ended the prophecy because 
	 * you must search and reshuffle
	 */
	public void scoreDoorFromSet()
	{
		emptyProphecy();
		doorFromSet.setVisible(true);
		if(nextDoorIndex() == playedDoors.size())
		{
			playedDoors.add(doorFromSet);
		}
		else
		{
			playedDoors.set(nextDoorIndex(), doorFromSet);
		}
		
		limbo.remove(doorFromSet);
		deck.remove(doorFromSet);
		adjustPlayedDoors();  
		doorFromSet = null;
		 
		shuffleDeck();
		gameOver = checkForWin();
	}
	
	public boolean checkForWin()
	{
		return playedDoors.size() == 8;
	}
	
	public void scoreDoorFromKey(Card k)
	{
		doorFromKey.setY(10);
		doorFromKey.setX((int)(30+(cardWidth*scaleFactor)*(nextDoorIndex()+1)));
		limbo.remove(doorFromKey);
		
		if(nextDoorIndex() == playedDoors.size())
		{
			playedDoors.add(doorFromKey);
		}
		else
		{
			playedDoors.set(nextDoorIndex(), doorFromKey);
		}
		doorFromKey = null;
		sendToDiscard(k);
		toDraw.remove(k); // needed because we didn't move it, 
		toDraw.add(0,k);  // which reorders it in toDraw
		gameOver = checkForWin();
	}
	
	
	public void adjustDiscard(Card c)
	{
		if((discard.size()) > maxCardsInDiscardColumn) // discardFull
		{
			c.setX(screenWidth - cardWidth - 2*fudgeX-10);
			c.setY(155 + 25*(discard.size()-1 - maxCardsInDiscardColumn));
		}
		else
		{
			c.setX(10);
			c.setY(10 + discardChange*(discard.size()-1));
		}
	}
	
	public void adjustDiscard()
	{
		for(int i = 0; i < discard.size(); i++)
		{
			Card c = discard.get(i);
			if(i > maxCardsInDiscardColumn) // discardFull
			{
				c.setX(screenWidth - cardWidth - 2*fudgeX-10);
				c.setY(155 + discardChange*(i - maxCardsInDiscardColumn));
			}
			else
			{
				c.setX(10);
				c.setY(10 + discardChange*(i));
			}
		}
	}
	public void sendToDiscard(Card c)
	{
		discard.add(c);
		if(prophecyCards.contains(c))
		{
			prophecyCards.remove(c);
			deck.remove(c);
		}
		
		if(hand.contains(c))  // there is no current prophecy.  it was from your hand
			hand.remove(c);
		adjustDiscard(c);
		c.setMovable(false);
		adjustHand();
		adjustProphecy();
	}
	
	public void setProphecy(int numForsee, int numDiscard)
	{
		emptyProphecy(); // in case you still have cards in the deck when you trigger
		prophecyInPlay = true;		// another prophecy
		// set the original 5 cards.
		numProphecyCardsToDiscard = numDiscard;
		numProphecyCardsToDisplay = numForsee;
		for(int i = 0; i < numForsee; i++)
		{
			if(i >= deck.size())
			{
				numProphecyCardsToDisplay = i;
				break; 
			}
			Card temp = deck.get(i);
			toDraw.add(temp);
			prophecyCards.add(temp);
			temp.setY(drawMinY - cardHeight);
			temp.setX(drawMaxX + cardWidth*(i+1));
			temp.setMovable(true);  
			temp.setVisible(true);
		}
		// WHAT IF ALL OF THE CARDS ARE DOORS.  WE CAN'T DISCARD DOORS
		for(Card c : prophecyCards)
		{
			if(!(c.getType().contains("door")))
			{
				lockHand();
				return;
			}
		}
		for(Card c : prophecyCards)
			c.setMovable(false);
		prophecyInPlay = false; // there must have been all doors in hand
	}
	
	public Card clickedProphecyCard(int x, int y)
	{
		if(!prophecyInPlay)
			return null;
		
		
		for(Card c : prophecyCards)
		{
			if(x > c.getX() + fudgeX && x < c.getX() + cardWidth + fudgeX
					&& y > c.getY() + fudgeY && y < c.getY() + cardHeight + fudgeY)
			{
				movedProphecyCard = true;
				return c;
			}
		}
		return null;
	}
	
	// currently, this happens, but gets skipped, as there is no pause.  
	// the game could end, in which case you MAY see this happen
	public void drawFiveNightmarePlay()
	{
		discardNightmare();
		//send five cards to limbo for the user to see
		Card temp;
		for(int i = 0; i < 5; i++)
		{
			if(deck.isEmpty())
			{
				gameOver = true;
				result = "YOU LOSE";
				return;
			}
			temp = deck.remove(0);
			toDraw.remove(temp);
			toDraw.add(0,temp);
			limbo.add(temp);
			temp.setX(drawMaxX + cardWidth*(limbo.size()-1) + 6*cardWidth);
			temp.setY(drawMinY);
			temp.setVisible(true);
		}
	}
	
	public void discardNightmare()
	{
		// send the last card played to limbo(must be a nightmare) to discard
		Card n = limbo.remove(limbo.size()-1);
		toDraw.remove(n);
		toDraw.add(0,n);
		sendToDiscard(n);
	}
	
	public void sendLocationsToDiscardFromLimbo()
	{
		int i = 0;
		while(i < limbo.size())
		{
			if(i == limbo.size())
				break;  // dead end cards do not get discarded this way
			while(limbo.get(i).getType().contains("location") && !limbo.get(i).getType().contains("DeadEnd"))
			{
				sendToDiscard(limbo.remove(i));
				if(i == limbo.size())
					break;
			}
			i++;
		}
	}
	
	public Card clickedKeyInHand(int x, int y)
	{
		for(Card c : hand)
		{
			if(x > c.getX() + fudgeX && x < c.getX() + cardWidth + fudgeX
					&& y > c.getY() + fudgeY && y < c.getY() + cardHeight + fudgeY
						&&  c.getType().contains("Key"))
				return c;
		}
		return null;
	}
	
	public Card clickedDoorInPlay(int x, int y)
	{
		for(Card c : playedDoors)
		{
			if(c == null)
			{
				//continue;
			}
			else if(x > c.getX() + fudgeX && x < c.getX() + cardWidth + fudgeX
					&& y > c.getY() + fudgeY && y < c.getY() + cardHeight + fudgeY
						&&  c.getType().contains("door"))

				return c;
		}
		return null;
	}
	
	public void playKeyOnNightmare(Card k)
	{
		discardNightmare();
		toDraw.remove(k);
		toDraw.add(0, k);
		sendToDiscard(k);
	}
	
	public void playDoorOnNightmare(Card d)
	{
		discardNightmare();
		toDraw.remove(d);  // change this to limbo later.  
		toDraw.add(0, d);	// it causes a shuffle
		limbo.add(d);
		playedDoors.remove(d);
		d.setX(drawMaxX + cardWidth*(limbo.size()-1) + 6*cardWidth);
		d.setY(drawMinY);
		adjustPlayedDoors();
	}
	
	public int nextDoorIndex()
	{
		for(int i = 0; i < playedDoors.size(); i++)
		{
			if(playedDoors.get(i) == null)
			{
				return i;
			}
		}
		return playedDoors.size();
	}
	
	public void adjustPlayedDoors()
	{
		for(int i = 0; i < playedDoors.size(); i++)
		{
			if(playedDoors.get(i) != null)
			{
				playedDoors.get(i).setX((int)(30+(cardWidth*scaleFactor)*(i+1)));
				playedDoors.get(i).setY(10);
			}
		}
	}
	
	
	public boolean clickedNightmare(int x, int y) 
	{												
		if(limbo.isEmpty())
			return false;
		Card c = limbo.get(limbo.size()-1);
		return(x > c.getX() + fudgeX && x < c.getX() + cardWidth + fudgeX
				&& y > c.getY() + fudgeY && y < c.getY() + cardHeight + fudgeY
				&&  c.getType().contains("dream"));
	}
	
	public void discardAndFillHand()
	{
		discardHand();
		fillHand(); 
	}
	
	public void discardHand()
	{
		while(!hand.isEmpty())
		{
			Card c = hand.get(hand.size()-1);
			toDraw.remove(c);
			toDraw.add(0, c);
			sendToDiscard(c);
		}
		if(!limbo.isEmpty() && limbo.get(limbo.size() - 1).getType().contains("dream"))
		{
			discardNightmare();
		}
	}
	
	public void fillHand()
	{
		while(hand.size() < 5)
		{	
			if(deck.isEmpty())
			{
				gameOver = true;
				result = "YOU LOSE";
				return;
			}
			drawCard();
		}
		fillingHandSoIgnoreNightmares = false;
	}
	
	private class Mousey implements MouseListener, MouseMotionListener
	{
		// do not code mouseClicked, mouseEntered, or MouseExited
		public void mouseClicked(MouseEvent mickey) {}
		public void mouseEntered(MouseEvent mickey) {}
		public void mouseExited(MouseEvent mickey) {}
		
		public void mousePressed(MouseEvent mickey)
		{
			if(titleScreen)
			{
				int y1 = 120 - cardHeight/2 + 10 + 3*cardHeight/2;
				int yChange = 10 + cardHeight;

				if(mickey.getX() >= 20 + fudgeX && mickey.getX() <= 40 + fudgeX 
						&& mickey.getY() >= y1 + fudgeY /*+ yChange*/
						&& mickey.getY() <= y1 + 20 + fudgeY /*+ yChange*/)
				{
					crossroadExpansion = !crossroadExpansion;
				}
				if(mickey.getX() >= 750 + fudgeX && mickey.getX() <= 750 + fudgeX + 2*cardWidth
						&& mickey.getY() >= 120 - cardHeight/2 + 10 + 5*yChange/3 + fudgeY 
						&& mickey.getY() <= 120 - cardHeight/2 + 10 + 5*yChange/3 + 2*cardHeight + fudgeY) 
				{
					populateCards();
					shuffleDeck();
					titleScreen = false;
				}
			}
			if(!gameOver)
			{
				//this allows you to move around movable cards
				moveCard(mickey.getX(), mickey.getY());
			
				//fill hand to 5 cards, sending doors and dreams to limbo temporarily
				if(firstDraw)
					drawHand(mickey.getX(), mickey.getY());
				else if(nightMareInPlay) // && canPlayNightmare
				{
					if(clickedDeck(mickey.getX(), mickey.getY()))
					{
						drawFiveNightmarePlay();
						nightMareInPlay = false;
						sendLocationsToDiscardFromLimbo();
						shuffleLimbo();
					}
					//must be the last card in limbo
					else if(clickedNightmare(mickey.getX(), mickey.getY()))
					{
						discardAndFillHand();
						nightMareInPlay = false;
					}
					else if(clickedKeyInHand(mickey.getX(), mickey.getY()) != null)
					{
						playKeyOnNightmare(clickedKeyInHand(mickey.getX(), mickey.getY()));
						nightMareInPlay = false;
					}
					else if(clickedDoorInPlay(mickey.getX(), mickey.getY()) != null)
					{
						playDoorOnNightmare(clickedDoorInPlay(mickey.getX(), mickey.getY()));
						nightMareInPlay = false;
						shuffleLimbo();
					}
				}
				else if(prophecyInPlay && numProphecyCardsToDiscard == 0 
						&& clickedDeck(mickey.getX(), mickey.getY()))
				{
					prophecyInPlay = false; //frees the deck
					lockProphecyAndSetProphecyOrder();
					adjustProphecy();
					drawCard();
				}
			
				else if(hand.size() != 5 && // draw card for fill hand.  
							clickedDeck(mickey.getX(), mickey.getY()))
				{
					if(doorFromSet != null) // didn't play the door when it entered limbo as a result of playing
					{						// a set of three locations
						removeDoorOnHold();
					}
					if(!prophecyInPlay)
						drawCard();
				}
				else if(hand.size() != 5 && clickedDoorInLimbo(mickey.getX(), mickey.getY(), doorFromSet))
				{
						scoreDoorFromSet();	
				}
				
				else if(hand.size() != 5 && clickedKeyToPlayDoor(mickey.getX(), mickey.getY(), doorFromKey) != null)
				{
					scoreDoorFromKey(clickedKeyToPlayDoor(mickey.getX(), mickey.getY(), doorFromKey));
					adjustHand();
				}
			
				else if(prophecyInPlay &&/* (prophecyCards.size() == 5 || prophecyCards.size() == deck.size())*/
						prophecyCards.size() == numProphecyCardsToDisplay && 
						clickedProphecyCard(mickey.getX(), mickey.getY())!=null) 
				{
					deltaDragX = clickedProphecyCard(mickey.getX(), mickey.getY()).getX(); // set DeltaDragX
					oldX = deltaDragX;  // a copy
				}
				else if(crossroadExpansion&&clickedEscape(mickey.getX(), mickey.getY())&&hand.size() == 5)
				{
					escape();
				}
				if(hand.size() == 5)
				{
					shuffleLimbo();
					freeHand();
				}
			}	
		}
		
		public void mouseReleased(MouseEvent mickey) 
		{

			for(int i = 0; i < toDraw.size(); i++)
			{
				Card c = toDraw.get(i);
				if(c.getSelected())
				{
					c.setSelected(false);
					
					// didn't drop into play area so snap back to original position 
					//PLAY LOCATION
					if(!hasBeenPlayed && isValidLocationPlay(c) && !prophecyCards.contains(c))
					{
						playLocation(c);
						adjustHand();
						setPlayedLocationCoordinates(c);
					}
					//DISCARD
					else if(!hasBeenPlayed && isDiscard(c) && !prophecyCards.contains(c)&&!c.getType().contains("DeadEnd"))
					{
						sendToDiscard(c);
						if(c.getType().contains("Key") )
						{
							setProphecy(5, 1);  // Changed setProphecy
						}

					}
					
					else if(prophecyInPlay && (prophecyCards.size() == numProphecyCardsToDisplay))// || prophecyCards.size() == deck.size()))
					{ 
						if(isDiscard(c) && numProphecyCardsToDiscard == 1)
						{
							sendToDiscard(c);
							prophecyInPlay = false; //frees the deck
							lockProphecyAndSetProphecyOrder();
							adjustProphecy();
							numProphecyCardsToDiscard = 0; 
							numProphecyCardsToDisplay = 0;
						}
						
						else if(movedProphecyCard)
						{
							movedProphecyCard = false;
							deltaDragX = 0; //released prophecy card.  reset deltaDragX to zero
							adjustProphecy();
							c.setY(startCardY);
						}
						else
						{
							c.setX(startCardX);
							c.setY(startCardY);
						}
					}
				
					else if(hasBeenPlayed)
					{
						if(mickey.getY() > drawMinY) // moved card below play area
						{
							takeBackLocation(c); 
							hasBeenPlayed = false;
						}
						else
						{
							c.setX(startCardX);
							c.setY(startCardY);
						}
					}
					else 
					{
						c.setX(startCardX);
						c.setY(startCardY);
					}
					adjustPlayedDoors();
				}
				
			}
			if(mickey.getButton() == 3)
			{
				adjustPlayedDoors();
			}
			if(mickey.getButton() == 2)
			{
				adjustDiscard();
			}
			repaint();
		}//end mouseReleased
		
		public void mouseDragged(MouseEvent minnie) 
		{
			for(int i = 0; i < toDraw.size(); i++)
			{
				Card c = toDraw.get(i);
				if(c.getSelected())
				{
					int deltaX = minnie.getX() - anchorX;
					c.setX(deltaX + startCardX);
					int deltaY = minnie.getY() - anchorY;
					c.setY(deltaY + startCardY);
				
					if(prophecyCards.contains(c)) // move this out if dragged becomes more complex
					{
						deltaDragX = c.getX() - oldX;
						
						
						for(int j = 0; j < Math.min(prophecyCards.size(), deck.size()); j++)
						{
							Card temp = prophecyCards.get(j);
							if(!temp.equals(c) && deltaDragX < 0 && c.getX() < temp.getX() + cardWidth - 10 && c.getX() > temp.getX()) 
							{
								prophecyCards.remove(c);
								prophecyCards.add(j, c);
								prophecyCards.remove(temp);
								prophecyCards.add(j+1,temp);
								adjustProphecy(c);       
							}
							else if(!temp.equals(c) && deltaDragX > 0 && c.getX() + cardWidth > temp.getX() + 10 && c.getX()< temp.getX())
							{
								prophecyCards.remove(c);
								prophecyCards.add(j, c);
								prophecyCards.remove(temp);
								prophecyCards.add(j-1,temp);
								adjustProphecy(c);       
							}
						}
						oldX = c.getX(); // updating delta after some computations
					}
				}
			}
			repaint();
		}
		//do not code mouseMoved
		public void mouseMoved(MouseEvent minnie) {}
	}
	
}
