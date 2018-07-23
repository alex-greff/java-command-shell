package unitTests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import filesystem.File;

public class FileTest {
  @Test
  public void testConstructNoInitialContents() {
    File<String> f = new File<String>("myFileName", null);
    assertEquals("myFileName", f.getName());
    assertEquals("", f.read());
  }

  @Test
  public void testConstructWithInitialContents() {
    File<String> f = new File<String>("myFileName", "my file's contents", null);
    assertEquals("myFileName", f.getName());
    assertEquals("my file's contents", f.read());
  }

  @Test
  public void testWriteWithEmptyString() {
    File<String> f = new File<String>("myFileName", null);
    f.write("");
    assertEquals("", f.read());
  }

  @Test
  public void testWriteWithNormalString() {
    File<String> f = new File<String>("myFileName", null);
    f.write("some string");
    assertEquals("some string", f.read());
  }

  @Test
  public void testWriteWithComplexString() {
    File<String> f = new File<String>("myFileName", null);
    f.write("this is some line\nthis is another line\nlast line");
    assertEquals("this is some line\nthis is another line\nlast line",
        f.read());
  }

  @Test
  public void testReadWithNoContents() {
    File<String> f = new File<String>("myFileName", null);
    f.write("");
    assertEquals("", f.read());
  }

  @Test
  public void testReadWithContents() {
    File<String> f = new File<String>("myFileName", null);
    assertEquals("", f.read());
  }

  @Test
  public void testReadWithComplexContents() {
    File<String> f =
        new File<String>("myFileName", "some\ncomplex content\n\n\n", null);
    f.write("more complex\t\ncontent     ");
    assertEquals("some\ncomplex content\n\n\nmore complex\t\ncontent     ",
        f.read());
  }

  @Test
  public void testClearWithNoContents() {
    File<String> f = new File<String>("myFileName", null);
    f.clear();
    assertEquals("", f.read());
  }

  @Test
  public void testClearWithContents() {
    File<String> f =
        new File<String>("myFileName", "some initial content", null);
    f.write("\nsome more content");
    f.clear();
    assertEquals("", f.read());
  }
}
