package unitTests;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import io.Console;
import io.QueryConsole;

public class QueryConsoleTest {
  QueryConsole<String> console;
  OutputStream os;

  @Before
  public void setup() {
    console = new QueryConsole<>();

    os = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(os);
    System.setOut(ps);
  }


  @Test
  public void testWriteWithEmptyString() {
    console.write("");

    assertEquals("", os.toString());
  }

  @Test
  public void testWriteWithNonEmptyString() {
    console.write("Some output");

    assertEquals("Some output", os.toString());
  }

  @Test
  public void testWritelnWithEmptyString() {
    console.writeln("");

    assertEquals("" + System.getProperty("line.separator"), os.toString());
  }

  @Test
  public void testWritelnWithNonEmptyString() {
    console.writeln("Some output");

    assertEquals("Some output" + System.getProperty("line.separator"),
        os.toString());
  }

  @Test
  public void tesReadWithEmptyInput() {
    InputStream in = new ByteArrayInputStream("\0".getBytes());
    System.setIn(in);

    assertEquals("\0", console.read());
  }

  @Test
  public void tesReadWithNonEmptyInput() {
    InputStream in = new ByteArrayInputStream("Some input".getBytes());
    System.setIn(in);

    assertEquals("Some input", console.read());
  }
}
