package graphics;

import core.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GamePage {
    private JPanel panel;
    private ImageComponent[][] imageComponents;
    private JProgressBar staminaBar;
    private JLabel staminaLabel;
    private int rows, cols;
    private static Image playerRight, playerUp, playerLeft, playerDown, blank, wall, boulder;
    private Maze maze;
    public GamePage(Maze maze) {
        final int maxWidth = 1200;
        final int maxHeight = 700;
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

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

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
        panel.add(grid, gbc);

        staminaBar = new JProgressBar();
        staminaBar.setMinimum(0);
        staminaBar.setMaximum(maze.getStamina());
        gbc.gridy = 1;
        panel.add(staminaBar, gbc);

        staminaLabel = new JLabel();
        gbc.gridy = 2;
        panel.add(staminaLabel, gbc);

        paint();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void left() throws InterruptedException {
        maze.left();
        paint();
        Thread.sleep(500);
    }
    public void right() throws InterruptedException {
        maze.right();
        paint();
        Thread.sleep(500);
    }
    public void forward(int staminaUsed) throws InterruptedException {
        maze.forward(staminaUsed);
        paint();
        Thread.sleep(500);
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
        staminaLabel.setText(maze.getStamina() + "");
    }
}
