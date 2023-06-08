package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import core.Maze;
import student.Main;

public class Checkpoint2Tests {
	@Test public void test1() { TestHelper.test(0, new String[] {"L F F R F"}, 1,2,0); }
	@Test public void test2() { TestHelper.test(0, new String[] {"L","F","F","R","F"}, 1,2,0); }
	@Test public void test3() { TestHelper.test(0, new String[] {"F","F10"}, 3,1,0); }
	@Test public void test4() { TestHelper.testSpeed(0, new String[] {"set speed 1"}, 1000); }
	@Test public void test5() { TestHelper.testSpeed(0, new String[] {"set speed 10"}, 100); }
	@Test public void test6() { TestHelper.testSpeed(0, new String[] {"F","set speed 6","F"}, 500); }
	
	//full levels
	@Test public void test7() {
		TestHelper.testEscape(1, new String[] {
				"L F2 R F4",
				"R",
				"F F",
				"L F4 L F2 R F3",
			});
	}
	@Test public void test8() {
		TestHelper.testEscape(2, new String[] {
				"F2 R F2 L F2 R F4 L F2 L F2 R F4 R F2 R F2 L F2 R F2 L F2 L F4 R F2 L F2 R F2 L F4"
			});
	}
}
