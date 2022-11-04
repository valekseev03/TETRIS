package ibcs3.ia.FinalProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Timer;



public class gameTetris extends JPanel{
	//Variables			
		private String[][] currentBoard;
		private static BufferedImage[] backgroundImage = new BufferedImage[3];
		private static BufferedImage stillImage;
		private int percentProgress = 0; //by 10's
		private int currentRound = 1;
		private static int boardLength = (10 * 35);
		private static int boardHeight = (20 * 35);
		
		private fallingBlocks b;
		private rowClearer r = new rowClearer();
		
		private Timer gameTimer;
		private final int DELAY = 16;
		private ArrayList<String> al_keysPressed = new ArrayList<String>();
		TimerListener gameTimerListener = new TimerListener();
		private final boolean DEBUG = false;
		boolean playing = false;
		JFrame mainFrame;
		
	//Constructor
		public gameTetris(JFrame mainFrame) {
			//Set panel attributes.
			this.setPreferredSize(new Dimension(boardLength, boardHeight));
			this.setBackground(Color.GREEN);
			this.mainFrame = mainFrame;
			
			//Set player assets.
			this.b = new fallingBlocks();
			this.r = new rowClearer();
			this.gameTimer = new Timer(DELAY, this.gameTimerListener);
			
			try 
			{
				this.backgroundImage[0] = ImageIO.read(new File("image1.jpg"));
				this.backgroundImage[1] = ImageIO.read(new File("Image2.jpg"));
				this.backgroundImage[2] = ImageIO.read(new File("image3.jpeg"));				
				this.stillImage = ImageIO.read(new File("face.png"));
			} 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(mainFrame, ("Error: Background Images Could Not Load"));			
				e.printStackTrace();
			}
			
			//Set key bindings.
			this.setKeyBindings();
			
			JOptionPane.showMessageDialog(mainFrame, "Use Right and Left Arrow Keys to move left or right");
			JOptionPane.showMessageDialog(mainFrame, "Use Up and Down Arrow Keys to spin blocks");
			JOptionPane.showMessageDialog(mainFrame, "Use Spacebar to instantly place block down");
			this.playing = true;
			playGame();
		}
		
	//Functions
		
		//Lose Game: Print Lose Message and Exit Project 
		public void loseGame() {
			System.out.println("You Lose!");
			JOptionPane.showMessageDialog(mainFrame, "You Lose!");
			this.playing = false;
			gameTimer.stop();
			this.mainFrame.dispose();
		}
		
		//Win Game: Print Win Message and Exit Project
		public void winGame() {
			System.out.println("You Win!");
			JOptionPane.showMessageDialog(mainFrame, "You Win!");
			this.playing = false;
			gameTimer.stop();
			this.mainFrame.dispose();
		}
		
		//Starts the Game
 		public void playGame() {
 			currentBoard = r.getBoard();
 			b.updateBoard(currentBoard);
 			JOptionPane.showMessageDialog(mainFrame, "Round #" + currentRound + ", Percent of Picture Found: " + percentProgress + "%");			
 			this.playing = true;
 		}
		
		
		//Sets key bindings so pressing keys executes functions
 		public void setKeyBindings()
 		{
 			//Bind left and right to horizontal movement.
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false), "goLeft");
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true), "stopLeft");
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false), "goRight");
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true), "stopRight");
 			
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,false), "spinRight");
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,true), "nspinRight");
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,false), "spinLeft");
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,true), "nspinLeft");
 			
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0,false), "fallDown");
 			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0,true), "nfallDown");
 			
 			
 			this.getActionMap().put("goLeft", new InputAction("LEFT",false));
 			this.getActionMap().put("stopLeft", new InputAction("LEFT",true));
 			this.getActionMap().put("goRight", new InputAction("RIGHT",false));		
 			this.getActionMap().put("stopRight", new InputAction("RIGHT",true));
 			
 			this.getActionMap().put("spinRight", new InputAction("UP",false));
 			this.getActionMap().put("nspinRight", new InputAction("UP",true));
 			this.getActionMap().put("spinLeft", new InputAction("DOWN",false));		
 			this.getActionMap().put("nspinLeft", new InputAction("DOWN",true));
		
 			this.getActionMap().put("fallDown", new InputAction("SPACE",false));		
 			this.getActionMap().put("nfallDown", new InputAction("SPACE",true));
		
 		}
 		
 		private class InputAction extends AbstractAction
 		{
 			private static final long serialVersionUID = 1L;
 			
 			private String s_key;
 			private boolean b_released;
 			
 			public InputAction(String key, boolean released)
 			{
 				this.s_key = key;
 				this.b_released = released;
 			}
 			
 			
 			//When a key is pressed or released, do this.
 			@Override
 			public void actionPerformed(ActionEvent e) 
 			{
 				if(!this.b_released)
 				{
 					if(!al_keysPressed.contains(this.s_key))
 					{
 						if(this.s_key == "LEFT"){
 							b.moveLeft();
 						}
 						
 						//Move right if needed.
 						if(this.s_key == "RIGHT"){
 							b.moveRight();
 						}
 						
 						if(this.s_key == "UP"){
 							b.spin(1);
 							
 							switch(b.currentPosition) {
								case STATE_UP:												
	 							while(b.detectSideCollision(false) || b.detectCollision()) {
	 								b.spin(-1);
	 							}
 							break;
 							
								case STATE_DOWN:
								while(b.detectSideCollision(true) || b.detectCollision()) {
									b.spin(-1);
								}
								break;
								
								default:
									while(b.detectCollision()) {
										b.spin(-1);
									}
									break;
							}
 						}
 						
 						//Move right if needed.
 						if(this.s_key == "DOWN"){	
 							b.spin(-1);
 														
 							switch(b.currentPosition) {
								case STATE_DOWN:												
	 							while(b.detectSideCollision(false) || b.detectCollision()) {
	 								b.spin(1);
	 							}
								break;
								
								case STATE_UP:
								while(b.detectSideCollision(true) || b.detectCollision()) {
									b.spin(1);
								}
								break;
								
								default:
									while(b.detectCollision()) {
										b.spin(1);
									}
								break;
 							}
 						}
 						
 						if(this.s_key == "SPACE"){
 							if(!b.detectCollision()) {
 								while(!b.detectCollision()) {
	 		 						b.yBlock++;
	 		 						
	 		 						if(b.secondBlockLength > 0) {
	 		 							b.ysecondBlock++;
	 		 						}
	 		 					}
	 						}					
 						}
 						
 						//Disallow left+right.
 						if(!(this.s_key == "LEFT" && al_keysPressed.contains("RIGHT")) && !(this.s_key == "RIGHT" && al_keysPressed.contains("LEFT")))
 						{
 							al_keysPressed.add(this.s_key);
 
 						}
 					}
 					if(!gameTimer.isRunning() && playing) gameTimer.start();
 				}
 				else
 				{
 					if(al_keysPressed.contains(this.s_key))
 					{	
 						al_keysPressed.remove(this.s_key);
 					}
 				}
 			}
 		}
	
 		//Updates array
		private void updateBoard() {
			boolean clippingError = false;
			
			try {
				for(int i = 0; i < b.blockLength; i++) {
					switch(b.currentPosition) {
						case STATE_DOWN:
							currentBoard[((int)this.b.yBlock/35) + (i)][((int)this.b.xBlock/35)] = "b";
							break;
							
						case STATE_UP:
							currentBoard[((int)this.b.yBlock/35) - (i)][((int)this.b.xBlock/35)] = "b";
							break;
							
						case STATE_LEFT:
							currentBoard[(int)(this.b.yBlock/35)][(int)(this.b.xBlock/35) - (i)] = "b";
							break;
							
						case STATE_RIGHT:
							currentBoard[(int)(this.b.yBlock/35)][(int)(this.b.xBlock/35) + (i)] = "b";
							break;							
						}
				}
				
				if(b.secondBlockLength > 0) {
					for(int i = 0; i < b.secondBlockLength; i++) {
						switch(b.currentPosition) {
							case STATE_DOWN:
								currentBoard[((int)this.b.ysecondBlock/35) + (i)][((int)this.b.xsecondBlock/35)] = "b";
								break;
								
							case STATE_UP:
								currentBoard[((int)this.b.ysecondBlock/35) - (i)][((int)this.b.xsecondBlock/35)] = "b";
								break;
								
							case STATE_LEFT:
								currentBoard[(int)(this.b.ysecondBlock/35)][(int)(this.b.xsecondBlock/35) - (i)] = "b";
								break;
								
							case STATE_RIGHT:
								currentBoard[(int)(this.b.ysecondBlock/35)][(int)(this.b.xsecondBlock/35) + (i)] = "b";
								break;							
							}
					}
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("error");				
				currentBoard = r.getBoard();
				b.resetBlock();
				clippingError = true;
			}
			
			if((int)(this.b.yBlock/35) <= 0 && !clippingError) {
				loseGame();
			}
			
			r.setBoard(currentBoard);
			r.clearRows();
			currentBoard = r.getBoard();
			b.updateBoard(currentBoard);
		}
		
		
		private void testifPlaced() {
			
			//detects if block is moving or at bottom
			if(b.detectCollision() || b.yBlock >= boardHeight - 35 || (b.ysecondBlock >= boardHeight - 35 && b.secondBlockLength > 0)) {
				b.counter++;
				
				if(b.counter >= 50) {
					updateBoard();
					b.resetBlock();
					
					//increases speed of block
					for(int i = 1; i <= r.rowsCleared; i++) {
						percentProgress += 10;
						b.fallDelay -= 2;
						
						if(percentProgress % 20 == 0 && r.rowsCleared > 0) {
							b.fallMuliplier++;
						}
					}
					
					if(r.rowsCleared > 0) {
						JOptionPane.showMessageDialog(mainFrame, "Round #" + currentRound + ", Percent of Picture Found: " + percentProgress + "%");			
					}	
					
					if(percentProgress >= 100) {
						//shows image then blocks image for next round
						int tempInt = percentProgress;
						percentProgress = 100;
						paintComponent(mainFrame.getGraphics());
						percentProgress = tempInt;
						percentProgress -= 100;
						currentRound++;
						
						if(currentRound <= 3) {
							currentRound--;
							b.blockLength = 0;
							b.secondBlockLength = 0;
							currentBoard = r.blankBoard();
							tempInt = percentProgress;
							percentProgress = 100;
							paintComponent(mainFrame.getGraphics());
							currentRound++;
							currentBoard = r.getBoard();
							percentProgress = tempInt;
							b.resetBlock();
							JOptionPane.showMessageDialog(mainFrame, "Round #" + currentRound + ", Percent of Picture Found: " + percentProgress + "%");										
						}else {
							//shows final image then executes win game conditions
							currentRound = 3;
							b.blockLength = 0;
							b.secondBlockLength = 0;
							currentBoard = r.blankBoard();
							percentProgress = 100;
							paintComponent(mainFrame.getGraphics());
							winGame();
						}
					}
				}
			}
		}
		
		
		@Override	
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.drawImage(this.backgroundImage[this.currentRound - 1], null, 0, 0);
			
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, boardLength, (int)(boardHeight * ((double)(100 - this.percentProgress) / (100.0))));
			
			for(int i = 0; i < currentBoard.length; i++) {
				for(int j = 0; j < currentBoard[0].length; j++) {
					if(currentBoard[i][j] == "b") {
						g2d.drawImage(this.stillImage, null, j * 35, i * 35);						
					}
				}
			}
			
			//Draw image.
			for(int i = 0; i < b.blockLength; i++) {
				switch(b.currentPosition) {
					case STATE_DOWN:
						g2d.drawImage(this.b.getImage(), null, (int)this.b.xBlock, (int)this.b.yBlock + (i * 35));
						break;
						
					case STATE_UP:
						g2d.drawImage(this.b.getImage(), null, (int)this.b.xBlock, (int)this.b.yBlock - (i * 35));
						break;
						
					case STATE_LEFT:
						g2d.drawImage(this.b.getImage(), null, (int)this.b.xBlock - (i * 35), (int)this.b.yBlock);
						break;
						
					case STATE_RIGHT:
						g2d.drawImage(this.b.getImage(), null, (int)this.b.xBlock + (i * 35), (int)this.b.yBlock);
						break;
						
				}
			}
			
			if(b.secondBlockLength > 0) {
				for(int i = 0; i < b.secondBlockLength; i++) {
					switch(b.currentPosition) {
						case STATE_DOWN:
							g2d.drawImage(this.b.getImage(), null, (int)this.b.xsecondBlock, (int)this.b.ysecondBlock + (i * 35));
							break;
							
						case STATE_UP:
							g2d.drawImage(this.b.getImage(), null, (int)this.b.xsecondBlock, (int)this.b.ysecondBlock - (i * 35));
							break;
							
						case STATE_LEFT:
							g2d.drawImage(this.b.getImage(), null, (int)this.b.xsecondBlock - (i * 35), (int)this.b.ysecondBlock);
							break;
							
						case STATE_RIGHT:
							g2d.drawImage(this.b.getImage(), null, (int)this.b.xsecondBlock + (i * 35), (int)this.b.ysecondBlock);
							break;
							
					}
				}
			}
			
			//Debug
			if(DEBUG)
			{
				g2d.setColor(Color.RED);
				if(this.al_keysPressed.contains("UP")) g2d.drawString("^", 10, 20);
				if(this.al_keysPressed.contains("LEFT")) g2d.drawString("<", 0, 30);
				if(this.al_keysPressed.contains("RIGHT")) g2d.drawString(">", 20, 30);
				if(this.al_keysPressed.contains("DOWN")) g2d.drawString("v", 10, 40);
			}
			
			g2d.dispose();
		}
		
		
		private class TimerListener implements ActionListener
	{

		//When a timer ticks, do this.
		@Override
		public void actionPerformed(ActionEvent ae){
			//When gameTimer ticks, do this.
			if(ae.getSource() == gameTimer){							

				b.fall();
				testifPlaced();
				
			}	

			//Redraw to screen.
			repaint();
		}		
	}	
	
	
}
