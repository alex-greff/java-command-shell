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
import static org.junit.Assert.assertNull;

import filesystem.Directory;
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.MalformedPathException;
import filesystem.Path;
import org.junit.Test;

public class MockFileSystemTest {

  @Test
  public void testChangeWorkingDirWithValidPath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.changeWorkingDir(new Path("validPath"));
  }

  @Test(expected = MalformedPathException.class)
  public void testChangeWorkingDirWithInvalidPath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.changeWorkingDir(new Path("invalidPath"));
  }

  @Test(expected = FSElementNotFoundException.class)
  public void testChangeWorkingDirWithNonExistentFilePath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.changeWorkingDir(new Path("nonExistentFilePath"));
  }

  @Test
  public void testGetAbsolutePathOfDirNormalDirectory() {
    MockFileSystem mfs = new MockFileSystem();
    String path = mfs.getAbsolutePathOfFSElement(new Directory("myDir", null));
    assertEquals(path, "/some/valid/directory/myDir");
  }

  @Test
  public void testGetAbsolutePathOfDirRootDirectory() {
    MockFileSystem mfs = new MockFileSystem();
    String path = mfs.getAbsolutePathOfFSElement(new Directory("root", null));
    assertEquals(path, "/some/valid/directory/root");
  }

  @Test
  public void testGetFileByPathWithValidPath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    File f = mfs.getFileByPath(new Path("validPath"));
    assertEquals(f.getName(), "someFile");
    assertEquals(f.read(), "some file contents");
  }

  @Test(expected = MalformedPathException.class)
  public void testGetFileByPathWithInvalidPath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getFileByPath(new Path("invalidPath"));
  }

  @Test(expected = FSElementNotFoundException.class)
  public void testGetFileByPathWithNonExistentFilePath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getFileByPath(new Path("nonExistentFilePath"));
  }

  @Test
  public void testGetDirByPathWithValidPath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    Directory d = mfs.getDirByPath(new Path("validPath"));
    assertEquals(d.getName(), "someDirectory");
    assertNull(d.getParent());
  }

  @Test(expected = MalformedPathException.class)
  public void testGetDirByPathWithInvalidPath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getDirByPath(new Path("invalidPath"));
  }

  @Test(expected = FSElementNotFoundException.class)
  public void testGetDirByPathWithNonExistentFilePath()
      throws MalformedPathException, FSElementNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getDirByPath(new Path("nonExistentFilePath"));
  }

  @Test
  public void testGetWorkingDir() {
    MockFileSystem mfs = new MockFileSystem();
    Directory d = mfs.getWorkingDir();
    assertEquals(d.getName(), "myWorkingDirectory");
    assertNull(d.getParent());
  }

  @Test
  public void testGetWorkingDirPath() {
    MockFileSystem mfs = new MockFileSystem();
    String s = mfs.getWorkingDirPath();
    assertEquals(s, "/some/path/to/myWorkingDirectory");
  }

  @Test
  public void testGetRoot() {
    MockFileSystem mfs = new MockFileSystem();
    Directory d = mfs.getRoot();
    assertEquals(d.getName(), "root");
    assertNull(d.getParent());
  }
}
