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

  private static FileSystem ourInstance = new FileSystem();
  private Directory root = new Directory("/");
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
    return ourInstance;
  }

  /**
   * Change the working dir to the dir given by the path
   *
   * @param path A path to the dir to change to
   */
  public void changeWorkingDir(String path) {
  }

  /**
   * Add a new file to the working directory
   *
   * @param theFile The file to add to the working directory
   */
  public void addFile(File theFile) {
  }

  /**
   * Add a new directory to the working directory
   *
   * @param theDir The directory to add to the working directory
   */
  public void addDir(Directory theDir) {
  }

  /**
   * Provides file located at given path to the caller
   *
   * @param path The path of the wanted file, can be absolute or
   * relative. Absolute path must start with / indicating root
   * directory.
   * @return The file located at the path
   */
  public File getFileByPath(String path) {
    return new File("none", "Not implemented");
  }

  /**
   * Provides directory located at given path to the caller
   *
   * @param path The path of the wanted file, can be absolute or
   * relative. Absolute path must start with / indicating root
   * directory.
   * @return The directory located at the path
   */
  public Directory getDirByPath(String path) {
    return new Directory("none");
  }

  /**
   * Get the path of the current working directory
   *
   * @return Absolute path of the current working directory
   */
  public String getWorkingDirPath() {
    return workingDirPath;
  }
}
