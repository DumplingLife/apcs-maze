package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import core.Maze;
import student.Main;

public class CheckpointFinalTests {
	@Test public void test1() { TestHelper.test(0, new String[] {"L F!"}, 1,1,1); }
	@Test public void test2() { TestHelper.test(0, new String[] {"L F! R F! R F!"}, 3,5,3); }
	@Test
	public void test3() {
		TestHelper.test(0, new String[] {
				"M1 = R F!",
				"M2 = L F!",
				"M2 M1",
				"M1",
			}, 3,5,3);
	}
	@Test public void test4() { TestHelper.testScheme(0, new String[] {"F","scheme 1","F"}, 1); }

	
	//full levels
	@Test public void test7() { TestHelper.testEscape(0, new String[] {"L F! R F! R F! R F! L F!"}); }
	@Test
	public void test8() {
		TestHelper.testEscape(2, new String[] {
				"M1 = F2 L F2 L",
				"M2 = F2 L F2 R",
				"M3 = F2 R F2 L",
				"M4 = F2 R F2 R",
				"M3 F2 R F2 M1 F2 R F2 M4 M2 M1 F2 M3 M3 F4"
			});
	}
	@Test
	public void test9() {
		TestHelper.testEscape(6, new String[] {
				//mix some single and multi-instruction lines, especially with macros
				"M1 = R R F! R F! R F! R F",
				"M2 = L L F! L F! L F! L F!",
				"L F! R F F_50",
				"M2",
				"F_300",
				"M1",
				"F_100",
				"M2 F_250",
				"M1 F_150 M2 F_200 M1 F_200",
				"M2",
				"F_150",
				"R F! R F!",
			});
	}
}
