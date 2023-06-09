package core;

public class Maze {
    public static final int STAMINA_FOR_ONE_BOULDER = 50;
    public static final int STAMINA_FOR_BREAKABLE_WALL = 2*STAMINA_FOR_ONE_BOULDER;
    private int playerRow, playerCol, playerOrientation;
    private boolean playerEscaped = false;
    private int rows, cols;
    private int stamina;
    //# is wall, . is empty, o is movable
    //X is breakable wall, x is half-broken breakable wall
    //_ is pressure plate, | is pressure plate with boulder, G is gate
    private char[][] obstacles;
    /*
    orientation:
      1
    2   0
      3
    */

    public Maze(MazeTemplate mazeTemplate) {
        rows = mazeTemplate.mazeStr.length;
        cols = mazeTemplate.mazeStr[0].length();
        playerRow = -1;
        playerCol = -1;
        obstacles = new char[rows][cols];
        for(int row=0; row<rows; row++) {
            for(int col=0; col<cols; col++) {
                char ch = mazeTemplate.mazeStr[row].charAt(col);
                if(ch == 'P') {
                    obstacles[row][col] = '.';
                    playerRow = row;
                    playerCol = col;
                }
                else obstacles[row][col] = ch;
            }
        }
        this.stamina = mazeTemplate.startingStamina;
        this.playerOrientation = 0;
    }
    public void left() {
        playerOrientation = (playerOrientation + 1) % 4;
    }
    public void right() throws InterruptedException {
        playerOrientation = (playerOrientation - 1 + 4) % 4;
    }
    public void forward(int staminaUsed) throws InterruptedException {
    	//preliminary error checks
        if(playerEscaped) {
            System.out.println("error: tried to make a move, but already escaped");
            return;
        }
        if(staminaUsed > stamina) {
            System.out.println("error: tried to use " + staminaUsed + " stamina, but only " + stamina + " available.");
            return;
        }
        

        //count # boulders in direction and find the cell after all those
        int r = playerRow;
        int c = playerCol;
        int numBoulders = 0;
        boolean canPushBoulders;
        while(true) {
            //make sure to update first so we don't count square that player is on
        	r = newR(r, playerOrientation);
        	c = newC(c, playerOrientation);

            if(inBounds(r,c) && hasBoulder(obstacles[r][c])) numBoulders++;
            else {
            	//r,c is position just past the last boulder
            	canPushBoulders = inBounds(r,c) && !stopsBoulders(obstacles[r][c]);
                break;
            }
        }
        

        //perform the move
        stamina -= staminaUsed;
        
        int newRow = newR(playerRow, playerOrientation);
        int newCol = newC(playerCol, playerOrientation);
        
        if(!inBounds(newRow, newCol)) {
            System.out.println("successfully escaped maze!");
            playerEscaped = true;
        }
        else if(numBoulders > 0) {
            if(!canPushBoulders || staminaUsed < numBoulders * STAMINA_FOR_ONE_BOULDER) {
                System.out.println("tried to move forward but cannot");
                return;
            }
            
            //r and c is the position just past the last boulder
            if(obstacles[r][c] == '_') obstacles[r][c] = '|';
            else obstacles[r][c] = 'o';
            
            if(obstacles[newRow][newCol] == '|') obstacles[newRow][newCol] = '_';
            else obstacles[newRow][newCol] = '.';
            
            playerRow = newRow;
            playerCol = newCol;
        }
        else {
        	if(obstacles[newRow][newCol] == '#' || (obstacles[newRow][newCol] == 'G' && !allPressurePlatesBlocked())) {
        		System.out.println("tried to move forward but cannot");
        	}
        	else if(obstacles[newRow][newCol] == 'X') {
        		if(staminaUsed < STAMINA_FOR_BREAKABLE_WALL) {
        			System.out.println("tried to move forward but cannot");
        		}
        		else {
        			obstacles[newRow][newCol] = 'x';
        		}
            }
            else if(obstacles[newRow][newCol] == 'x') {
        		if(staminaUsed < STAMINA_FOR_BREAKABLE_WALL) {
        			System.out.println("tried to move forward but cannot");
        		}
        		else {
        			obstacles[newRow][newCol] = '.';
        		}
            }
            else if(obstacles[newRow][newCol] == '.' || obstacles[newRow][newCol] == '_' || (obstacles[newRow][newCol] == 'G' && allPressurePlatesBlocked())) {
                playerRow = newRow;
                playerCol = newCol;
            }
            else {
            	throw new RuntimeException();
            }
        }
    }
    public String getNextCell() {
        int newRow = newR(playerRow, playerOrientation);
        int newCol = newC(playerCol, playerOrientation);
        
        if(playerEscaped) return "wall";
        else if(!inBounds(newRow, newCol)) return "empty";
        else {
        	char c = obstacles[newRow][newCol];
        	if(c == '#' || c == 'X' || c == 'x' || (c == 'G' && !allPressurePlatesBlocked())) return "wall";
        	else if(hasBoulder(c)) return "boulder";
        	else return "empty";
        }
    }
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
    private int newR(int r, int orientation) {
        if(playerOrientation == 1) return r-1;
        if(playerOrientation == 3) return r+1;
        else return r;
    }
    private int newC(int c, int orientation) {
    	if(playerOrientation == 0) return c+1;
        if(playerOrientation == 2) return c-1;
        else return c;
    }
    private boolean hasBoulder(char c) {
    	return c == 'o' || c == '|';
    }
    private boolean stopsBoulders(char c) {
    	return c == '#' || c == 'X' || c == 'x' || c == 'G';
    }
    public boolean allPressurePlatesBlocked() {
    	for(char[] row : obstacles) {
    		for(char c : row) {
    			if(c == '_') return false;
    		}
    	}
    	return true;
    }

    public char[][] getObstacles() {
        return obstacles;
    }
    public int getPlayerRow() {
        return playerRow;
    }
    public int getPlayerCol() {
        return playerCol;
    }
    public int getPlayerOrientation() {
        return playerOrientation;
    }
    public boolean getPlayerEscaped() {
    	return playerEscaped;
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public int getStamina() {
        return stamina;
    }
}
