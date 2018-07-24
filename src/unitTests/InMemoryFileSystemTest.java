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
import filesystem.FSElementAlreadyExistsException;
import filesystem.FSElementNotFoundException;
import filesystem.InMemoryFileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import org.junit.Test;

public class InMemoryFileSystemTest {

  @Test
  public void testAddingNewDirectoryToWorkingDirectory()
      throws FSElementAlreadyExistsException {
    InMemoryFileSystem fs = new InMemoryFileSystem();
    // make sure that the current working directory is root
    assertEquals("/", fs.getWorkingDirPath());
    // add a new directory to the working directory
    fs.getWorkingDir().createAndAddNewDir("test");
    // ensure that this directory is now in the working directory
    assertTrue(fs.getWorkingDir().containsChildElement("test"));
  }

  @Test
  public void testGettingAbsolutePathOfDirectory()
      throws FSElementAlreadyExistsException {
    InMemoryFileSystem fs = new InMemoryFileSystem();
    // add a new directory to the working directory
    fs.getWorkingDir().createAndAddNewDir("test");
    // the filesystem should now contain a directory called "test"
    Directory testDir = fs.getWorkingDir().getChildDirectoryByName("test");
    assertEquals("/test", fs.getAbsolutePathOfFSElement(testDir));
    // make sure it also works with the root
    assertEquals("/", fs.getAbsolutePathOfFSElement(fs.getRoot()));
  }

  @Test
  public void testGettingRootDirectoryByPath()
      throws MalformedPathException, FSElementNotFoundException {
    InMemoryFileSystem fs = new InMemoryFileSystem();
    // make a path for root
    Directory rt = fs.getDirByPath(new Path("/"));
    assertEquals(fs.getRoot(), rt);

  }

  @Test
  public void testGettingRootDirectoryWithConvolutedPath()
      throws MalformedPathException, FSElementNotFoundException {
    InMemoryFileSystem fs = new InMemoryFileSystem();
    // make a convoluted path
    Directory spookyRoot = fs.getDirByPath(new Path("/./././././././"));
    assertEquals(fs.getRoot(), spookyRoot);
  }

  @Test
  public void testGettingNonRootDirectoryByPath() throws MalformedPathException,
                                                         FSElementNotFoundException, FSElementAlreadyExistsException {
    InMemoryFileSystem fs = new InMemoryFileSystem();
    // add a new directory to the working directory
    fs.getWorkingDir().createAndAddNewDir("test");
    Directory expected = fs.getRoot().getChildDirectoryByName("test");
    Directory actual = fs.getDirByPath(new Path("/test/"));
    assertEquals(expected, actual);
  }

  @Test
  public void testGetWorkingDirectoryWithRoot() {

  }

  @Test
  public void testGetWorkingDirectoryWithRegularDirectory() {

  }

  @Test
  public void testGetWorkingDirectoryPathWithRoot() {

  }

  @Test
  public void testGetWorkingDirectoryPathWithRegularDirectory() {

  }

  @Test
  public void testChangeWorkingDirectoryToRoot() {

  }

  @Test
  public void testChangeWorkingDirectoryToFile() {

  }

  @Test
  public void testChangeWorkingDirectoryToDirectory() {

  }

  @Test
  public void testChangeWorkingDirectoryToFSElement() {

  }

  @Test
  public void testGetFileByPathNonExistentFile() {

  }

  @Test
  public void testGetFileByPathWithPathToFile() {

  }

  @Test
  public void testGetFileByPathWithPathToDirectory() {

  }

  @Test
  public void testGetDirectoryByPathToNonExistentDirectory() {

  }

  @Test
  public void testGetDirectoryByPathWithPathToFile() {

  }

  @Test
  public void testGetDirectoryByPathWithPathToDirectory() {

  }

  @Test
  public void testGetRoot() {

  }
}
