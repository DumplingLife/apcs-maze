package graphics;

import core.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GamePage extends JPanel {
    private ImageComponent[][] imageComponents;
    private JProgressBar staminaBar;
    private JLabel staminaLabel;
    private int rows, cols;
    private static Image playerRight, playerUp, playerLeft, playerDown, blank, wall, boulder, breakableWall;
    private Maze maze;
    public int speed = 500;
    private final int maxWidth = 1200;
    private final int maxHeight = 700;
    private int cellDimensions;
    public GamePage(Maze maze) {
        this.maze = maze;
        rows = maze.getRows();
        cols = maze.getCols();
        
        cellDimensions = Math.min(maxWidth/cols, maxHeight/rows);
        setScheme(0);
        
        this.setLayout(new GridBagLayout());

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(rows, cols));
        imageComponents = new ImageComponent[rows][cols];
        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                ImageComponent p = new ImageComponent(blank);
                grid.add(p);
                imageComponents[r][c] = p;
            }
        }
        grid.setPreferredSize(new Dimension(cols * cellDimensions, rows * cellDimensions));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(grid, gbc);

        staminaBar = new JProgressBar();
        staminaBar.setMinimum(0);
        staminaBar.setMaximum(maze.getStamina());
        gbc.insets = new Insets(8,0,0,0);
        gbc.gridy = 1;
        this.add(staminaBar, gbc);

        staminaLabel = new JLabel();
        gbc.gridy = 2;
        gbc.insets = new Insets(0,0,8,0);
        this.add(staminaLabel, gbc);
        
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

            	try {
	                if(keyCode == KeyEvent.VK_UP) {
						System.out.println("F");
	                	maze.forward(0);
	                    paint();
	                }
	                else if(keyCode == KeyEvent.VK_LEFT) {
	                	System.out.println("L");
	                	maze.left();
	                    paint();
	                }
	                else if(keyCode == KeyEvent.VK_RIGHT) {
	                	System.out.println("R");
	                	maze.right();
	                    paint();
	                }
	                else if(keyCode == KeyEvent.VK_SPACE) {
	                	System.out.println("F_50");
	                	maze.forward(50);
	                    paint();
	                }
	            }
	            catch (InterruptedException e1) {
					e1.printStackTrace();
				}
            }
        });
        
        paint();
    }

    public void left() throws InterruptedException {
        maze.left();
        paint();
        Thread.sleep(speed);
    }
    public void right() throws InterruptedException {
        maze.right();
        paint();
        Thread.sleep(speed);
    }
    public void forward(int staminaUsed) throws InterruptedException {
        maze.forward(staminaUsed);
        paint();
        Thread.sleep(speed);
    }
    public String getNextCell() {
    	return maze.getNextCell();
    }
    public void setScheme(int scheme) {
    	String folder = "scheme" + scheme;
    	try {
            playerRight = loadImage(Paths.get(folder, "playerRight.png").toFile());
            playerUp = loadImage(Paths.get(folder, "playerUp.png").toFile());
            playerLeft = loadImage(Paths.get(folder, "playerLeft.png").toFile());
            playerDown = loadImage(Paths.get(folder, "playerDown.png").toFile());
            blank = loadImage(Paths.get(folder, "blank.png").toFile());
            wall = loadImage(Paths.get(folder, "wall.png").toFile());
            boulder = loadImage(Paths.get(folder, "boulder.png").toFile());
            breakableWall = loadImage(Paths.get(folder, "breakableWall.png").toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Image loadImage(File file) throws IOException {
    	return ImageIO.read(file).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
    }
    public void paint() {
        char[][] obstacles = maze.getObstacles();
        int playerRow = maze.getPlayerRow();
        int playerCol = maze.getPlayerCol();
        int playerOrientation = maze.getPlayerOrientation();
        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                if(!maze.getPlayerEscaped() && r == playerRow && c == playerCol) {
                    if(playerOrientation == 0) imageComponents[r][c].setImage(playerRight);
                    if(playerOrientation == 1) imageComponents[r][c].setImage(playerUp);
                    if(playerOrientation == 2) imageComponents[r][c].setImage(playerLeft);
                    if(playerOrientation == 3) imageComponents[r][c].setImage(playerDown);
                }
                else if(obstacles[r][c] == '.') imageComponents[r][c].setImage(blank);
                else if(obstacles[r][c] == '#') imageComponents[r][c].setImage(wall);
                else if(obstacles[r][c] == 'o') imageComponents[r][c].setImage(boulder);
                else if(obstacles[r][c] == 'X' || obstacles[r][c] == 'x') imageComponents[r][c].setImage(breakableWall);
            }
        }
        staminaBar.setValue(maze.getStamina());
        staminaLabel.setText("Stamina: " + maze.getStamina());
    }
}
