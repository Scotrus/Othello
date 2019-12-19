// The "Othello" class.

//Kyle Hong
//Othello
//Culminating
//Date: 2018-06-12

//This program plays the game "Othello" (a.k.a. Reversi). Each player would
//take turns to enter coordinates of their next block (or color). At the end,
//the color with greater number will be the winner.

import java.awt.*;
import hsa.Console;

public class Othello
{
    static Console c;           // The output console

    public static void main (String[] args)
    {
	c = new Console ();

	//Declaration
	char[] [] board = new char [8] [8];
	String cordi = "";
	char col;
	int bmove = 0;

	//Assignment
	for (int i = 0 ; i < 8 ; i++) //Setting up the board with 'e', which mean empty spaces.
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		board [i] [j] = 'e';
	    }
	}

	board [3] [3] = 'w'; //Filling up the initial four blocks
	board [3] [4] = 'b';
	board [4] [3] = 'b';
	board [4] [4] = 'w';

	//Title Screen
	Color back = new Color (47, 156, 99);
	c.setColor (back);
	c.fillRect (0, 0, 639, 499);

	c.setColor (Color.black);
	c.drawLine (120, 100, 620, 100);
	c.drawLine (80, 200, 555, 200);
	c.drawLine (50, 300, 490, 300);
	c.drawLine (120, 125, 120, 330);
	c.drawLine (220, 30, 220, 330);
	c.drawLine (320, 20, 320, 345);
	c.drawLine (420, 50, 420, 330);
	c.drawLine (520, 40, 520, 260);

	c.fillOval (330, 110, 80, 80);
	c.fillOval (230, 210, 80, 80);
	c.setColor (Color.white);
	c.fillOval (230, 110, 80, 80);
	c.fillOval (330, 210, 80, 80);

	Font title = new Font ("Berlin Sans FB Demi", Font.BOLD, 70);
	c.setFont (title);
	c.drawString ("Othello", 203, 410);

	Font cont = new Font ("Berlin Sans FB Demi", Font.BOLD, 20);
	c.setFont (cont);
	c.drawString ("[Press any key to start the game]", 168, 450);

	c.getChar ();
	c.clear ();

	//Introduction Screen
	drawboard (board);

	c.setFont (cont);

	c.setColor (Color.black); //Rules
	c.drawString ("<RULES>", 315, 40);
	c.drawString ("1. Each player plays a colour, either", 315, 80);
	c.drawString ("black or white.", 315, 100);
	c.drawString ("2. In each turn, each move should", 315, 140);
	c.drawString ("flip at least one of the other colour.", 315, 160);
	c.drawString ("3. The game ends when either", 315, 200);
	c.drawString ("player cannot make a move.", 315, 220);
	c.drawString ("4. The player with the most pieces", 315, 260);
	c.drawString ("on the board at the end wins.", 315, 280);

	c.setCursor (17, 1);
	c.println ("This is a modern version of \"Reversi\" (a.k.a. \"Othello\").");
	c.println ("\nWhite starts first. Determine which player's going to take white. \nIf it's determined, press any key to start");
	c.getChar ();

	//A big loop for actual game
	do
	{
	    c.clear ();

	    //Updating the Gaming board
	    drawboard (board);

	    //Updating the Score board
	    drawsboard (board, cordi);

	    //Getting a coordinate
	    c.setCursor (17, 1);

	    if (counter (board, 'e') % 2 == 0) //This prevents confusion of "who should play next?" by showing who's turn it is.
	    {
		col = 'w';
		c.print ("It's white's turn.");
		if (checker (board, col)) //If the player can make a move, it asks the user to enter the coordinate.
		{
		    c.println (" Please choose the coordinate for your block. \nEnter it as A5, B7, etc.");
		}
		else //If the player cannot make a move in their turn, the loop breaks and the game ends.
		{
		    c.println (" But there's nowhere to place your block! Game ends.");
		    break;
		}
	    }
	    else
	    {
		col = 'b';
		c.print ("It's black's turn.");
		if (checker (board, col)) //If the player can make a move, it asks the user to enter the coordinate.
		{
		    c.println (" Please choose the coordinate for your block. \nEnter it as A5, B7, etc.");
		}
		else //If the player cannot make a move in their turn, the loop breaks and the game ends.
		{
		    c.println (" But there's nowhere to place your block! Game ends.");
		    break;
		}
	    }

	    cordi = c.readString ();

	    //A body loop for an entire error trap structure
	    while (true)
	    {
		//Error trap checking if the proper coordinate was used & if the block is placed in the empty space.
		while (true) //Used an infinite loop since the escape conidtional would be too long and make an error if I placed them into one line.
		{
		    //Check if the player tries to place their block out of the board (Also checks if the first letter is not capitalized etc.)
		    if (!((cordi.charAt (0) - 65) >= 0 && (cordi.charAt (0) - 65) <= 7) || !((cordi.charAt (1) - 49) >= 0 && (cordi.charAt (1) - 49) <= 7))
		    {
			c.println ("That's an invalid coordinate. Please place your block inside the board.");
			cordi = c.readString ();
		    }

		    //Check if the player tries to place their block in an already occupied space.
		    else if (board [cordi.charAt (0) - 65] [cordi.charAt (1) - 49] != 'e')
		    {
			c.println ("There's already a block in the given coordinate. Please choose again.");
			cordi = c.readString ();
		    }

		    //Check if the player breakes the format of coordinate input (input of more than two letters in the coordinate)
		    else if (cordi.length () != 2)
		    {
			c.println ("You typed in an invalid format. Please enter it again.");
			cordi = c.readString ();
		    }

		    if (!((cordi.charAt (0) - 65) >= 0 && (cordi.charAt (0) - 65) <= 7) || !((cordi.charAt (1) - 49) >= 0 && (cordi.charAt (1) - 49) <= 7))
			continue;

		    else if (board [cordi.charAt (0) - 65] [cordi.charAt (1) - 49] != 'e')
			continue;

		    else if (cordi.length () != 2)
			continue;

		    break;
		}

		bmove = counter (board, col); //This variable saves how many blocks were there before the user placed the new one.

		flipper (cordi, board, col);

		//Error trap when the player's move does not flip any colours, which is invalid.
		if (bmove == counter (board, col) - 1)
		{
		    board [cordi.charAt (0) - 65] [cordi.charAt (1) - 49] = 'e'; //Resetting the player's move if the player's move doesn't change anything
		    c.println ("Your move should flip at least one block. Please choose it again.");
		    cordi = c.readString ();
		    continue;
		}

		break;
	    }

	}
	while (counter (board, 'e') != 0);

	drawboard (board);
	drawsboard (board, cordi);

	c.println ("Press any key to continue.");
	c.getChar ();

	//Winner Displaying Screen
	c.clear ();
	drawboard (board);
	drawsboard (board, cordi);

	Font winner = new Font ("Berlin Sans FB Demi", Font.BOLD, 50);
	c.setFont (winner);

	c.setCursor (17, 1);
	if (counter (board, 'b') > counter (board, 'w'))
	{
	    c.setColor (Color.black);
	    c.drawString ("The winner is Black!", 90, 425);
	}
	else if (counter (board, 'b') < counter (board, 'w'))
	{
	    c.setColor (Color.black);
	    for (int i = 0 ; i <= 639 ; i++)
	    {
		c.drawLine (i, 320, i, 499);
		delay (3);
	    }

	    c.setColor (Color.white);
	    c.drawString ("The winner is White!", 90, 425);
	}
	else
	{
	    c.setColor (Color.gray);

	    c.drawString ("Draw!", 250, 425);
	    c.setFont (cont);

	    c.drawString ("Not sure how this even happened...", 0, 499);
	}

    } // main method


    //This method recognizes the coordinate that user enters and alters the
    //"board" array to flip the colors properly depending on the user's color.
    public static void flipper (String cordi, char[] [] board, char col)
    {
	int y = cordi.charAt (0) - 65;
	int x = cordi.charAt (1) - 49;

	board [y] [x] = col;

	for (int i = y + 1 ; i < 8 ; i++) //Downward Checking
	{
	    if (board [i] [x] == 'e')
	    {
		break;
	    }
	    else if (board [i] [x] == col)
	    {
		for (int j = y + 1 ; j < i ; j++)
		{
		    board [j] [x] = col;
		}
		break;
	    }
	}

	for (int i = y - 1 ; i >= 0 ; i--) //Upward Checking
	{
	    if (board [i] [x] == 'e')
	    {
		break;
	    }
	    else if (board [i] [x] == col)
	    {
		for (int j = y - 1 ; j > i ; j--)
		{
		    board [j] [x] = col;
		}
		break;
	    }
	}

	for (int i = x + 1 ; i < 8 ; i++) //Rightward Checking
	{
	    if (board [y] [i] == 'e')
	    {
		break;
	    }
	    else if (board [y] [i] == col)
	    {
		for (int j = x + 1 ; j < i ; j++)
		{
		    board [y] [j] = col;
		}
		break;
	    }
	}

	for (int i = x - 1 ; i >= 0 ; i--) //Leftward Checking
	{
	    if (board [y] [i] == 'e')
	    {
		break;
	    }
	    else if (board [y] [i] == col)
	    {
		for (int j = x - 1 ; j > i ; j--)
		{
		    board [y] [j] = col;
		}
		break;
	    }
	}

	for (int i = 1 ; x - i >= 0 && y - i >= 0 ; i++) //Left-up diagonal Checking
	{
	    if (board [y - i] [x - i] == 'e')
	    {
		break;
	    }
	    else if (board [y - i] [x - i] == col)
	    {
		for (int j = 1 ; j < i ; j++)
		{
		    board [y - j] [x - j] = col;
		}
		break;
	    }
	}

	for (int i = 1 ; x - i >= 0 && y + i < 8 ; i++) //Left-down diagonal Checking
	{
	    if (board [y + i] [x - i] == 'e')
	    {
		break;
	    }
	    else if (board [y + i] [x - i] == col)
	    {
		for (int j = 1 ; j < i ; j++)
		{
		    board [y + j] [x - j] = col;
		}
		break;
	    }
	}

	for (int i = 1 ; x + i < 8 && y - i >= 0 ; i++) //Right-up diagonal Checking
	{
	    if (board [y - i] [x + i] == 'e')
	    {
		break;
	    }
	    else if (board [y - i] [x + i] == col)
	    {
		for (int j = 1 ; j < i ; j++)
		{
		    board [y - j] [x + j] = col;
		}
		break;
	    }
	}

	for (int i = 1 ; x + i < 8 && y + i < 8 ; i++) //Right-down diagonal Checking
	{
	    if (board [y + i] [x + i] == 'e')
	    {
		break;
	    }
	    else if (board [y + i] [x + i] == col)
	    {
		for (int j = 1 ; j < i ; j++)
		{
		    board [y + j] [x + j] = col;
		}
		break;
	    }
	}
    }


    //This method counts the number of colors given on the board and returns an integer value.
    public static int counter (char[] [] board, char col)
    {
	int count = 0;
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int j = 0 ; j < 8 ; j++)
	    {
		if (board [i] [j] == col)
		{
		    count++;
		}
	    }
	}

	return count;
    }


    //This method checks if a player can place a block in his/her turn.
    public static boolean checker (char[] [] board, char col)
    {
	String cordi;
	int before;

	char[] [] copy = new char [8] [8]; //using copied version so that I don't alter the original while checking

	//using nested loop to make a 'clone'
	for (int y = 0 ; y < 8 ; y++)
	{
	    for (int x = 0 ; x < 8 ; x++)
	    {
		copy [y] [x] = board [y] [x];
	    }
	}

	for (int y = 0 ; y < 8 ; y++)
	{
	    for (int x = 0 ; x < 8 ; x++)
	    {
		if (copy [y] [x] != 'e')
		{
		    continue;
		}

		cordi = (((char) (y + 65)) + "") + (((char) (x + 49)) + "");

		before = counter (copy, col);

		copy [y] [x] = col;

		//A copy of 'flipper' method (Didn't use the original flipper method here since the flipper method directly alters the 'board' array)
		for (int i = y + 1 ; i < 8 ; i++) //Downward Checking
		{
		    if (copy [i] [x] == 'e')
		    {
			break;
		    }
		    else if (copy [i] [x] == col)
		    {
			for (int j = y + 1 ; j < i ; j++)
			{
			    copy [j] [x] = col;
			}
			break;
		    }
		}

		for (int i = y - 1 ; i >= 0 ; i--) //Upward Checking
		{
		    if (copy [i] [x] == 'e')
		    {
			break;
		    }
		    else if (copy [i] [x] == col)
		    {
			for (int j = y - 1 ; j > i ; j--)
			{
			    copy [j] [x] = col;
			}
			break;
		    }
		}

		for (int i = x + 1 ; i < 8 ; i++) //Rightward Checking
		{
		    if (copy [y] [i] == 'e')
		    {
			break;
		    }
		    else if (copy [y] [i] == col)
		    {
			for (int j = x + 1 ; j < i ; j++)
			{
			    copy [y] [j] = col;
			}
			break;
		    }
		}

		for (int i = x - 1 ; i >= 0 ; i--) //Leftward Checking
		{
		    if (copy [y] [i] == 'e')
		    {
			break;
		    }
		    else if (copy [y] [i] == col)
		    {
			for (int j = x - 1 ; j > i ; j--)
			{
			    copy [y] [j] = col;
			}
			break;
		    }
		}

		for (int i = 1 ; x - i >= 0 && y - i >= 0 ; i++) //Left-up diagonal Checking
		{
		    if (copy [y - i] [x - i] == 'e')
		    {
			break;
		    }
		    else if (copy [y - i] [x - i] == col)
		    {
			for (int j = 1 ; j < i ; j++)
			{
			    copy [y - j] [x - j] = col;
			}
			break;
		    }
		}

		for (int i = 1 ; x - i >= 0 && y + i < 8 ; i++) //Left-down diagonal Checking
		{
		    if (copy [y + i] [x - i] == 'e')
		    {
			break;
		    }
		    else if (copy [y + i] [x - i] == col)
		    {
			for (int j = 1 ; j < i ; j++)
			{
			    copy [y + j] [x - j] = col;
			}
			break;
		    }
		}

		for (int i = 1 ; x + i < 8 && y - i >= 0 ; i++) //Right-up diagonal Checking
		{
		    if (copy [y - i] [x + i] == 'e')
		    {
			break;
		    }
		    else if (copy [y - i] [x + i] == col)
		    {
			for (int j = 1 ; j < i ; j++)
			{
			    copy [y - j] [x + j] = col;
			}
			break;
		    }
		}

		for (int i = 1 ; x + i < 8 && y + i < 8 ; i++) //Right-down diagonal Checking
		{
		    if (copy [y + i] [x + i] == 'e')
		    {
			break;
		    }
		    else if (copy [y + i] [x + i] == col)
		    {
			for (int j = 1 ; j < i ; j++)
			{
			    copy [y + j] [x + j] = col;
			}
			break;
		    }
		}

		if (before == counter (copy, col) - 1)
		{
		    copy [y] [x] = 'e';
		}
		else
		{
		    return true;
		}

	    }
	}

	return false;
    }


    //This method draws the gaming board on the pre-determined place on the board.
    //Also it prints the blocks on the board as well depending on the board array entered.
    public static void drawboard (char[] [] board)
    {
	Color desk = new Color (217, 215, 202);
	c.setColor (desk);
	c.fillRect (0, 0, 639, 320);

	Color back = new Color (47, 156, 99);
	c.setColor (back); //drawing the board
	c.fillRect (20, 20, 280, 280);
	c.setColor (Color.black);
	c.drawRect (20, 20, 280, 280);

	for (int i = 40 ; i <= 250 ; i += 30) //Grid on the board
	{
	    for (int j = 40 ; j <= 250 ; j += 30)
	    {
		c.drawRect (j, i, 30, 30);
	    }
	}

	c.setColor (Color.white);
	Font coordi = new Font ("Arial", Font.BOLD, 13);
	c.setFont (coordi);
	for (int i = 1 ; i <= 8 ; i++) //Coordinates on the board 1~8
	{
	    c.drawString (i + "", i * 30 + 22, 37);
	}

	for (int i = 1 ; i <= 8 ; i++) //A~H
	{
	    c.drawString (((char) ('A' + i - 1)) + "", 25, i * 30 + 30);
	}

	for (int i = 45 ; i <= 255 ; i += 30) //blocks on the board
	{
	    for (int j = 45 ; j <= 255 ; j += 30)
	    {
		if (board [(i - 45) / 30] [(j - 45) / 30] == 'b')
		{
		    c.setColor (Color.black);
		}
		else if (board [(i - 45) / 30] [(j - 45) / 30] == 'w')
		{
		    c.setColor (Color.white);
		}
		else
		{
		    continue;
		}
		c.fillOval (j, i, 20, 20);
	    }
	}

    }


    //This method draws the scoreboard on the pre-determined place on the board.
    //Also it displays the number of turns left and previous move.
    public static void drawsboard (char[] [] board, String cordi)
    {
	Color sboard = new Color (78, 121, 94);
	c.setColor (sboard);
	c.fillRect (340, 100, 260, 120);
	c.setColor (Color.black);
	c.drawRect (340, 100, 260, 120);

	Font cont = new Font ("Berlin Sans FB Demi", Font.BOLD, 20);
	c.setFont (cont);
	c.drawString ("SCORE BOARD", 350, 90);

	c.setColor (Color.white);
	c.drawString ("White: " + counter (board, 'w'), 350, 130);
	c.drawString ("Black: " + counter (board, 'b'), 350, 155);
	c.drawString ("Number of turns left: " + counter (board, 'e'), 350, 180);
	c.drawString ("Previous move: " + cordi, 350, 205);
    }


    public static void delay (int millisecs)  // Delay Method used for graphics
    {
	try
	{
	    Thread.currentThread ().sleep (millisecs);
	}


	catch (InterruptedException e)
	{
	}
    }
} // Othello class


