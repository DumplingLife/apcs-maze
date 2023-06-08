package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import core.Maze;
import student.Main;

public class Checkpoint3Tests {
	@Test public void test1() { TestHelper.test(3, new String[] {"L F2_100"}, 1,1,1,0); }
	@Test public void test2() { TestHelper.test(3, new String[] {"L F1_100 F1 L L F_100 F L L F_0"}, 2,1,1,0); }
	
	
	//full levels
	@Test public void test3() {
		TestHelper.testEscape(3, new String[] {
				"L F2 R F4_50 R F2 L F4 L F2 R F3",
			});
	}
	@Test public void test4() {
		TestHelper.testEscape(4, new String[] {
				"L F R F R F3_50 L F_100 F_100 F2 L F F_50 R F2"
			});
	}
	@Test public void test5() {
		TestHelper.testEscape(5, new String[] {
				"L F F_50",
				"L L F L",
				"F3",
				"F2_100",
				"F",
				"L F_50 L",
				"L F R F4 R",
				"F R F_150 L F2"
			});
	}
}
