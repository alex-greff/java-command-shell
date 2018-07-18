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
 * Manages files, directories and the current working directory.
 *
 * @author anton
 */
public class InMemoryFileSystem implements FileSystem {
  // the root dir has no parent
  /**
   * The root directory object.
   */
  private Directory root = new Directory("/", null);
  /**
   * The current working directory.
   */
  private Directory workingDir = root;
  /**
   * The path to the current working directory.
   */
  private String workingDirPath = "/";

  /**
   * Public default constructor.
   */
  public InMemoryFileSystem() {
  }

  /**
   * Change the working dir to the dir given by the path
   *
   * @param path A path to the dir to change to
   * @throws FSElementNotFoundException Thrown when the directory does not
   * exist
   * @throws MalformedPathException Thrown when the path is invalid
   */
  public void changeWorkingDir(Path path)
      throws MalformedPathException, FSElementNotFoundException {
    Directory newWorkingDir = getDirByPath(path);
    if (newWorkingDir == null) {
      throw new FSElementNotFoundException();
    }
    workingDirPath = getAbsolutePathOfFSElement(newWorkingDir);
    workingDir = newWorkingDir;
  }

  /**
   * Given an fselement returns its absolute path in the filesystem
   *
   * @param theElement The fselement for which the path is wanted
   * @return The absolute path to the directory
   */
  public String getAbsolutePathOfFSElement(FSElement theElement) {
    String result = "";
    if (theElement == root) {
      return "/";
    } else if (theElement.getParent() != root) {
      result += getAbsolutePathOfFSElement(theElement.getParent());
    }
    result += "/" + theElement.getName();
    return result;
  }

  /**
   * Provides file located at given path to the caller
   *
   * @param path The path of the wanted file, can be absolute or relative.
   * Absolute path must start with / indicating root directory.
   * @return The file located at the path
   * @throws FSElementNotFoundException Thrown when the file does not exist
   * @throws MalformedPathException Thrown when the path is invalid
   */
  public File getFileByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException {
    Path copyPath = new Path(path);
    String fileName = copyPath.removeLast();
    Directory parent = getDirByPath(copyPath);
    File f = parent.getChildFileByName(fileName); 
    if (f == null)
      throw new FSElementNotFoundException();
    return f;
  }

  /**
   * Provides directory located at given path to the caller
   *
   * @param thePath The path of the wanted file, can be absolute or relative.
   * Absolute path must start with / indicating root directory.
   * @return The directory located at the path
   * @throws FSElementNotFoundException Thrown when the directory does not
   * exist
   * @throws MalformedPathException Thrown when the path is invalid
   */
  public FSElement getFSElementByPath(Path thePath)
      throws MalformedPathException, FSElementNotFoundException {
    // don't mutate the original path
    Path path = new Path(thePath);
    Directory curr = workingDir;
    while (!path.isEmpty()) {
      String segment = path.removeFirst();
      if (segment.equals("/")) {
        curr = root;
      } else if (segment.equals("..")) {
        curr = curr.getParent();
        if (curr == null) {
          // can't get roots parent
          throw new MalformedPathException();
        }
      } else if (!segment.equals(".")) {
        FSElement maybeDir = curr.getChildByName(segment);
        if (maybeDir instanceof Directory) {
          curr = (Directory) maybeDir;
        } else if (maybeDir == null || !path.isEmpty()) {
          throw new FSElementNotFoundException();
        }
      }
    }
    return curr;
  }

  /**
   * Provides directory located at given path to the caller
   *
   * @param path The path of the wanted file, can be absolute or relative.
   * Absolute path must start with / indicating root directory.
   * @return The directory located at the path
   * @throws FSElementNotFoundException Thrown when the directory does not
   * exist
   * @throws MalformedPathException Thrown when the path is invalid
   */
  public Directory getDirByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException {
    FSElement maybeDir = getFSElementByPath(path);
    if (!(maybeDir instanceof Directory)) {
      throw new FSElementNotFoundException();
    }
    return (Directory) maybeDir;
  }

  /**
   * Getter for the working directory
   *
   * @return the working directory object
   */
  public Directory getWorkingDir() {
    return workingDir;
  }

  /**
   * Get the path of the current working directory
   *
   * @return Absolute path of the current working directory
   */
  public String getWorkingDirPath() {
    return workingDirPath;
  }

  /**
   * Getter for the root directory
   *
   * @return the root directory object
   */
  public Directory getRoot() {
    return root;
  }

}
