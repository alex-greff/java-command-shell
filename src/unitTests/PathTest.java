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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import org.junit.Test;
import filesystem.MalformedPathException;
import filesystem.Path;

public class PathTest {
  @Test
  public void testInitializePathWithValidString()
      throws MalformedPathException {
    Path p = new Path("/some/dir/path");
    String[] segements = {"/", "some", "dir", "path"};

    for (String s : segements) {
      assertEquals(s, p.removeFirst());
    }
  }

  @Test
  public void testInitializePathWithValidConvolutedPath()
      throws MalformedPathException {
    Path p = new Path("../.././some/../dir/././path");
    String[] segements =
        {"..", "..", ".", "some", "..", "dir", ".", ".", "path"};

    for (String s : segements) {
      assertEquals(s, p.removeFirst());
    }
  }

  @Test
  public void testInitializePathWithAnotherPathObject()
      throws MalformedPathException {
    Path p_other = new Path("/some/dir/path");
    Path p = new Path(p_other);
    String[] segements = {"/", "some", "dir", "path"};

    for (String s : segements) {
      assertEquals(s, p.removeFirst());
    }
  }

  @Test(expected = MalformedPathException.class)
  public void testInitializePathWithSimpleInvalidPath()
      throws MalformedPathException {
    Path p = new Path("/some/invalid//dir/path");
  }

  @Test(expected = MalformedPathException.class)
  public void testInitializePathWithComplexInvlaidPath()
      throws MalformedPathException {
    Path p = new Path("../../...../some/dir/path");
  }

  @Test
  public void testGetNumberOfTokens() throws MalformedPathException {
    Path p = new Path("some/path/../");
    assertEquals(3, p.getNumberOfTokens());
  }

  @Test
  public void testRemoveLastWithNonEmptyPath() throws MalformedPathException {
    Path p = new Path("path");
    assertEquals("path", p.removeLast());
  }

  @Test
  public void testRemoveLastWithEmptyPath() throws MalformedPathException {
    Path p = new Path("path");
    p.removeLast();
    assertNull(p.removeLast());
  }

  @Test
  public void testRemoveFirstWithNonEmptyPath() throws MalformedPathException {
    Path p = new Path("path");
    assertEquals("path", p.removeFirst());
  }

  @Test
  public void testRemoveFistWithEmptyPath() throws MalformedPathException {
    Path p = new Path("path");
    p.removeLast();
    assertNull(p.removeFirst());
  }

  @Test
  public void testRemoveAllTokensWithMixOfRemoveFirstAndRemoveLast()
      throws MalformedPathException {
    Path p = new Path("some/dir/../path");
    assertEquals("some", p.removeFirst());
    assertEquals("path", p.removeLast());
    assertEquals("..", p.removeLast());
    assertEquals("dir", p.removeFirst());
    assertNull(p.removeFirst());
    assertNull(p.removeLast());
  }

  @Test
  public void testGetWithNonEmptyPath() throws MalformedPathException {
    Path p = new Path("some/dir/../path");
    assertEquals("..", p.get(2));
  }

  @Test
  public void testIteratorWithNonEmptyPath() throws MalformedPathException {
    Path p = new Path("some/dir/../path");

    String[] segments = {"some", "dir", "..", "path"};

    Iterator<String> iterator = p.iterator();

    int i = 0;
    while (iterator.hasNext()) {
      assertEquals(segments[i], iterator.next());
      i++;
    }
  }

  @Test
  public void testIsEmptyWithEmptyPath() throws MalformedPathException {
    Path p = new Path("some/dir/../path");
    assertFalse(p.isEmpty());
  }

  @Test
  public void testIsEmptyWithNonEmptyPath() throws MalformedPathException {
    Path p = new Path("path");
    p.removeFirst();
    assertTrue(p.isEmpty());
  }
}
