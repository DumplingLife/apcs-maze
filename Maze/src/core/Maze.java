package core;

public class Maze {
    public static final int STAMINA_FOR_ONE_BOULDER = 50;
    private int playerRow, playerCol, playerOrientation;
    private boolean playerEscaped = false;
    private int rows, cols;
    private int stamina;
    //# is wall, . is empty, o is movable
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
    public String forward(int staminaUsed) throws InterruptedException {
        if(playerEscaped) {
            System.out.println("error: tried to make a move, but already escaped");
            return "error";
        }

        //count # boulders in direction
        int r = playerRow;
        int c = playerCol;
        int numBoulders = 0;
        boolean endOnWall = false;
        while(true) {
            //make sure to update first so we don't count square that player is on
        	r = newR(r, playerOrientation);
        	c = newC(c, playerOrientation);

            if(inBounds(r,c) && obstacles[r][c] == 'o') numBoulders++;
            else {
                if((!inBounds(r,c) && numBoulders > 0) || (inBounds(r,c) && obstacles[r][c] == '#')) endOnWall = true;
                break;
            }
        }

        if(staminaUsed > stamina) {
            System.out.println("error: tried to use " + staminaUsed + " stamina, but only " + stamina + " available.");
            return "error";
        }
        stamina -= staminaUsed;

        if(endOnWall || staminaUsed < numBoulders * STAMINA_FOR_ONE_BOULDER) {
            System.out.println("error: tried to move forward but cannot");
            return "error";
        }


        int newPlayerRow = newR(playerRow, playerOrientation);
        int newPlayerCol = newC(playerCol, playerOrientation);

        if(!inBounds(newPlayerRow, newPlayerCol)) {
            System.out.println("successfully escaped maze!");
            playerEscaped = true;
            return "error";
        }

        if(numBoulders > 0) {
            //r and c is now the position just past the last boulder
            //it has a boulder because we pushed the boulders
            obstacles[r][c] = 'o';
            //this was previously a boulder, now no obstacle
            obstacles[newPlayerRow][newPlayerCol] = '.';
        }

        playerRow = newPlayerRow;
        playerCol = newPlayerCol;
        
        int nextCellR = newR(playerRow, playerOrientation);
        int nextCellC = newC(playerCol, playerOrientation);
        if(!inBounds(nextCellR, nextCellC) || obstacles[nextCellR][nextCellC] == '.') return "empty";
        else if(obstacles[nextCellR][nextCellC] == '#') return "wall";
        else if(obstacles[nextCellR][nextCellC] == 'o') return "boulder";
        else return "oops";
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
