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

import io.ErrorConsole;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

public class ErrorConsoleTest {

  ErrorConsole<String> console;
  OutputStream os;

  @Before
  public void setup() {
    console = new ErrorConsole<>();

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
