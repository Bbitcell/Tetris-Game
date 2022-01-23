import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;



import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import static javax.swing.JOptionPane.showMessageDialog;

/* Name: Mohammed Umer Raheemuddin
 * ID: 4110718
 * 
 * Project 2 CS 112: Tetris  
 * 
 * This program uses Swing and AWT to create a Tetris game.
 * 
 * Class: Tetris_JPanel
 * 
 * The main panel class that organizes the graphic objects and move them based on events. 
 * Also, the panel class contains FileWriter and FileReader writes the best score into the file.
 * The word "Tetriaminos" refers to a block that contains four single blocks.
 * 
 * 
 * Notable imports: 
 * 
 * FileWriter and its dependencies for writing the best score into a file.
 * FileReader and its dependencies for reading the best score from a file.
 * JPanel to create a containers that contains all the graphic objects in the program.
 * JOption to inform the user that they have lost the game.
 * KeyListener to get keyboard input from the user.
 * ActionListener to have time based events.
 * Timer to schedule time based events.
 * Random to randomly select the Tetriaminos from the seven Tetriaminos.
 * Arrays to mainly append the arrays.
*/



public class Tetris_JPanel extends JPanel implements ActionListener, KeyListener {
	
	
	// Schedule events according to the timer below.
	// Every 200 milliseconds.
	Timer time = new Timer(200, this);
	
	// The size of each single block.
	private int square_size = 25;
	
	// The variables below simplify the JPanel's area into a columns and rows.

	private int game_board_columns = 10 ;
	private int game_board_rows = 20;
	
	
	// Variables that determine the height and width of the JPanel to position the graphic objects.
	private int width = square_size * game_board_columns;
	private int height = square_size * game_board_rows;
	

	// The Matrix below holds all the possible locations in the JPanel.
	private int [][] game_board_matrix = new int [game_board_rows] [game_board_columns];

	
	
	// The variables below store the already occupied position by other blocks.
	private int  occupied_matrix_x;
	private int  occupied_matrix_y;
	
	
	
	// The main block has three sub blocks to form a Tetriaminos.
	// The sub block move depending on the position of the main block.
	
	// The variables below determine the position of the main block.
	public int main_block_x_position ;
	public int main_block_y_position ;
	
	// The variables below determine the velocity of the main block.
	private int main_block_x_velocity ;
	private int main_block_y_velocity ;
	
	
	// The variables below determine the position of the sub blocks.
	public int sub_block_2_x_position ;
	public int sub_block_2_y_position ;
	

	public int sub_block_3_x_position ;
	public int sub_block_3_y_position ;
	
	
	public int sub_block_4_x_position ;
	public int sub_block_4_y_position ;
	
	// Initialize the four blocks.
	Rectangle2D main_block;
	Rectangle2D sub_block_2;
	Rectangle2D sub_block_3;
	Rectangle2D sub_block_4;
	
	// Create a random object that randomly selects Tetriaminos.
	Random random = new Random();
	
	
	
	// Variables that store name of each Tetriaminos.
	private String L_Block = "L Block";
	private String Reverse_L_Block = "Reverse L Block";
	
	
	private String S_Block = "S Block";
	private String Reverse_S_Block = "Reverse S Block";
	
	private String Square_Block = "Square Block";
	private String Line_Block = "Line Block";
	private String W_Block = "W Block";
	
	
	
	// Store all the names of the Tetriaminos into a list.
	private String [] all_blocks = {L_Block, Reverse_L_Block, S_Block, Reverse_S_Block, Square_Block, Line_Block, W_Block };
	
	
	// Store the randomly selected Tetriamino into the variable below.
	private String chosen_block;
	// Store the next randomly selected Tetriamino into the variable below.
	public String [] chosen_blocks= new String [2];
	

	
	// The color variable determines the color of the current Tetriamino.
	
	private Color color;
	
	
	// The variables below control the rotation of the Tetriaminos.
	// More information in rotate method.
	
	
	// How many times up arrow key was pressed.
	
	private int rotator_pressed = 0;

	// Default rotation values for the | shaped Tetriaminos.
	private int Line_x_rotate = 0;
	private int Line_y_rotate = square_size;
	
	
	// Default rotation values for the s and flip s shaped Tetriamino.
	private int S_x_rotate = 1;
	private int S_y_rotate = 1;
	
	// Default rotation values for the L  shaped Tetriamino.
	private int L_x_rotate = 0;
	private int L_y_rotate = 1;
	private int L_ox_rotate = 1;
	private int L_oy_rotate = 0;
	
	// Default rotation values for flip L shaped Tetriamino.
	private int RL_x_rotate = 0;
	private int RL_y_rotate = 1;
	private int RL_ox_rotate = 1;
	private int RL_oy_rotate = 0;
	
	
	
	// Default rotation values for the _|_ shaped Tetriamino.
	private int W_x_rotate = 1;
	private int W_y_rotate = 0;
	private int W_ox_rotate = -1;
	private int W_oy_rotate = 1;

	
	
	
	
	
	// Activates the choose_block method.
	private int new_block = 1;
	
	// Controls how often some methods are called.
	int  activator = 1;
	

	// Store all the previous blocks into a list.
	// By default, the list is empty.
	Rectangle2D [] previous_blocks  = new Rectangle2D [0];

	// Array to store all colors of previous Tetriaminos.
	Color [] previous_colors_list = new Color[1];
	
	
	// Variable to store the score of the game.
	public int score;
	
	// Variable to determine if the user clicked the left arrow key or right arrow key.
	private int right_or_left;
	
	// Constructor
	public Tetris_JPanel() {

		// The variables below determine the initial position of the block.
		main_block_x_position = width/2;
		main_block_y_position = 0;
		
		// The variables below determine the initial velocity of the block.
		main_block_x_velocity = 0;
		main_block_y_velocity = square_size;

		// First we need to make all the possible values for the grid to zero.
		// Zero means that the location in the grid is empty.
		
		// We can do this by using a nested for-loop.
		for (int row = 0; row < game_board_matrix.length; row++)
		{
			for (int column =0 ; column < game_board_matrix[0].length; column++)
			{				

					game_board_matrix [row] [column] = 0; 

				}

			}
		
		// Start time to schedule events
		time.start();
		
		
		// General properties for Tetris_JPanel.

		setBackground(Color.BLACK);

		// Add user input via keyboard.
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		
		
	}
	
	
	
	
	
	// Add graphics to this JPanel
	public void paint(Graphics object) {
		// Inherit the paintComponent() from the JPanel class.
		super.paintComponent(object);
		
		
		// Create a environment to let other graphics interact with each other.
		
		Graphics2D game_board =  (Graphics2D) object;
		
		// Every Tetriaminos in Tetris is made up of four single blocks.
		
		
		
		
		// Before that randomly choose the Tetriamino that should be displayed.
		
		Choose_New_Block();

		
		// Retrieve the property of the selected Tetriamino
		
		// More information on this method later.
		All_Blocks_Properties();

		
		// Create the main block based the directly edited variables.
		
		main_block = new Rectangle2D.Double(main_block_x_position, main_block_y_position, square_size ,square_size); 
		game_board.setPaint(color);
		game_board.draw(main_block);;
		
		
		
		// Create the sub block that is based around the main block.
		// Sub block x and y positions are declared in the All_Blocks_Properties(). 
		//The positions are based on main block x and y positions.
		
		sub_block_2 = new Rectangle2D.Double(sub_block_2_x_position, sub_block_2_y_position, square_size ,square_size); 
		game_board.setPaint(color);
		game_board.draw(sub_block_2);;
		
		
		sub_block_3 = new Rectangle2D.Double(sub_block_3_x_position, sub_block_3_y_position, square_size ,square_size); 
		game_board.setPaint(color);
		game_board.draw(sub_block_3);;
		
		
		sub_block_4 = new Rectangle2D.Double(sub_block_4_x_position , sub_block_4_y_position, square_size ,square_size); 
		game_board.setPaint(color);
		game_board.draw(sub_block_4);;
		
		
		
		
		// The code below creates the blocks that were previously created and not destroyed.
		
		// More information on this code later.
		
		// Check if the length of the array is at least one block.
		if (previous_blocks.length > 0 ) {
			
			
			// To render all the previous blocks, create a for loop to individually create each block.
			for (int index = 0; index < previous_blocks.length; index++)
				
			{	
				// This if checks if the block was destroyed.
				if (previous_blocks[index] != null)
				{
					
				// If false, then render the previous block that were not destroyed.
				
					
				// Color the previous blocks with their previous color.
				game_board.setPaint(previous_colors_list[index/4]);
				
				game_board.fill(previous_blocks[index]);
				game_board.setPaint(Color.white);
				game_board.draw(previous_blocks[index]);

				}
				}

		}
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		

		// Check if the current Tetriamino is out of the game board or overlapping with other blocks.

		Out_Of_Bounds();
		
		// When the block hits the ground or other blocks, then occupy the location of game_board matrix by marking it by 1.
		
		Occupy_Blocks();
		
		
		// If there are ten "1" in the same row.
		// Then delete all the blocks in that same row.
		// After that shift down all the blocks before the deleted row blocks. 
		
		Game_Board_Shift_Down();	
		
		// To learn more details, check the individual methods.
		
		
		
		// Move the main block based on the velocity.
		
		main_block_y_position += main_block_y_velocity;
		main_block_x_position += main_block_x_velocity;
		
		// Reset the velocity of main block to the default values.
		main_block_x_velocity = 0;
		main_block_y_velocity = square_size;
		

		// update the all Graphics.
		repaint();
	}
	
	// This method randomly selects a Tetriamino from all_blocks.
	public void Choose_New_Block()
	{
		// Delete the Tetriamino in the first index of chosen_block
		// This works after a Tetriamino is placed in the game board.
		chosen_blocks [0] = null;
		
		// Check if a new Tetriamino needs to be created.
		if (new_block == 1) {
			// if true, then check if the  first index is empty and second index in occupied.
			
			
			if (chosen_blocks[0] == null && chosen_blocks[1] != null)
			{   
				// If true, then move the Tetriamino in the second index to the first index.
				
				chosen_blocks[0] =chosen_blocks[1];
				
				// Randomly select Tetriamino block for the second index.
				chosen_blocks[1] = all_blocks[random.nextInt(all_blocks.length)];
			}
			// Check If both indexes are empty. 
			else if (chosen_blocks[0] == null || chosen_blocks[1] == null)
			{
				// If true, then randomly select Tetriamino blocks for the both indexes.
				
				chosen_blocks[0] = all_blocks[random.nextInt(all_blocks.length)];
				chosen_blocks[1] = all_blocks[random.nextInt(all_blocks.length)];
			}

	// The first Tetriamino of chosen_blocks array is going to be rendered.
	chosen_block = chosen_blocks[0];

	

	// Inform the user about the next Tetriamino.
	
	System.out.println("Next Block :\t" + chosen_blocks[1]);

	// Deactivate the need for a new Tetriamino to be created
	new_block = 0;
	}
	}
	
	
	// This method assigns the color and the location of the sub blocks depending on the type of Tetriamino
	public void All_Blocks_Properties()
	{
		
		// The switch the Tetriamino, then assigns the color and location of the sub blocks based on the main block's location.
		// The variables that contain "rotate" change the location of sub blocks when the user presses up arrow key.
		// As a result, the Tetriamino rotates 90, 180, 270, and 360 degrees clockwise.
		// This multi-degree rotation is possible because of Rotate_Block() method. More information in the method.
		
		// However, the square Tetriamino does not need to rotate because rotation is the same result.
		// As a result, the square Tetriamino has no rotator variables for the sub block.
		
		switch (chosen_block)
		{
		case "L Block":
			// The variables below determine the color of the sub block.
			color = Color.ORANGE;
			
			// The variables below determine the position of the sub block.
			sub_block_2_x_position = main_block_x_position -(square_size *L_x_rotate);
			sub_block_2_y_position = main_block_y_position -(square_size *L_y_rotate);
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_3_x_position = main_block_x_position + (square_size * (L_x_rotate + L_ox_rotate));
			sub_block_3_y_position = main_block_y_position + (square_size * (L_y_rotate + L_oy_rotate));
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_4_x_position = main_block_x_position +(square_size *L_x_rotate);
			sub_block_4_y_position = main_block_y_position +(square_size *L_y_rotate);
			break;
			
		case "Reverse L Block":
			// The variables below determine the color of the sub block.
			color = Color.BLUE;
			
			// The variables below determine the position of the sub block.
			sub_block_2_x_position = main_block_x_position -(square_size *RL_x_rotate);
			sub_block_2_y_position = main_block_y_position -(square_size *RL_y_rotate);
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_3_x_position = main_block_x_position -(square_size * (RL_x_rotate + RL_ox_rotate));
			sub_block_3_y_position = main_block_y_position +(square_size * (RL_y_rotate + RL_oy_rotate));
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_4_x_position = main_block_x_position + (square_size *RL_x_rotate);
			sub_block_4_y_position = main_block_y_position +(square_size *RL_y_rotate);
			break;
		
		case "S Block":
			// The variables below determine the color of the sub block.
			color = Color.GREEN;
			
			// The variables below determine the position of the sub block.
			sub_block_2_x_position = main_block_x_position - square_size;
			sub_block_2_y_position = main_block_y_position ;
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_3_x_position = main_block_x_position;
			sub_block_3_y_position = main_block_y_position -square_size;
			


			
			// The variables below determine the position of the sub block.
			sub_block_4_x_position = main_block_x_position + (square_size * S_x_rotate) ;
			sub_block_4_y_position = main_block_y_position - (square_size * S_y_rotate);
			break;
		case "Reverse S Block":
			
			// The variables below determine the color of the sub block.
			color = Color.RED;
			
			// The variables below determine the position of the sub block.
			sub_block_2_x_position = main_block_x_position +square_size;
			sub_block_2_y_position = main_block_y_position;
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_3_x_position = main_block_x_position;
			sub_block_3_y_position = main_block_y_position -square_size;
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_4_x_position = main_block_x_position - (square_size * S_x_rotate) ;
			sub_block_4_y_position = main_block_y_position - (square_size * S_y_rotate);
			break;
		case "Square Block":
			
			// The variables below determine the color of the sub block.
			color = Color.YELLOW;
			
			
			// The variables below determine the position of the sub block.
			sub_block_2_x_position = main_block_x_position +square_size;
			sub_block_2_y_position = main_block_y_position;
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_3_x_position = main_block_x_position;
			sub_block_3_y_position = main_block_y_position -square_size;
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_4_x_position = main_block_x_position + square_size;
			sub_block_4_y_position = main_block_y_position -square_size;
			break;
		case "Line Block":
			// The variables below determine the color of the sub block.
			color = Color.CYAN;
			
			
			// The variables below determine the position of the sub block.
			sub_block_2_x_position = main_block_x_position -(1*Line_x_rotate);
			sub_block_2_y_position = main_block_y_position -(1*Line_y_rotate);
			

			
			// The variables below determine the position of the sub block.
			sub_block_3_x_position = main_block_x_position - (2*Line_x_rotate);
			sub_block_3_y_position = main_block_y_position - (2*Line_y_rotate);
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_4_x_position = main_block_x_position -(3*Line_x_rotate);
			sub_block_4_y_position = main_block_y_position -(3*Line_y_rotate);
			break;
		case "W Block":
			// The variables below determine the color of the sub block.
			color = Color.MAGENTA;
			
			
			// The variables below determine the position of the sub block.
			sub_block_2_x_position = main_block_x_position + (square_size * W_x_rotate);
			sub_block_2_y_position = main_block_y_position + (square_size * W_y_rotate);
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_3_x_position = main_block_x_position - (square_size * W_x_rotate);
			sub_block_3_y_position = main_block_y_position - (square_size * W_y_rotate);
			

			
			
			// The variables below determine the position of the sub block.
			sub_block_4_x_position = main_block_x_position - (square_size * (W_x_rotate + W_ox_rotate ));
			sub_block_4_y_position = main_block_y_position- (square_size * (W_y_rotate + W_oy_rotate));
			break;
	
		}
		

	}
	
	
	// The method below check if the current Tetriamino is outside the game board or overlapping with other blocks.
	public void Out_Of_Bounds()
	{
		
		// Check if the Tetriamino is ouside of game board horizontally (only for x axis).
		if (main_block_x_position > (width-(square_size * 2)) || sub_block_2_x_position > (width-(square_size * 2)) || sub_block_3_x_position > (width-(square_size * 2)) || sub_block_4_x_position > (width-(square_size * 2))) {
			
			// IF the block is outside on the right side.
			// Then stop the main block which halts the entire Tetriamino.
			main_block_x_velocity = 0;

			// Check if the block is out of the screen on the right side.
			if (main_block_x_position > (width -square_size) || sub_block_2_x_position > (width -square_size)  || sub_block_3_x_position > (width -square_size)  || sub_block_4_x_position > (width -square_size))
			{
				// Return the block to a step behind.
				main_block_x_position -= square_size;
			}

		}
		else if (main_block_x_position < (square_size) || sub_block_2_x_position < (square_size)  || sub_block_3_x_position < (square_size)  || sub_block_4_x_position < (square_size) ) {
			
			// IF the block is outside on the left side.
			// Then stop the main block which halts the entire Tetriamino.
			main_block_x_velocity = 0;

			// Check if the block is out of the screen on the left side.
			if (main_block_x_position < 0 || sub_block_2_x_position < 0  || sub_block_3_x_position < 0  || sub_block_4_x_position < 0)
			{
				// Return the block to a step behind.
				main_block_x_position +=  square_size;
			}
			


		}
		
		
		
		// Check if the Tetriamino is overlapping with other blocks.
		// For loop to individually check each block in previous_block
		
		for (int index = 0; index< previous_blocks.length; index++)
		{
		// Check if the previous block is null and the activator is active.
		// The activator controls the number of times theses code is executed.
			
		if (previous_blocks[index] != null && activator ==1)
		{
			// Stop time to avoid any unexpected errors.
			time.stop();
			
			
		// Check if the any blocks of current Tetriamino overlapping with the previous block.
		if ( 
				 (main_block_y_position == (previous_blocks[index].getY())&&  main_block_x_position == previous_blocks[index].getX()) 
				 || (sub_block_2_y_position == (previous_blocks[index].getY() ) &&  sub_block_2_x_position == (previous_blocks[index].getX()))
				 || (sub_block_3_y_position == (previous_blocks[index].getY())&&  sub_block_3_x_position == previous_blocks[index].getX())
				 || (sub_block_4_y_position == (previous_blocks[index].getY() ) &&  sub_block_4_x_position == previous_blocks[index].getX())) 
		{
			
			// If true, then check if the user pressed the left or right arrow key.
			// So that the current Tetriamino returns to its original position.
			if (right_or_left == 0)
			{
				// IF the user pressed left arrow key.
				// Return the position of the current Tetriamino one step behind.
			main_block_x_position +=  square_size;


			}
			else if (right_or_left == 1)
			{
				// IF the user pressed right arrow key.
				// Return the position of the current Tetriamino one step behind.
				main_block_x_position -=  square_size;
			}
			
			// Update the current Tetriamino with the new positions.
			All_Blocks_Properties();
			main_block.setRect(main_block_x_position, main_block_y_position, square_size, square_size);
			sub_block_2.setRect(sub_block_2_x_position, sub_block_2_y_position, square_size, square_size);
			sub_block_3.setRect(sub_block_3_x_position, sub_block_3_y_position, square_size, square_size);
			sub_block_4.setRect(sub_block_4_x_position, sub_block_4_y_position, square_size, square_size);


		}
		// Continue the time.
		time.start();
		}
		}

	}
	
	// The method below assigns the value "1" to game_board_matrix according to the current Tetriamino's position.
	// Also, stops the Tetriamino when it reaches the bottom.
	public void Occupy_Blocks()
	{
		// This variable controls the amount of time. Store_Previous_Blocks(), reset_block(), and Check_Game_Lost() are called.
		activator =1;

		// The for loop check the position of the current Tetriamino.
		for (int main_row = 0; main_row < game_board_matrix.length; main_row++)
		{



		for (int main_column =0 ; main_column < game_board_matrix[main_row].length; main_column++)
		{	


			// Check if any index in the array contains "1".
			if (game_board_matrix[main_row] [main_column] == 1)
				
			{ 
				// If true, then convert the row and column of the index into a pixel length.
				
				occupied_matrix_y = main_row * square_size;
				
				occupied_matrix_x = main_column * square_size;
			}
			else {
				// If false, then convert it to a value outside of game_board so that occupied_matrix_y and occupied_matrix_x
				// do not get the same value as the previous occupied_matrix_y and occupied_matrix_x.
				occupied_matrix_y = -500;
				
				occupied_matrix_x = -500;
			}
			
			// Check if the current Tetriamino hit the most bottom row or block.

			if ((main_block_y_position >= (height-square_size)) || (sub_block_2_y_position >= (height-square_size))  || (sub_block_3_y_position >= (height-square_size))  || (sub_block_4_y_position >= (height-square_size)) 
					 || (main_block_y_position == (occupied_matrix_y -square_size)&&  main_block_x_position == occupied_matrix_x) 
					 || (sub_block_2_y_position == (occupied_matrix_y -square_size) &&  sub_block_2_x_position == (occupied_matrix_x))
					 || (sub_block_3_y_position == (occupied_matrix_y  -square_size)&&  sub_block_3_x_position == occupied_matrix_x)
					 || (sub_block_4_y_position == (occupied_matrix_y -square_size) &&  sub_block_4_x_position == occupied_matrix_x)) 
			{
				
				// Stop time to avoid any unexpected errors.
				
				time.stop();


				


				
				// Turns the current Tetriamino's position into a column-row format.
				int matrix_x_position = main_block_x_position/square_size;
				int matrix_y_position = main_block_y_position/square_size;
				
				int matrix_x2_position = sub_block_2_x_position/square_size;
				int matrix_y2_position = sub_block_2_y_position/square_size;
				
				int matrix_x3_position = sub_block_3_x_position/square_size;
				int matrix_y3_position = sub_block_3_y_position/square_size;
				
				int matrix_x4_position = sub_block_4_x_position/square_size;
				int matrix_y4_position = sub_block_4_y_position/square_size;

				// The nested for loop below replaces 0 in the grid with 1 where the block has hit the bottom. 
				for (int row = 0; row < game_board_matrix.length; row++)
				{

					for (int column =0 ; column < game_board_matrix[row].length; column++)
					{	
						
						// check if the columns and rows in the game_board corresponds to the current Tetriamino position.
						if ((row == matrix_y_position && column == matrix_x_position) || (row == matrix_y2_position && column == matrix_x2_position) 
								|| (row == matrix_y3_position && column == matrix_x3_position) || (row == matrix_y4_position && column == matrix_x4_position))
						{
							// If true, then assign "1" to that game_board index.
							game_board_matrix [row] [column] = 1; 
							
							
							// Check if the activator is active.
							// This controls amount of times the methods are called.
							if (activator == 1) {
								
							// If true, then execute Store_Previous_Blocks(), reset_block(), and check_game_lost().
						
							// The current Tetriamino's its properties are then added to previous block list and disables the activator.
								
							// More information in the method.
							Store_Previous_Blocks();
							
							// Reset the Tetriamino to its default values.
							reset_block();
							
							// The code below checks if the current is out of the game board.
							try {
								check_game_lost();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							}

						}

					}

				}		
				// Continue the time.
				time.start();
				
			}
		
		}
		}
	}
	
	// The method below clears the row  which has 10 indexes that contains "1". By clear, I mean replace the "1" with "0".
	// Also, shift the graphics and the game board indexes to a row down.
	// If the rows were before the deleted row values.
	public void Game_Board_Shift_Down() 
	{
		// This is the last row's number in game_board_matrix.
		int pre_row = 19;

		
		// For loop to individually check each index of the matrix.
		for (int main_row = 0; main_row < game_board_matrix.length; main_row++)
		{
			// Sum of "1" in each row. 
			int sum_of_row = 0;


		for (int main_column =0 ; main_column < game_board_matrix[main_row].length; main_column++)
		{	
			
			// Check if the index contains "1".
			if (game_board_matrix[main_row] [main_column] == 1 )
				
			{
				// If true, then stop time to avoid errors.
				time.stop();
				
				// Add 1 to  sum_of_row.
				sum_of_row++;
				
				// Check if sum_of_row is the value 10;
				if (sum_of_row == 10 )
				{
					// If true then add 1 to score.
					score++;
					
					// Individually check if each block that has the same row.
					for (int row = 0; row < previous_blocks.length; row++)
					{
						// Check If the block is not null.
						if (previous_blocks[row] != null )
						{
						// if true, then check if the previous block has the same row.
						if (previous_blocks[row].getY() == main_row * square_size )
						{
							// If true, then assign the position of the previous block to pre_row.
							pre_row = (int) previous_blocks[row].getY();
							
							// Delete the block.
							previous_blocks[row] = null;

						}
						}
						


					}
					// Individually check if each block that has the same row.
					for (int row = 0; row < previous_blocks.length; row++)
					{
						// Check If the previous block is not null and the row of the previous block is before pre_row.
					if (previous_blocks[row] != null && previous_blocks[row].getY() < pre_row) {
						
					// If true, then move the previous block to a row down.
					previous_blocks[row].setRect(previous_blocks[row].getX(), previous_blocks[row].getY() + square_size, square_size, square_size);
					
					// Check if the previous block is outside the game board.
					if (previous_blocks[row].getY() > 19 * square_size)
					{
						// Delete the previous block.
						previous_blocks[row] = null;
					}
					

					}
					}


					// Individually assign "0" to each index of the main row.
					for (int column = 0; column < game_board_matrix[main_row].length; column++)
					{

						

							game_board_matrix[main_row] [main_column-column] = 0;
							

					}
					
					// Individually shift each index of the game board to a row down.
					for (int row = 1; row < game_board_matrix.length; row++)
					{
					

					// Check if the sum of row is more  -1.
					if (main_row- row +1 > -1 && main_row-row >-1)
					{
					
					// Shift the indexes to a row down.
					game_board_matrix[main_row- row +1] = Arrays.copyOf(game_board_matrix [main_row-row], game_board_matrix [main_row].length);

					// Delete the indexes of the row.
					for (int column = 0; column < game_board_matrix[main_row].length; column++)
					{
						

							game_board_matrix[main_row-row] [main_column-column] = 0;
							

					}
					}
					}
					
					

				}
				// Continue the time.
				time.start();
			}
		
		}
		}
	}
	
	// The method below appends the current Tetriamino to previous_blocks.
	public void Store_Previous_Blocks()
	{
			// The paint method will create new graphic objects in the list.
		
			// Append previous_blocks.
		    previous_blocks = Arrays.copyOf(previous_blocks, previous_blocks.length + 4);
		    
			// Append previous_colors_list.
		    previous_colors_list = Arrays.copyOf(previous_colors_list, previous_colors_list.length + 1);
			previous_colors_list [previous_blocks.length /4 -1] = color; 
			
			

			
			
			// Store the current Tetriamino into previous_blocks.
			previous_blocks [previous_blocks.length -4] = main_block;
			previous_blocks [previous_blocks.length-3] = sub_block_2;
			previous_blocks [previous_blocks.length-2] = sub_block_3;
			previous_blocks [previous_blocks.length-1] = sub_block_4;
		
		
	}
	
	
	// The method checks if the user lost he game.
	public void check_game_lost() throws IOException
	{	
		// Check individually if any previous_blocks is outside of the game board.
		for (int index =0 ; index < previous_blocks.length; index++)
		
		// Check if the previous_block is not null.
		if (previous_blocks[index] != null)
		{
		// if true, then check if the previous block is outside of the screen.
		if (previous_blocks[index].getY() < 0)
		{
			// Display a messagebox to the user informing them that they have lost.
			showMessageDialog(null, "Game Over !");
			
			// Store score into a text file.
			store_score();
			
			// Close the program.
			System.exit(0);
		}
		}
	}
	
	
	// The method below reads the score and compares to the current score.
	// After that, Store the highest score to a text file.
	public void store_score () throws IOException
	{
		// Check if there is already file text file named "Score".
		try (FileReader reader = new FileReader("Score.txt"))
		{
		// If true, then read that text file and assign it to a variable.
		int previous_score;
		previous_score = reader.read();


		// After that check if the score in the file is less than the current score.
		if (previous_score < score)
		{
			
		// If true, then replace the previous score with the current score.
		FileWriter writer = new FileWriter ("Score.txt");
		writer.write(score);
		writer.close();
        
		// Inform the user about the new high score.
        System.out.println("New High Score ! : \t" + score);
		}

		
		
		}
		// If false, then create a new file named "Score.txt".
		catch (IOException e)
		{
			FileWriter writer = new FileWriter ("Score.txt");
			writer.write(score);
			writer.close();
		}
		
	} 
	
	
	// The method resets Tetriamino block's properties to the default.
	public void reset_block()
	{
			// Activate new_block.
			new_block = 1;
			
			// Reset rotator_pressed to default value.
			rotator_pressed = 0;
		
			// Deactivate activator.
			activator = 0;
			
			
			// Reset the Tetriamino rotation values to the default.
			
			Line_x_rotate = 0;
			Line_y_rotate = square_size;
			
			S_x_rotate = 1;
			S_y_rotate = 1;
			
			
			L_x_rotate = 0;
			L_y_rotate = 1;
			L_ox_rotate = 1;
			L_oy_rotate = 0;
			
			
			RL_x_rotate = 0;
			RL_y_rotate = 1;
			RL_ox_rotate = 1;
			RL_oy_rotate = 0;
			
			
			W_x_rotate = 1;
			W_y_rotate = 0;
			W_ox_rotate = -1;
			W_oy_rotate = 1;


			// Reset the Tetriamino position.
			main_block_x_position = width/2;
			main_block_y_position = -100;
	
		
		


	}
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			// When the user presses the up arrow key then rotate the current Tetriamino.
			Rotate_Blocks();

		
		}
		else if (key == KeyEvent.VK_DOWN) {
			
			// Double the speed of fall.
			
			main_block_y_velocity = (square_size * 2);
		}
		
		else if (key == KeyEvent.VK_RIGHT) {
			
			// Move the Tetriamino to the right. 
			
			main_block_x_velocity = square_size;
			
			// Indicate that the key pressed was right arrow key.
			right_or_left = 1;
			
			// Allow the current Tetriamino move. If the current Tetriamino is outside of the screen.
			if (main_block_x_position < (square_size) || sub_block_2_x_position < (square_size)  || sub_block_3_x_position < (square_size)  || sub_block_4_x_position < (square_size) ) {


				main_block_x_position += main_block_x_velocity;


			}
			

		}
		
		else if (key == KeyEvent.VK_LEFT) {
			
			
			// Move the Tetriamino to the left. 
			main_block_x_velocity = -square_size;
			
			// Indicate that the key pressed was left arrow key.
			right_or_left = 0;
			


			
			
			// Allow the current Tetriamino move. If the current Tetriamino is outside of the screen.
			if (main_block_x_position > (width-(square_size * 2)) || sub_block_2_x_position > (width-(square_size * 2)) || sub_block_3_x_position > (width-(square_size * 2)) || sub_block_4_x_position > (width-(square_size * 2))) {


				main_block_x_position += main_block_x_velocity;



			}


			
			


		}
		else if (key == KeyEvent.VK_SPACE)
		{
			// Pause the game when the user presses the spacebar key
			time.start();
		}
		else if (key == KeyEvent.VK_ESCAPE)
		{

			// Pause the game when the user presses the escape key
			time.stop();
			
			
			// call the store_score() method.
			try {
				store_score();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}
		
		
	}
	
	
	// The method below rotates the current Tetriamino block to 90, 180, 270, and 360 degrees.
	public void Rotate_Blocks()
	{
		// Calculate how many times the user has clicked the up arrow key.
		
		rotator_pressed++;
		
		// Rotate variables change the position of sub blocks in the All_Block_Properties.
		
		
		// The Line, S, and flip S Tetriamino have only two possible distinct shapes from rotation
		// So rotator only needs to be pressed twice.
		if (rotator_pressed % 2 == 1) {
			Line_y_rotate = 0;
			Line_x_rotate = square_size;
			S_x_rotate = -1;
			S_y_rotate = -1;
		}
		
		// If the user pressed the up arrow key twice.
		else if (rotator_pressed % 2 == 0) {
			Line_y_rotate = square_size;
			Line_x_rotate = 0;
			S_x_rotate = 1;
			S_y_rotate = 1;
		}

		// The other Tetriamino have four possible distinct shapes from rotation
		// So rotator only needs to be pressed four times.
		if (rotator_pressed % 4 == 0) {

			L_x_rotate = 0;
			L_y_rotate = 1;
			L_ox_rotate = 1;
			L_oy_rotate = 0;
			
			
			RL_x_rotate = 0;
			RL_y_rotate = 1;
			RL_ox_rotate = 1;
			RL_oy_rotate = 0;
			
			
			
			W_x_rotate = 1;
			W_y_rotate = 0;
			W_ox_rotate = -1;
			W_oy_rotate = 1;
		}
		else if (rotator_pressed % 4 == 1) {
			L_x_rotate = 1;
			L_y_rotate = 0;
			L_ox_rotate = -2;
			L_oy_rotate = 1;
			
			
			RL_x_rotate = 1;
			RL_y_rotate = 0;
			RL_ox_rotate = 0;
			RL_oy_rotate = -1;
			
			
			W_x_rotate = 0;
			W_y_rotate = 1;
			W_ox_rotate = -1;
			W_oy_rotate = -1;
		}
		else if (rotator_pressed % 4 == 2) {
			L_x_rotate = 0;
			L_y_rotate = 1;
			L_ox_rotate = -1;
			L_oy_rotate = -2;
			
			
			RL_x_rotate = 0;
			RL_y_rotate = 1;
			RL_ox_rotate = -1;
			RL_oy_rotate = -2;
			
			
			W_x_rotate = 1;
			W_y_rotate = 0;
			W_ox_rotate = -1;
			W_oy_rotate = -1;
		}
		else if (rotator_pressed % 4 == 3) {
			L_x_rotate = 1;
			L_y_rotate = 0;
			L_ox_rotate = 0;
			L_oy_rotate = -1;
			
			RL_x_rotate = 1;
			RL_y_rotate = 0;
			RL_ox_rotate = -2;
			RL_oy_rotate = 1;
			
			
			W_x_rotate = 0;
			W_y_rotate = 1;
			W_ox_rotate = 1;
			W_oy_rotate = -1;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// Redundant methods require by the complier.
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
