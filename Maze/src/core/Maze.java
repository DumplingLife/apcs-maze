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

    public Maze(String[] mazeStr, int startingStamina) {
        rows = mazeStr.length;
        cols = mazeStr[0].length();
        playerRow = -1;
        playerCol = -1;
        obstacles = new char[rows][cols];
        for(int row=0; row<rows; row++) {
            for(int col=0; col<cols; col++) {
                char ch = mazeStr[row].charAt(col);
                if(ch == 'P') {
                    obstacles[row][col] = '.';
                    playerRow = row;
                    playerCol = col;
                }
                else obstacles[row][col] = ch;
            }
        }
        this.stamina = startingStamina;
        this.playerOrientation = 0;
    }
    public void left() {
        playerOrientation = (playerOrientation + 1) % 4;
    }
    public void right() throws InterruptedException {
        playerOrientation = (playerOrientation - 1 + 4) % 4;
    }
    public void forward(int staminaUsed) throws InterruptedException {
        if(playerEscaped) {
            System.out.println("uh oh: tried to make a move, but already escaped");
            return;
        }

        //count # boulders in direction
        int r = playerRow;
        int c = playerCol;
        int numBoulders = 0;
        boolean endOnWall = false;
        while(true) {
            //make sure to update first so we don't count square that player is on
            if(playerOrientation == 0) c++;
            if(playerOrientation == 1) r--;
            if(playerOrientation == 2) c--;
            if(playerOrientation == 3) r++;

            if(inBounds(r,c) && obstacles[r][c] == 'o') numBoulders++;
            else {
                if((!inBounds(r,c) && numBoulders > 0) || (inBounds(r,c) && obstacles[r][c] == '#')) endOnWall = true;
                break;
            }
        }

        if(staminaUsed > stamina) {
            System.out.println("uh oh: tried to use " + staminaUsed + " stamina, but only " + stamina + " available.");
            return;
        }
        stamina -= staminaUsed;

        if(endOnWall || staminaUsed < numBoulders * STAMINA_FOR_ONE_BOULDER) {
            System.out.println("uh oh: tried to move forward but cannot");
            return;
        }


        int newPlayerRow = playerRow;
        int newPlayerCol = playerCol;
        if(playerOrientation == 0) newPlayerCol = playerCol + 1;
        if(playerOrientation == 1) newPlayerRow = playerRow - 1;
        if(playerOrientation == 2) newPlayerCol = playerCol - 1;
        if(playerOrientation == 3) newPlayerRow = playerRow + 1;

        if(!inBounds(newPlayerRow, newPlayerCol)) {
            System.out.println("successfully escaped maze!");
            playerEscaped = true;
            return;
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
    }
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
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
    public boolean getPlayerEscaped() { return playerEscaped; }
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
