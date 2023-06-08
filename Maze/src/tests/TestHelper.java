package tests;

import org.junit.jupiter.api.Assertions;

import core.Maze;
import student.Main;

public class TestHelper {
	//pass in the level, lines of input, and 6 expected values
	//pass in null for a value if you don't care about checking the value (to keep tests more simple)
	private static void test(int level, String[] lines, Integer expRow, Integer expCol, Integer expOrientation, Integer expStamina, Boolean expEscaped, Integer expSpeed, Integer expScheme) {
		//set (or reset) level for every test
		Main.mainView.setLevel(level);
		Main.mainView.setGamePage();
		
		//real speed should be 1ms for faster testing, but keep track of expected speed
		int speed = -1;
		for(String line : lines) {
			Main.mainView.getGamePage().speed = 1;
			Main.processLine(line);
			//we will know if speed changed if it isn't 1 (it can't be 1 under normal instructions)
			if(Main.mainView.getGamePage().speed != 1) speed = Main.mainView.getGamePage().speed;
		}
		Maze maze = Main.mainView.getGamePage().getMaze();
		if(expRow != null) Assertions.assertEquals(expRow, maze.getPlayerRow());
		if(expCol != null) Assertions.assertEquals(expCol, maze.getPlayerCol());
		if(expOrientation != null) Assertions.assertEquals(expOrientation, maze.getPlayerOrientation());
		if(expStamina != null) Assertions.assertEquals(expStamina, maze.getStamina());
		if(expEscaped != null) Assertions.assertEquals(expEscaped, maze.getPlayerEscaped());
		if(expSpeed != null) Assertions.assertEquals(expSpeed, speed);
		if(expScheme != null) Assertions.assertEquals(expScheme, Main.mainView.getGamePage().getScheme());
		
	}
	
	public static void test(int level, String[] lines, int expRow, int expCol, int expOrientation) {
		test(level, lines, expRow, expCol, expOrientation, null, null, null, null);
	}
	public static void test(int level, String[] lines, int expRow, int expCol, int expOrientation, int expStamina) {
		test(level, lines, expRow, expCol, expOrientation, expStamina, null, null, null);
	}
	public static void testScheme(int level, String[] lines, int expScheme) {
		test(level, lines, null, null, null, null, null, null, expScheme);
	}
	public static void testSpeed(int level, String[] lines, int expSpeed) {
		test(level, lines, null, null, null, null, null, expSpeed, null);
	}
	public static void testEscape(int level, String[] lines) {
		test(level, lines, null, null, null, null, true, null, null);
	}
}
