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
    ds.addAll(new ArrayList<String>(Arrays.asList(items)));
    assertArrayEquals(items, ds.toArray());
  }

  @Test
  public void testReadAllItemsFromEmptyDirStack() {
    String[] items = {};
    ds.addAll(new ArrayList<String>(Arrays.asList(items)));
    assertArrayEquals(items, ds.toArray());
  }
}
