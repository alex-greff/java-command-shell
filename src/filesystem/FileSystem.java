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
package filesystem;

/**
 * The file system interface.
 *
 * @author greff
 */
public interface FileSystem {

  /**
   * Changes the current working directory to the location at path.
   *
   * @param path The path of the new working directory.
   * @throws MalformedPathException Throws if the path is invalid.
   * @throws FSElementNotFoundException Throws if the file/directory is not
   *         found.
   */
  public void changeWorkingDir(Path path)
      throws MalformedPathException, FSElementNotFoundException;

  /**
   * Gets the absolute path to an element in the filesystem.
   *
   * @param theElement The target fselement.
   * @return Returns a string with the absolute path.
   */
  public String getAbsolutePathOfFSElement(FSElement theElement);

  /**
   * Gets a file by the given path.
   * 
   * @param path The path.
   * @return Returns the found file.
   * @throws MalformedPathException Thrown if the path is invalid.
   * @throws FSElementNotFoundException Thrown if no file is found.
   */
  public File<?> getFileByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException;
  /**
   * Gets a directory by the given path.
   * 
   * @param path The path.
   * @return Returns the found directory.
   * @throws MalformedPathException Thrown if the path is invalid.
   * @throws FSElementNotFoundException Thrown if no directory is found.
   */
  public Directory getDirByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException;

  /**
   * Gets the current working directory.
   * 
   * @return Returns the currently working directory.
   */
  public Directory getWorkingDir();

  /**
   * Gets the absolute path of the current working directory.
   * 
   * @return Returns the absolute path of the current working directory.
   */
  public String getWorkingDirPath();

  /**
   * Gets the root directory of the file system.
   * 
   * @return Returns the root directory.
   */
  public Directory getRoot();

  /**
   * Gets a FS element by the given path.
   * 
   * @param path The path.
   * @return Returns the found FS element.
   * @throws MalformedPathException Thrown if the path is invalid.
   * @throws FSElementNotFoundException Thrown if no FS element is found.
   */
  public FSElement getFSElementByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException;
}
