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

import static org.junit.Assert.assertArrayEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import filesystem.DirectoryStack;

public class DirectoryStackTest {
  DirectoryStack ds;

  @Before
  public void setup() {
    ds = DirectoryStack.getInstance();
  }

  @After
  public void tearDown() throws NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    Field field = ds.getClass().getDeclaredField("ourInstance");
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  public void testReadAllItemsFromNonEmptyDirStack() {
    String[] items = {"/dir1", "/some/dir1/dir2", "/"};
    ds.addAll(new ArrayList<>(Arrays.asList(items)));
    assertArrayEquals(items, ds.toArray());
  }

  @Test
  public void testReadAllItemsFromEmptyDirStack() {
    String[] items = {};
    ds.addAll(new ArrayList<>(Arrays.asList(items)));
    assertArrayEquals(items, ds.toArray());
  }
}
