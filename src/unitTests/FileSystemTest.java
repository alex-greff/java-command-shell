package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filesystem.Directory;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import org.junit.Test;

public class FileSystemTest {

  @Test
  public void testAddingNewDirectoryToWorkingDirectory() {
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
      throws FileNotFoundException {
    FileSystem fs = FileSystem.getInstance();
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
      throws MalformedPathException, FileNotFoundException {
    FileSystem fs = FileSystem.getInstance();
    Path dirPath = new Path("/test/");
    Directory expected = fs.getRoot().getDirByName("test");
    Directory actual = fs.getDirByPath(dirPath);
    assertEquals(expected, actual);
  }
}