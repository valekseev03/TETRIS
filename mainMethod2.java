package ibcs3.ia.FinalProject;

import javax.swing.JFrame;

/*
 	Tetris With A Couple of Extra Blocks
	However Spinning Blocks in Certain Places Cause Errors 
	(So You Can Occasionally Lose a Block) 
*/

public class mainMethod2 {
	public static void main(String[] args)
	{
		JFrame gui = new JFrame("AlmostTetris the Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameTetris game = new gameTetris(gui);
		
		//Add panel to frame.
		gui.getContentPane().add(game);
		
		//Pack and set visible.
		gui.pack();
		gui.setResizable(false);
		gui.setVisible(true);
		
		//Program continues to run until frame is closed.

	}
}
