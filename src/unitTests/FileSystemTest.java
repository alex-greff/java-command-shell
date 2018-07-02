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
import static org.junit.Assert.assertTrue;

import filesystem.Directory;
import filesystem.FileAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;

public class FileSystemTest {

  @Before
  public void resetSingleton()
      throws SecurityException, NoSuchFieldException,
      IllegalArgumentException, IllegalAccessException {
    Field instance = FileSystem.class.getDeclaredField("ourInstance");
    instance.setAccessible(true);
    instance.set(null, null);
  }

  @Test
  public void testAddingNewDirectoryToWorkingDirectory()
      throws FileAlreadyExistsException {
    FileSystem fs = FileSystem.getInstance();
    // make sure that the current working directory is root
    assertEquals("/", fs.getWorkingDirPath());
    // add a new directory to the working directory
    fs.getWorkingDir().createAndAddNewDir("test");
    // ensure that this directory is now in the working directory
    assertTrue(fs.getWorkingDir().containsDir("test"));
  }

  @Test
  public void testGettingAbsolutePathOfDirectory()
      throws FileNotFoundException, FileAlreadyExistsException {
    FileSystem fs = FileSystem.getInstance();
    // add a new directory to the working directory
    fs.getWorkingDir().createAndAddNewDir("test");
    // the filesystem should now contain a directory called "test"
    Directory testDir = fs.getWorkingDir().getDirByName("test");
    assertEquals("/test", fs.getAbsolutePathOfDir(testDir));
    // make sure it also works with the root
    assertEquals("/", fs.getAbsolutePathOfDir(fs.getRoot()));
  }

  @Test
  public void testGettingRootDirectoryByPath()
      throws MalformedPathException, FileNotFoundException {
    FileSystem fs = FileSystem.getInstance();
    // make a path for root
    Path root = new Path("/");
    Directory rt = fs.getDirByPath(root);
    assertEquals(fs.getRoot(), rt);

  }

  @Test
  public void testGettingRootDirectoryWithConvolutedPath()
      throws MalformedPathException, FileNotFoundException {
    FileSystem fs = FileSystem.getInstance();
    // make a convoluted path
    Path spooky = new Path("/./././././././");
    Directory spookyRoot = fs.getDirByPath(spooky);
    assertEquals(fs.getRoot(), spookyRoot);
  }

  @Test
  public void testGettingNonRootDirectoryByPath()
      throws MalformedPathException, FileNotFoundException, FileAlreadyExistsException {
    FileSystem fs = FileSystem.getInstance();
    // add a new directory to the working directory
    fs.getWorkingDir().createAndAddNewDir("test");
    Path dirPath = new Path("/test/");
    Directory expected = fs.getRoot().getDirByName("test");
    Directory actual = fs.getDirByPath(dirPath);
    assertEquals(expected, actual);
  }
}