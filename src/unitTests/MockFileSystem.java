package unitTests;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;

public class MockFileSystem implements FileSystem {

  /**
   * Simulates changing the working directory. Use "validPath" to simulate a
   * valid path behavior. Use "invalidPath" and "nonExistentFilePath" to throw
   * MalformedPathException and FileNotFoundException, respectively.
   * 
   * @param pathString The string of the path.
   */
  @Override
  public void changeWorkingDir(String pathString)
      throws MalformedPathException, FileNotFoundException {
    switch (pathString) {
      case "validPath":
        break;
      case "invalidPath":
        throw new MalformedPathException();
      case "nonExistentFilePath":
        throw new FileNotFoundException();
    }
  }

  /**
   * Simulates getting the absolute path of a directory.
   * 
   * @param theDir The wanted directory.
   * @return Returns "/some/valid/directory/[theDir.getName()]"
   */
  @Override
  public String getAbsolutePathOfDir(Directory theDir) {
    return "/some/valid/directory/" + theDir.getName();
  }

  /**
   * Simulates getting a file by its path string. Use "validPath" to simulate a
   * valid path behavior. Use "invalidPath" and "nonExistentFilePath" to throw
   * MalformedPathException and FileNotFoundException, respectively. Using any
   * other string for pathString will throw a FileNotFoundException by default.
   * 
   * @param pathString The string of the path.
   * @return Returns a file with the name "someFile" and the contents "some file
   *         contents".
   */
  @Override
  public File getFileByPath(String pathString)
      throws MalformedPathException, FileNotFoundException {
    switch (pathString) {
      case "validPath":
        return new File("someFile", "some file contents");
      case "invalidPath":
        throw new MalformedPathException();
      case "nonExistentFilePath":
        throw new FileNotFoundException();
      default:
        throw new FileNotFoundException();
    }
  }

  /**
   * Simulates getting a directory by its path string. Use "validPath" to
   * simulate a valid path behavior. Use "invalidPath" and "nonExistentFilePath"
   * to throw MalformedPathException and FileNotFoundException, respectively.
   * Using any other string for pathString will throw a FileNotFoundException by
   * default.
   * 
   * @param pathString The string of the path.
   * @return Returns a directory with the name "someDirectory" with no parent or
   *         children.
   */
  @Override
  public Directory getDirByPath(String pathString)
      throws MalformedPathException, FileNotFoundException {
    switch (pathString) {
      case "validPath":
        return new Directory("someDirectory", null);
      case "invalidPath":
        throw new MalformedPathException();
      case "nonExistentFilePath":
        throw new FileNotFoundException();
      default:
        throw new FileNotFoundException();
    }
  }

  /**
   * Simulates getting the working directory.
   * 
   * @return Returns a directory with name "myWorkingDirectory" with no parent
   *         or children.
   */
  @Override
  public Directory getWorkingDir() {
    return new Directory("myWorkingDirectory", null);
  }

  /**
   * Simulates getting the path to the working directory.
   * 
   * @return Returns the string "/some/path/to/myWorkingDirectory".
   */
  @Override
  public String getWorkingDirPath() {
    return "/some/path/to/myWorkingDirectory";
  }

  /**
   * Simulates getting the root directory.
   * 
   * @return Returns a directory with name "root" with no parent or children.
   */
  @Override
  public Directory getRoot() {
    return new Directory("root", null);
  }
}
