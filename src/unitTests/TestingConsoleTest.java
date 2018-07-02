package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class TestingConsoleTest {
  @SuppressWarnings("deprecation")
  @Test
  public void testConsoleWriteln() {
    TestingConsole tc = new TestingConsole();
    tc.writeln("some line 1");
    tc.writeln("some line 2");

    assertEquals(new String[] {"some line 1", "some line 2"},
        tc.getAllWrites());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testConsoleWrite() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");

    assertEquals(new String[] {"ab"}, tc.getAllWrites());
  }
  
  @SuppressWarnings("deprecation")
  @Test
  public void testConsoleGetAllWrites() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");
    tc.writeln("c");

    assertEquals(new String[] {"ab", "c"}, tc.getAllWrites());
  }
  
  @SuppressWarnings("deprecation")
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
