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
	        new MazeTemplate(new String[] {
	            "#######",
	            "#.....#",
	            "#.###.#",
	            "#P#...#",
	            "###.###",
	        }, 0),
	        //L F F R F F F F R F F L F F F F L F F R F F F
	        new MazeTemplate(new String[] {
	            "############",
	            "#.....#.....",
	            "#.###.#.#.##",
	            "#P#.......##",
	            "############",
	        }, 0),
	        //F2 R F2 L F2 R F4 L F2 L F2 R F4 R F2 R F2 L F2 R F2 L F2 L F4 R F2 L F2 R F2 L F4
	        new MazeTemplate(new String[] {
		        "#################",
		        "#P......#...#...#",
		        "###.###.#.#####.#",
		        "#.#...#...#...#.#",
		        "#.#.#.#######.#.#",
		        "#.#.#.#.......#.#",
		        "#.#.#.#.###.###.#",
		        "#.#.#...#.......#",
		        "#.###.###.#######",
		        "#.#...#...#.....#",
		        "#.#.###.#######.#",
		        "#...#.......#...#",
		        "#####.#####.#.#.#",
		        "#.......#.....#.#",
		        "#.#######.###.###",
		        "#.#.........#....",
		        "#################",
	        }, 0),
	        new MazeTemplate(new String[] {
	            "############",
	            "#.o.........",
	            "#.###.#.#.##",
	            "#P#.......##",
	            "############",
	        }, 4 * Maze.STAMINA_FOR_ONE_BOULDER),
	        //L F R F R F3_50 L F2_100 F2 L F F_50 R F2
	        new MazeTemplate(new String[] {
	            "######",
	            "#..#.#",
	            "#Po#o.",
	            "##.#.#",
	            "##.X.#",
	            "##.###",
	            "######",
	        }, 10 * Maze.STAMINA_FOR_ONE_BOULDER), // only needs 8 stamina
	        //L F F_50 L L F L F! L F_50 L L F R F! R F R F_150 L F!
	        new MazeTemplate(new String[] {
	            "#..##.#",
	            "#ooooo#",
	            "#....X#",
	            "#P....#",
	            "#######",
	        }, 20 * Maze.STAMINA_FOR_ONE_BOULDER), //only needs 9 stamina
	        /*
			M1 = R R F! R F! R F! R F
			M2 = L L F! L F! L F! L F!
			L F! R F F_50
			M2
			F_300
			M1
			F_100
			M2
			F_250
			M1
			F_150
			M2
			F_200
			M1
			F_200
			M2
			F_150
			R F! R F!
	         */
	        new MazeTemplate(new String[] {
	            "#########",
	            "#..o..__#",
	            "#.#o#####",
	            "#.#o#####",
	            "#P#o....#",
	            "#.#o###.#",
	            "#.#o###.#",
	            "#.#o###G#",
	            "#...###.#",
	            "#######.#",
	        }, 28 * Maze.STAMINA_FOR_ONE_BOULDER),
	        //R F_50 L F_100 R F L F L F_50
	        new MazeTemplate(new String[] {
	            "#######",
	            "#P.._.#",
	            "#ooo_.G",
	            "#_....#",
	            "#.#####",
	        }, 10 * Maze.STAMINA_FOR_ONE_BOULDER),
	        
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
                setGamePage();
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
    public void setLevel(int level) {
    	this.level = level;
    }
    //sets game page to the stored level, sets/resets graphics accordingly
    public void setGamePage() {
    	gamePage = new GamePage(new Maze(levelTemplates[level]));
        cards.add(gamePage, "Game Panel");
        changePage("Game Panel");
        gamePage.requestFocusInWindow();
    }
    public void quit() {
    	frame.dispose();
    }
}