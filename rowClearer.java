package ibcs3.ia.FinalProject;


public class rowClearer {
	private String[][] gameBoard;
	private static int boardLength = 10;
	private static int boardHeight = 20;
	private static String fullRow = "";
	int rowsCleared = 0;
	
	public rowClearer() {
		fullRow = "";
		this.gameBoard = new String[boardHeight][boardLength];
		
		for(int i = 0; i < rowClearer.boardHeight; i++) {
			for(int j = 0; j < rowClearer.boardLength; j++) {
				this.gameBoard[i][j] = "_";
			}
		}
		
		for(int i = 0; i < 10; i++) {
			rowClearer.fullRow += "b"; //blocks
		}
	}
	
	public String[][] getBoard() {
		
		return this.gameBoard;
	}
	
	public String[][] blankBoard() {
		String [][] tempBoard = new String[boardHeight][boardLength];
		for(int i = 0; i < rowClearer.boardHeight; i++) {
			for(int j = 0; j < rowClearer.boardLength; j++) {
				tempBoard[i][j] = "_";
			}
		}
		return tempBoard;
	}
	
	public void setBoard(String[][] newBoard) {
		if(newBoard[0].length == rowClearer.boardLength && newBoard.length == rowClearer.boardHeight) {
			this.gameBoard = newBoard;
		}
	}
	
	public void clearRows() {
		rowsCleared = 0;
		String tempString = "";
		
		//Looping Through Board
		for(int i = 0; i < rowClearer.boardHeight; i++) {
			//Getting Row Values
			for(int j = 0; j < rowClearer.boardLength; j++) {
				tempString += this.gameBoard[i][j];
			}
			
			//Checking if Row is Full
			if(tempString.equals(fullRow)) {
					rowsCleared++;
				//Clearing Full Rows
					for(int j = 0; j < rowClearer.boardLength; j++) {
						this.gameBoard[i][j] = "_";
					}
				
				//Moving Rows Down
					for(int j = i; j > 0; j--) {
						this.gameBoard[j] = this.gameBoard[j - 1];
					}
				
				//Filling Top Row With Empty Row
					this.gameBoard[0] = new String[rowClearer.boardLength];
					for(int j = 0; j < this.gameBoard[0].length; j++) {
						this.gameBoard[0][j] = "_";
					}
			}
			
			tempString = "";
			
		}
	}	
}
