package ibcs3.ia.FinalProject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import ibcs3.ia.FinalProject.fallingBlocks.PossiblePositions;

public class fallingBlocks {
	private BufferedImage block_image;
	int xBlock = 4 * 35;
	int yBlock = 0;
	int xBlock2 = 0;
	boolean secondX = false;
	
	int xsecondBlock = 0;
	int ysecondBlock = 0;
	int secondBlockLength = 0;
	int yDif = 0;
	int xDif = 0;
	
	int fallRate = 1;
	int fallMuliplier = 2;
	int moveSpeed = 35;
	int blockLength = 2;
	int fallDelay = 16;
	int delayCounter = 1;
	int delayCounterDivider = 1;
	int counter = 0;
	
	private static int boardLength = (10 * 35);
	private static int boardHeight = (20 * 35);
	
	private String[][] currentBoard;
	
	private int errorCounter = 0;
	
	enum PossiblePositions
	{
		STATE_UP,
		STATE_RIGHT,
		STATE_DOWN,
		STATE_LEFT
	}
	
	enum PossibleBlocks
	{
		BLOCK_FOUR_LINE,
		BLOCK_T,
		BLOCK_LEFT_L,
		BLOCK_RIGHT_L,
		BLOCK_SQUARE,
		BLOCK_RIGHT_ZIGZAG,
		BLOCK_LEFT_ZIGZAG,
		BLOCK_RANDOM_STRAIGHT
	}

	PossiblePositions currentPosition = PossiblePositions.STATE_DOWN;
	PossibleBlocks currentBlock = PossibleBlocks.BLOCK_T;
	
	public fallingBlocks() {
		try 
		{
			this.block_image = ImageIO.read(new File("face2.png"));
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, ("Error: Block Image Could Not Load"));
			e.printStackTrace();
		}
		
		resetBlock();
	}
	
	
	
	public void resetBlock() {
		int randomizer = (int)(Math.random() * 8);
		
		if(randomizer == 0) {
			currentBlock = PossibleBlocks.BLOCK_FOUR_LINE;
			blockLength = 4;
			secondBlockLength = 0;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 0;
			ysecondBlock = 0;
			xDif = 0;
			yDif = 0;
		}else if (randomizer == 1) {
			currentBlock = PossibleBlocks.BLOCK_T;
			blockLength = 3;
			secondBlockLength = 1;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 4 * 35;
			ysecondBlock = 1 * 35;
			xDif = 1;
			yDif = 1;
		}else if (randomizer == 2) {
			currentBlock = PossibleBlocks.BLOCK_LEFT_L;
			blockLength = 3;
			secondBlockLength = 1;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 4 * 35;
			ysecondBlock = 0;
			xDif = 1;
			yDif = 1;
		}else if (randomizer == 3) {
			currentBlock = PossibleBlocks.BLOCK_RIGHT_L;
			blockLength = 3;
			secondBlockLength = 1;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 6 * 35;
			ysecondBlock = 0;
			xDif = 1;
			yDif = 1;
		}else if (randomizer == 4) {
			currentBlock = PossibleBlocks.BLOCK_SQUARE;
			blockLength = 2;
			secondBlockLength = 2;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 6 * 35;
			ysecondBlock = 0;
			xDif = 1;
			yDif = 1;
		}else if (randomizer == 5) {
			currentBlock = PossibleBlocks.BLOCK_RIGHT_ZIGZAG;
			blockLength = 2;
			secondBlockLength = 2;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 6 * 35;
			ysecondBlock = 1 * 35;
			xDif = 1;
			yDif = 1;
		}else if (randomizer == 6) {
			currentBlock = PossibleBlocks.BLOCK_LEFT_ZIGZAG;
			blockLength = 2;
			secondBlockLength = 2;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 4 * 35;
			ysecondBlock = 1 * 35;
			xDif = 1;
			yDif = 1;
		}else {
			currentBlock = PossibleBlocks.BLOCK_RANDOM_STRAIGHT;
			blockLength = (int)(Math.random() * 3) + 1;
			secondBlockLength = 0;
			
			xBlock = 5 * 35;
			yBlock = 0;
			
			xsecondBlock = 0;
			ysecondBlock = 0;
			xDif = 0;
			yDif = 0;
		}
		
		counter = 0;		
		currentPosition = PossiblePositions.STATE_DOWN;
		
	}
	
	
	public void updateBoard(String[][] newBoard) {
		currentBoard = newBoard;
	}
	
	public void spin(int i) {
			errorCounter++;
			int setter = this.currentPosition.ordinal();
			setter += i;
			
			if(setter < 0) {
				setter = 3;
			}
			
			if(setter > 3) {
				setter = 0;
			}
			
			if(setter == 0) {
				this.currentPosition = PossiblePositions.STATE_UP;
				switch(this.currentBlock) {
					case BLOCK_T:
					case BLOCK_RIGHT_ZIGZAG:
						xsecondBlock = xBlock - (35 * xDif);
						ysecondBlock = yBlock - (35 * yDif);
						break;
					case BLOCK_LEFT_ZIGZAG:
						xsecondBlock = xBlock + (35 * xDif);
						ysecondBlock = yBlock - (35 * yDif);
						break;
					case BLOCK_LEFT_L:
						xsecondBlock = xBlock + (35 * xDif);
						ysecondBlock = yBlock;
						break;
					case BLOCK_RIGHT_L:
					case BLOCK_SQUARE:
						xsecondBlock = xBlock - (35 * xDif);
						ysecondBlock = yBlock;
						break;
				}
			}else if (setter == 1) {
				this.currentPosition = PossiblePositions.STATE_RIGHT;
				switch(this.currentBlock) {
					case BLOCK_T:
					case BLOCK_RIGHT_ZIGZAG:
						xsecondBlock = xBlock + (35 * xDif);
						ysecondBlock = yBlock - (35 * yDif);
						break;
					case BLOCK_LEFT_ZIGZAG:
						xsecondBlock = xBlock + (35 * xDif);
						ysecondBlock = yBlock + (35 * yDif);
						break;
					case BLOCK_LEFT_L:
						xsecondBlock = xBlock;
						ysecondBlock = yBlock + (35 * yDif);
						break;
					case BLOCK_RIGHT_L:
					case BLOCK_SQUARE:
						xsecondBlock = xBlock;
						ysecondBlock = yBlock - (35 * xDif);
						break;
				}
			}else if (setter == 2) {
				this.currentPosition = PossiblePositions.STATE_DOWN;
				switch(this.currentBlock) {
					case BLOCK_T:
					case BLOCK_RIGHT_ZIGZAG:
						xsecondBlock = xBlock + (35 * xDif);
						ysecondBlock = yBlock + (35 * yDif);
						break;
					case BLOCK_LEFT_ZIGZAG:
						xsecondBlock = xBlock - (35 * xDif);
						ysecondBlock = yBlock + (35 * yDif);
						break;
					case BLOCK_LEFT_L:
						xsecondBlock = xBlock - (35 * xDif);
						ysecondBlock = yBlock;
						break;
					case BLOCK_RIGHT_L:
					case BLOCK_SQUARE:
						xsecondBlock = xBlock + (35 * xDif);
						ysecondBlock = yBlock;
						break;
				}
			}else {				
				this.currentPosition = PossiblePositions.STATE_LEFT;
				switch(this.currentBlock) {
					case BLOCK_T:
					case BLOCK_RIGHT_ZIGZAG:
						xsecondBlock = xBlock - (35 * xDif);
						ysecondBlock = yBlock + (35 * yDif);
						break;
					case BLOCK_LEFT_ZIGZAG:
						xsecondBlock = xBlock - (35 * xDif);
						ysecondBlock = yBlock - (35 * yDif);
						break;
					case BLOCK_LEFT_L:
						xsecondBlock = xBlock;
						ysecondBlock = yBlock - (35 * yDif);
						break;
					case BLOCK_RIGHT_L:
					case BLOCK_SQUARE:
						xsecondBlock = xBlock;
						ysecondBlock = yBlock + (35 * yDif);
						break;
				}
			}
			
			if(errorCounter >= 100) {
				resetBlock();
				errorCounter = 0;
			}
	}
	
	public boolean detectSideCollision(boolean left_or_right) {
		try {
			int multiplier = 1;
			int adder = 1;
			
			if(left_or_right) {
				adder = -1;
			}
			
			switch(this.currentPosition) {
				case STATE_LEFT:
					multiplier = -1;
									
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35)][(int)(xBlock/35) + (i * multiplier) + adder] == "b") {
								return true;
						}
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35)][(int)(xsecondBlock/35) + (i * multiplier) + adder] == "b") {
									return true;
							}
						}
					}
					break;
							
				case STATE_RIGHT:
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35)][(int)(xBlock/35) + (i * multiplier) + adder] == "b") {
							return true;
						}
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35)][(int)(xsecondBlock/35) + (i * multiplier) + adder] == "b") {
									return true;
							}
						}
					}
					break;
				
				case STATE_DOWN:
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35) + (i * multiplier)][(int)(xBlock/35) + adder] == "b") {
							return true;
						}
	
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35) + (i * multiplier)][(int)(xsecondBlock/35) + adder] == "b") {
									return true;
							}
						}
					}
					break;
							
				case STATE_UP:
					multiplier = -1;
					
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35) + (i * multiplier)][(int)(xBlock/35) + adder] == "b") {
										return true;
						}
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35) + (i * multiplier)][(int)(xsecondBlock/35) + adder] == "b") {
									return true;
							}
						}
					}
					break;
						
				}		
				return false;
				
			}catch(Exception e) {
				return true;
			}	
	}
	
	public boolean detectCollision() {
		//detect down Collision of blocks compared to currentBoard
		try {
			int multiplier = 1;
			switch(this.currentPosition) {
				case STATE_LEFT:
					multiplier = -1;
									
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35) + 1][(int)(xBlock/35) + (i * multiplier)] == "b") {
								return true;
						}
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35) + 1][(int)(xsecondBlock/35) + (i * multiplier)] == "b") {
									return true;
							}
						}
					}
					break;
							
				case STATE_RIGHT:
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35) + 1][(int)(xBlock/35) + (i * multiplier)] == "b") {
							return true;
						}
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35) + 1][(int)(xsecondBlock/35) + (i * multiplier)] == "b") {
									return true;
							}
						}
					}
					break;
				
				case STATE_DOWN:
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35) + (i * multiplier) + 1][(int)(xBlock/35)] == "b") {
							return true;
						}
	
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35) + (i * multiplier) + 1][(int)(xsecondBlock/35)] == "b") {
									return true;
							}
						}
					}
					break;
							
				case STATE_UP:
					for(int i = 0; i < blockLength; i++) {
						if(currentBoard[(int)(yBlock/35) + 1][(int)(xBlock/35)] == "b") {
										return true;
						}
					}
					
					if(secondBlockLength > 0) {
						for(int i = 0; i < secondBlockLength; i++) {
							if(currentBoard[(int)(ysecondBlock/35) + 1][(int)(xsecondBlock/35)] == "b") {
									return true;
							}
						}
					}
					break;
						
				}		
				return false;
				
			}catch(Exception e) {
				return true;
			}		
	}
	
	public void moveLeft() {
		if(secondBlockLength > 0) {
			if(secondX) {
				if(this.xBlock > 0 && !detectSideCollision(true) && this.xBlock2 > 0 && this.xsecondBlock > 0) {
					this.xBlock -= moveSpeed;
					this.xBlock2 -= moveSpeed;
					this.xsecondBlock -= moveSpeed;
				}
			}else {
				if(this.xBlock > 0 && !detectSideCollision(true) && this.xsecondBlock > 0) {
					this.xBlock -= moveSpeed;
					this.xsecondBlock -= moveSpeed;
				}
			}
		}else {
			if(secondX) {
				if(this.xBlock > 0 && !detectSideCollision(true) && this.xBlock2 > 0) {
					this.xBlock -= moveSpeed;
					this.xBlock2 -= moveSpeed;
				}
			}else {
				if(this.xBlock > 0 && !detectSideCollision(true)) {
					this.xBlock -= moveSpeed;
				}
			}
		}
	}
	
	public void moveRight() {
		if(secondBlockLength > 0) {
			if(secondX) {
				if(this.xBlock < boardLength - 35 && !detectSideCollision(false) && this.xBlock2 < boardLength - 35 && this.xsecondBlock < boardLength - 35) {
					this.xBlock += moveSpeed;
					this.xBlock2 += moveSpeed;
					this.xsecondBlock += moveSpeed;
				}
			}else {
				if(this.xBlock < boardLength - 35 && !detectSideCollision(false) && this.xsecondBlock < boardLength - 35) {
					this.xBlock += moveSpeed;
					this.xsecondBlock += moveSpeed;
				}
			}
		}else {
			if(secondX) {
				if(this.xBlock < boardLength - 35 && !detectSideCollision(false) && this.xBlock2 < boardLength - 35) {
					this.xBlock += moveSpeed;
					this.xBlock2 += moveSpeed;
				}
			}else {
				if(this.xBlock < boardLength - 35 && !detectSideCollision(false)) {
					this.xBlock += moveSpeed;
				}
			}
		}
	}
	
	
	public void fall() {	
		delayCounter++;
		if(delayCounter >= (fallDelay / delayCounterDivider)) {
			if(secondBlockLength > 0) {
				if(!detectCollision() && (this.yBlock < boardHeight - 35 && this.ysecondBlock < boardHeight - 35)) {
					counter = 0;
					this.yBlock += fallRate * fallMuliplier;
					this.ysecondBlock += fallRate * fallMuliplier;
				}else if (this.yBlock > boardHeight - 35){
					switch(this.currentBlock) {
					case BLOCK_T:
					case BLOCK_RIGHT_ZIGZAG:
					case BLOCK_LEFT_ZIGZAG:
						this.yBlock = boardHeight - 35;
						this.ysecondBlock = yBlock - (35 * yDif);
						break;
					case BLOCK_LEFT_L:
					case BLOCK_RIGHT_L:
					case BLOCK_SQUARE:
						this.ysecondBlock = boardHeight - 35;
						this.yBlock = ysecondBlock;
						break;
					}
				}else if (this.yBlock > boardHeight - 35) {
					switch(this.currentBlock) {
					case BLOCK_T:
					case BLOCK_RIGHT_ZIGZAG:
						this.ysecondBlock = boardHeight - 35;
						this.yBlock = ysecondBlock - (35 * yDif);
						break;
						
					case BLOCK_LEFT_L:
					case BLOCK_RIGHT_L:
					case BLOCK_SQUARE:
						this.ysecondBlock = boardHeight - 35;
						this.yBlock = ysecondBlock;
						break;
					}
				}
			}else {
				if(!detectCollision() && this.yBlock < boardHeight - 35) {
					this.yBlock += fallRate * fallMuliplier;
					counter = 0;
				}else if (this.yBlock > boardHeight - 35){
					this.yBlock = boardHeight - 35;
				}
			}
			
			delayCounter = 1;
		}
	}
	
	public BufferedImage getImage()
	{
		return block_image;
	}
}
