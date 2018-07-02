// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: ursualex
// UT Student #: 1004357199
// Author: Alexander Ursu
//
// Student2:
// UTORID user_name: greffal1
// UT Student #: 1004254497
// Author: Alexander Greff
//
// Student3:
// UTORID user_name: sankarch
// UT Student #: 1004174895
// Author: Chedy Sankar
//
// Student4:
// UTORID user_name: kamins42
// UT Student #: 1004431992
// Author: Anton Kaminsky
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestingConsoleTest {

  @Test
  public void testConsoleWriteln() {
    TestingConsole tc = new TestingConsole();
    tc.writeln("some line 1");
    tc.writeln("some line 2");

    assertEquals(new String[]{"some line 1", "some line 2"},
        tc.getAllWrites());
  }

  @Test
  public void testConsoleWrite() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");

    assertEquals(new String[]{"ab"}, tc.getAllWrites());
  }

  @Test
  public void testConsoleGetAllWrites() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");
    tc.writeln("c");

    assertEquals(new String[]{"ab", "c"}, tc.getAllWrites());
  }

  @Test
  public void testConsoleGetAllWritesNoWrites() {
    TestingConsole tc = new TestingConsole();

    assertEquals(new String[0], tc.getAllWrites());
  }

  @Test
  public void testConsoleGetLastWrite() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");
    tc.writeln("c");

    assertEquals("c", tc.getLastWrite());
  }

  @Test
  public void testConsoleGetLastWriteNoWrites() {
    TestingConsole tc = new TestingConsole();

    assertNull(tc.getLastWrite());
  }

  @Test
  public void testConsoleGetAllWritesAsString() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");
    tc.writeln("c");

    assertEquals("ab\nc\n", tc.getAllWritesAsString());
  }

  @Test
  public void testConsoleGetAllWritesAsStringNoWrites() {
    TestingConsole tc = new TestingConsole();

    assertEquals("", tc.getAllWritesAsString());
  }
}
