package graphics;

import core.*;

import java.awt.CardLayout;
import javax.swing.*;

public class MainView {
    private JFrame frame;
    private JPanel cards;
    private GamePage gamePage;
    private int level;
    private MazeTemplate[] levelTemplates = new MazeTemplate[] {
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
    };

    public MainView() {
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
}