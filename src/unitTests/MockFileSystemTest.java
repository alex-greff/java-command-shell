package unitTests;

import static org.junit.Assert.*;
import org.junit.*;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileNotFoundException;
import filesystem.MalformedPathException;

public class MockFileSystemTest {
  @Test
  public void testChangeWorkingDirWithValidPath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.changeWorkingDir("validPath");
  }

  @Test(expected = MalformedPathException.class)
  public void testChangeWorkingDirWithInvalidPath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.changeWorkingDir("invalidPath");
  }

  @Test(expected = FileNotFoundException.class)
  public void testChangeWorkingDirWithNonExistentFilePath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.changeWorkingDir("nonExistentFilePath");
  }
  
  @Test
  public void testGetAbsolutePathOfDirNormalDirectory() {
    MockFileSystem mfs = new MockFileSystem();
    String path = mfs.getAbsolutePathOfDir(new Directory("myDir", null));
    assertEquals(path, "/some/valid/directory/myDir");
  }
  
  @Test
  public void testGetAbsolutePathOfDirRootDirectory() {
    MockFileSystem mfs = new MockFileSystem();
    String path = mfs.getAbsolutePathOfDir(new Directory("root", null));
    assertEquals(path, "/some/valid/directory/root");
  }
  
  @Test
  public void testGetFileByPathWithValidPath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    File f = mfs.getFileByPath("validPath");
    assertEquals(f.getName(), "someFile");
    assertEquals(f.read(), "some file contents");
  }

  @Test(expected = MalformedPathException.class)
  public void testGetFileByPathWithInvalidPath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getFileByPath("invalidPath");
  }

  @Test(expected = FileNotFoundException.class)
  public void testGetFileByPathWithNonExistentFilePath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getFileByPath("nonExistentFilePath");
  }
  
  @Test
  public void testGetDirByPathWithValidPath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    Directory d = mfs.getDirByPath("validPath");
    assertEquals(d.getName(), "someDirectory");
    assertNull(d.getParent());
  }

  @Test(expected = MalformedPathException.class)
  public void testGetDirByPathWithInvalidPath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getDirByPath("invalidPath");
  }

  @Test(expected = FileNotFoundException.class)
  public void testGetDirByPathWithNonExistentFilePath()
      throws MalformedPathException, FileNotFoundException {

    MockFileSystem mfs = new MockFileSystem();
    mfs.getDirByPath("nonExistentFilePath");
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
