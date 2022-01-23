import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;



public class Tetris_Frame extends JFrame{
	
	// Instance variables that are responsible for adjusting the JFrame size
	
	private int width = 275;
	private int height = 600;
	
	
	// Default Constructor that has its own properties such as JPanel and size.
	public Tetris_Frame() {
		
		// Print the instruction for the user.
		instructions();
		
		// Create a modified JPanel object that contains the main graphics and script of the game. 
		
		Tetris_JPanel game_board = new Tetris_JPanel();
		
		// Add the modified JPanel to this JFrame.
		
		add(game_board);
		
		// Set the size of the JFrame to the specified values and make the window resizable.
		
		setSize(width, height);
		setResizable(true);

		// Close the game when the user clicks the close button.
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Make the JFrame visible,
		
		setVisible(true);
		
	}
	
	// The method below prints the instruction for the user.
	public void instructions() 
	{
		System.out.println("Press the left and right keys to move the Tetriamino horizontally.");
		System.out.println("Press the down keys to  double the speed to move the Tetriamino.");
		System.out.println("Press the up keys to rotate the Tetriamino.");
		System.out.println("Press the spacbar key to resume the game.");
		System.out.println("Press the escape key to pause the game.");
	}
	
	// Create the Tetris_JFrame with proper exception error handlers.
	
	public static void main(String[] args) throws IOException {
		

		
		
		// Try to create the main window.
		
		try {
			Tetris_Frame frame = new Tetris_Frame ();
		} 
		
		// If the object creation failed then:
		
		
		// Check if it was due to index of out bounds error.
		
		catch (ArrayIndexOutOfBoundsException  error_1) {
			
			// If it was true then store the error as string in a variable.
			
			
			String error = error_1.toString() + "\t Error line:\t" + error_1.getStackTrace()[0].getLineNumber() + "\tClass:\t" + error_1.getStackTrace()[0].getClassName();
			
			// Write create and store error in the Error_log.txt file.
			
			FileWriter writer = new FileWriter("Error_log.txt");
			writer.write(error);
			writer.close();
			System.out.println("Sorry there was an error! \n Please email me Error_log.txt to fix this issue!");
		}
		
		
		// Check if it was due to an arithmetic error.
		
		catch (ArithmeticException   error_2) {
			
			// If it was true then store the error as string in a variable.

			String error = error_2.toString() + "\t Error line:\t" + error_2.getStackTrace()[0].getLineNumber() + "\tClass:\t" + error_2.getStackTrace()[0].getClassName();

			// Write create and store error in the Error_log.txt file.
			
			FileWriter writer = new FileWriter("Error_log.txt");
			writer.write(error);
			writer.close();
			System.out.println("Sorry there was an error! \n Please email me Error_log.txt to fix this issue!");
		}
		
		
		// Check if it was due to an null pointer error.
		
		catch (NullPointerException    error_3) {
			
			// If it was true then store the error as string in a variable.
			
			String error = error_3.toString() + "\t Error line:\t" + error_3.getStackTrace()[0].getLineNumber() + "\tClass:\t" + error_3.getStackTrace()[0].getClassName();
			
			
			// Write create and store error in the Error_log.txt file.
			
			FileWriter writer = new FileWriter("Error_log.txt");
			writer.write(error);
			writer.close();
			System.out.println("Sorry there was an error! \n Please email me Error_log.txt to fix this issue!");
		}

		
	}

}
