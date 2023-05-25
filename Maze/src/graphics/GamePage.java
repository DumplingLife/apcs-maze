package graphics;

import core.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GamePage extends JPanel {
    private ImageComponent[][] imageComponents;
    private JProgressBar staminaBar;
    private JLabel staminaLabel;
    private int rows, cols;
    private static Image playerRight, playerUp, playerLeft, playerDown, blank, wall, boulder;
    public Maze maze;
    public int speed = 500;
    private final int maxWidth = 1200;
    private final int maxHeight = 700;
    public GamePage(Maze maze) {
        this.maze = maze;
        rows = maze.getRows();
        cols = maze.getCols();
        int cellDimensions = Math.min(maxWidth/cols, maxHeight/rows);

        try {
            playerRight = ImageIO.read(new File("playerRight.png")).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
            playerUp = ImageIO.read(new File("playerUp.png")).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
            playerLeft = ImageIO.read(new File("playerLeft.png")).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
            playerDown = ImageIO.read(new File("playerDown.png")).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
            blank = ImageIO.read(new File("blank.png")).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
            wall = ImageIO.read(new File("wall.png")).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
            boulder = ImageIO.read(new File("boulder.png")).getScaledInstance(cellDimensions,cellDimensions,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        
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
    public String forward(int staminaUsed) throws InterruptedException {
        String nextCell = maze.forward(staminaUsed);
        paint();
        Thread.sleep(speed);
        return nextCell;
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
            }
        }
        staminaBar.setValue(maze.getStamina());
        staminaLabel.setText("Stamina: " + maze.getStamina());
    }
}
