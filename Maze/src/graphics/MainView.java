package graphics;

import core.Maze;

import java.awt.CardLayout;
import javax.swing.*;

public class MainView {
    private JFrame frame;
    private JPanel cards;
    private GamePage gamePage;
    private Maze[] levels = new Maze[] {
        //L F F R F F F F R F F L F F F F L F F R F F F
        new Maze(new String[] {
            "############",
            "#.....#.....",
            "#.###.#.#.##",
            "#P#.......##",
            "############",
        }, 0),
        //
        new Maze(new String[] {
            "############",
            "#.o.........",
            "#.###.#.#.##",
            "#P#.......##",
            "############",
        }, 4 * Maze.STAMINA_FOR_ONE_BOULDER),
        //L F R F R F3_50 L F2 L F F_50 R F2
        new Maze(new String[] {
            "######",
            "#..#.#",
            "#Po.o.",
            "##.#.#",
            "##...#",
            "##.###",
            "######",
        }, 6 * Maze.STAMINA_FOR_ONE_BOULDER), //actually only needs 4 stamina
        new Maze(new String[] {
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
            (e) -> { changePage("Instructions Panel"); }
        );
        InstructionsPanel instructionsPanel = new InstructionsPanel((e) -> { changePage("Start Panel"); });
        LevelSelectPanel levelSelectPanel = new LevelSelectPanel(
            levels.length,
            (i) -> {
                gamePage = new GamePage(levels[i]);
                cards.add(gamePage.getPanel(), "Game Panel");
                changePage("Game Panel");
            },
            (e) -> { changePage("Start Panel"); }
        );
        cards.add(startPanel, "Start Panel");
        cards.add(instructionsPanel, "Instructions Panel");
        cards.add(levelSelectPanel, "Level Select Panel");

        frame.add(cards);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        changePage("Start Panel");
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
}