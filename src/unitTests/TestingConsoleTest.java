package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class TestingConsoleTest {
  @Test
  public void test_console_writeln() {
    TestingConsole tc = new TestingConsole();
    tc.writeln("some line 1");
    tc.writeln("some line 2");

    assertEquals(new String[] {"some line 1", "some line 2"},
        tc.getAllWrites());
  }

  @Test
  public void test_console_write() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");

    assertEquals(new String[] {"ab"}, tc.getAllWrites());
  }
  
  @Test
  public void test_console_getAllWrites() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");
    tc.writeln("c");

    assertEquals(new String[] {"ab", "c"}, tc.getAllWrites());
  }
  
  @Test
  public void test_console_getAllWrites_no_writes() {
    TestingConsole tc = new TestingConsole();

    assertEquals(new String[0], tc.getAllWrites());
  }
  
  @Test
  public void test_console_getLastWrite() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");
    tc.writeln("c");

    assertEquals("c", tc.getLastWrite());
  }
  
  @Test
  public void test_console_getLastWrite_no_writes() {
    TestingConsole tc = new TestingConsole();

    assertNull(tc.getLastWrite());
  }
  
  @Test
  public void test_console_getAllWritesAsString() {
    TestingConsole tc = new TestingConsole();
    tc.write("a");
    tc.write("b");
    tc.writeln("c");

    assertEquals("ab\nc\n", tc.getAllWritesAsString());
  }
  
  @Test
  public void test_console_getAllWritesAsString_no_writes() {
    TestingConsole tc = new TestingConsole();

    assertEquals("", tc.getAllWritesAsString());
  }
}
