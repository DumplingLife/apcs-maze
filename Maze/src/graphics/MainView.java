package graphics;

import core.*;

import java.awt.CardLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class MainView {
    private JFrame frame;
    private JPanel cards;
    private GamePage gamePage;
    private int level;
    private MazeTemplate[] levelTemplates;

    public MainView() {
    	//read level.txt, then put it into levelTemplates
    	Scanner scan = null;
    	try {
			scan = new Scanner(new File("level.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
    	int customLevelStamina = Integer.parseInt(scan.nextLine());
    	ArrayList<String> customLevelGridList = new ArrayList<>();
    	while(scan.hasNext()) {
    		customLevelGridList.add(scan.nextLine());
    	}
    	String[] customLevelGrid = customLevelGridList.toArray(new String[0]);
    	
    	
    	levelTemplates = new MazeTemplate[] {
	        //L F F R F F F F R F F L F F F F L F F R F F F
	        new MazeTemplate(new String[] {
	            "############",
	            "#.....#.....",
	            "#.###.#.#.##",
	            "#P#.......##",
	            "############",
	        }, 0),
	        //
	        new MazeTemplate(new String[] {
	            "############",
	            "#.o.........",
	            "#.###.#.#.##",
	            "#P#.......##",
	            "############",
	        }, 4 * Maze.STAMINA_FOR_ONE_BOULDER),
	        //L F R F R F3_50 L F2 L F F_50 R F2
	        new MazeTemplate(new String[] {
	            "######",
	            "#..#.#",
	            "#Po#o.",
	            "##.#.#",
	            "##...#",
	            "##.###",
	            "######",
	        }, 6 * Maze.STAMINA_FOR_ONE_BOULDER), //actually only needs 4 stamina
	        new MazeTemplate(new String[] {
	            "#########",
	            "#..o....#",
	            "#.#o#####",
	            "#.#o#####",
	            "#P#o....#",
	            "#.#o###.#",
	            "#.#o###.#",
	            "#.#o###.#",
	            "#...###.#",
	            "#######.#",
	        }, 29 * Maze.STAMINA_FOR_ONE_BOULDER), //best way is 29 stamina, I think
	        //for screenshots
	        /*
	        new MazeTemplate(new String[] {
	            "#######",
	            "#.Poo.#",
	            "#######",
	        }, 4 * Maze.STAMINA_FOR_ONE_BOULDER), //actually only needs 4 stamina
	        */
	        
	        new MazeTemplate(customLevelGrid, customLevelStamina),
	    };
    	
    	
        frame = new JFrame("Start Page");
        cards = new JPanel(new CardLayout());
        StartPanel startPanel = new StartPanel(
            (e) -> { changePage("Level Select Panel"); },
            (e) -> { new InstructionsFrame(); }
        );
        LevelSelectPanel levelSelectPanel = new LevelSelectPanel(
        	levelTemplates.length,
            (i) -> {
            	level = i;
                gamePage = new GamePage(new Maze(levelTemplates[level]));
                cards.add(gamePage, "Game Panel");
                changePage("Game Panel");
                gamePage.requestFocusInWindow();
            },
            (e) -> { changePage("Start Panel"); }
        );
        cards.add(startPanel, "Start Panel");
        cards.add(levelSelectPanel, "Level Select Panel");

        frame.add(cards);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        changePage("Start Panel");
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    private void changePage(String newPage) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, newPage);
        frame.pack();
    }

    public GamePage getGamePage() {
        return gamePage;
    }
    public void resetGamePage() {
    	gamePage.maze = new Maze(levelTemplates[level]);
    	gamePage.paint();
    }
    public void quit() {
    	frame.dispose();
    }
}