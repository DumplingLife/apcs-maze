package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import core.Maze;
import student.Main;

public class Checkpoint1Tests {
	@Test public void test1() { TestHelper.test(0, new String[] {"L F F R F"}, 1,2,0); }
	@Test public void test2() { TestHelper.test(0, new String[] {"L R L R"}, 3,1,0); }
	@Test public void test3() { TestHelper.test(0, new String[] {"F"}, 3,1,0); }
	@Test public void test4() { TestHelper.test(1, new String[] {"L"}, 3,1,1); }
	@Test public void test5() { TestHelper.test(1, new String[] {"R"}, 3,1,3); }
	
	//full levels
	@Test public void test6() { TestHelper.testEscape(0, new String[] {"L F F R F F F F R F F R F F L F F"}); }
	@Test public void test7() { TestHelper.testEscape(1, new String[] {"L F F R F F F F R F F L F F F F L F F R F F F"}); }
	
	//intentional failed test to test testing
	//@Test public void testFail() { TestHelper.test(0, new String[] {"R"}, -1,null,null,null,null,null); }
}
