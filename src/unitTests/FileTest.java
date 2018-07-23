package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import filesystem.Directory;
import filesystem.File;

public class FileTest {
  @Test
  public void testConstructNoInitialContents() {
    File<String> f = new File<>("myFileName", null);
    assertEquals("myFileName", f.getName());
    assertEquals("", f.read());
  }

  @Test
  public void testConstructWithInitialContents() {
    File<String> f = new File<>("myFileName", "my file's contents", null);
    assertEquals("myFileName", f.getName());
    assertEquals("my file's contents", f.read());
  }

  @Test
  public void testWriteWithEmptyString() {
    File<String> f = new File<>("myFileName", null);
    f.write("");
    assertEquals("", f.read());
  }

  @Test
  public void testWriteWithNormalString() {
    File<String> f = new File<>("myFileName", null);
    f.write("some string");
    assertEquals("some string", f.read());
  }

  @Test
  public void testWriteWithComplexString() {
    File<String> f = new File<>("myFileName", null);
    f.write("this is some line\nthis is another line\nlast line");
    assertEquals("this is some line\nthis is another line\nlast line",
        f.read());
  }

  @Test
  public void testReadWithNoContents() {
    File<String> f = new File<>("myFileName", null);
    f.write("");
    assertEquals("", f.read());
  }

  @Test
  public void testReadWithContents() {
    File<String> f = new File<>("myFileName", null);
    assertEquals("", f.read());
  }

  @Test
  public void testReadWithComplexContents() {
    File<String> f =
        new File<>("myFileName", "some\ncomplex content\n\n\n", null);
    f.write("more complex\t\ncontent     ");
    assertEquals("some\ncomplex content\n\n\nmore complex\t\ncontent     ",
        f.read());
  }

  @Test
  public void testClearWithNoContents() {
    File<String> f = new File<>("myFileName", null);
    f.clear();
    assertEquals("", f.read());
  }

  @Test
  public void testClearWithContents() {
    File<String> f =
        new File<>("myFileName", "some initial content", null);
    f.write("\nsome more content");
    f.clear();
    assertEquals("", f.read());
  }

  @Test
  public void testCloneWithParent() {
    Directory parent = new Directory("myParent", null);
    File<String> file = new File<>("myName", "my contents", parent);

    File<String> new_file = (File<String>) file.copy();
    assertEquals("myName", new_file.getName());
    assertEquals("my contents", new_file.read());
    assertEquals("myParent", new_file.getParent().getName());
  }

  @Test
  public void testCloneNoParent() {
    File<String> file = new File<>("myName", "my contents", null);

    File<String> new_file = (File<String>) file.copy();
    assertEquals("myName", new_file.getName());
    assertEquals("my contents", new_file.read());
    assertNull(new_file.getParent());
  }
}
