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
    workingDirPath = getAbsolutePathOfDir(newWorkingDir);
    workingDir = newWorkingDir;
  }

  /**
   * Given a directory returns its absolute path
   *
   * @param theDir The directory for which the path is wanted
   * @return The absolute path to the directory
   */
  public String getAbsolutePathOfDir(Directory theDir) {
    StringBuilder path = new StringBuilder();
    Directory curDir = theDir;
    if (curDir.getName().equals("/")) {
      return "/";
    } else {
      while (!curDir.getName().equals("/")) {
        String segment =
            new StringBuilder("/" + curDir.getName()).reverse().toString();
        path.append(segment);
        curDir = curDir.getParent();
      }
      return path.reverse().toString();
    }
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
    String fileName = path.removeLast();
    Directory parent = getDirByPath(path);
    FSElement maybeFile = parent.getChildByName(fileName);
    if (maybeFile instanceof File) {
      return (File) maybeFile;
    } else {
      throw new FSElementNotFoundException();
    }
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
    Directory curr = workingDir;
    for (String segment : path) {
      if (segment.equals("/")) {
        curr = root;
      } else if (segment.equals("..")) {
        curr = curr.getParent();
        if (curr == null) {
          throw new MalformedPathException();
        }
      } else if (!segment.equals(".")) {
        FSElement maybeDir = curr.getChildByName(segment);
        if (maybeDir instanceof Directory) {
          curr = (Directory) maybeDir;
        } else {
          throw new FSElementNotFoundException();
        }
      }
    }
    return curr;
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

  /**
   * Gets a string representation of the path to the given file
   *
   * @param file The given file object
   * @return a string representing the file's path
   */
  public String getStringPath(File file) {
    return getStringPath(file.getParent()) + "/" + file.getName();
  }

  /**
   * Gets a string representation of the path to the given directory
   *
   * @param dir The given directory object
   * @return a string representing the directory's path
   */
  public String getStringPath(Directory dir) {
    if (dir == root) {
      return getStringPath(dir.getParent()) + "/" + dir.getName();
    } else {
      return root.getName();
    }
  }

}
