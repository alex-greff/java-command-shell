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
 */
public class FileSystem {

  private static FileSystem ourInstance = null;
  // the root dir has no parent
  private Directory root = new Directory("/", null);
  private Directory workingDir = root;
  private String workingDirPath = "/";

  private FileSystem() {
  }

  /**
   * Get the singleton instance of the filesystem
   *
   * @return This filesystem instance
   */
  public static FileSystem getInstance() {
    if (ourInstance == null) {
      ourInstance = new FileSystem();
    }
    return ourInstance;
  }

  /**
   * Change the working dir to the dir given by the path
   *
   * @param path A path to the dir to change to
   * @throws FileNotFoundException Thrown when a directory does not exist
   */
  public void changeWorkingDir(Path path)
      throws MalformedPathException, FileNotFoundException {
    Directory newWorkingDir = getDirByPath(path);
    if (newWorkingDir == null) {
      throw new FileNotFoundException();
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
   */
  public File getFileByPath(Path path)
      throws MalformedPathException, FileNotFoundException {
    String fileName = path.removeLast();
    Directory parent = getDirByPath(path);
    return parent.getFileByName(fileName);
  }

  /**
   * Provides directory located at given path to the caller
   *
   * @param path The path of the wanted file, can be absolute or relative.
   * Absolute path must start with / indicating root directory.
   * @return The directory located at the path
   */
  public Directory getDirByPath(Path path)
      throws MalformedPathException, FileNotFoundException {
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
        curr = curr.getDirByName(segment);
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

}
