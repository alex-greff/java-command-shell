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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a directory which can contain files and directories
 *
 * @author anton
 */
public class Directory {

  private String name;
  private Directory parent;
  private HashMap<String, Directory> childDirs = new HashMap<>();
  private HashMap<String, File> childFiles = new HashMap<>();

  /**
   * Creates a new directory given the name of the directory and its
   * parent
   *
   * @param name The name of the new directory
   * @param parent The parent of this directory
   */
  public Directory(String name, Directory parent) {
    this.name = name;
    this.parent = parent;
  }

  /**
   * Adds a given directory as a child of this directory if the
   * directory does not already exist
   *
   * @param newDir The child directory
   */
  public void addDir(Directory newDir) {
    String dirName = newDir.getName();
    if (!childDirs.containsKey(dirName)) {
      this.childDirs.put(dirName, newDir);
    }
  }

  /**
   * Adds a given file as a child of this directory if the file does
   * not already exist
   *
   * @param newFile The child file
   */
  public void addFile(File newFile) {
    String fileName = newFile.getName();
    if (!childFiles.containsKey(fileName)) {
      this.childFiles.put(fileName, newFile);
    }
  }

  /**
   * Removes a child directory with the given name if it exists
   *
   * @param name The name of the directory to remove
   */
  public void removeDirByName(String name) {
    // TODO: maybe throw if the dir does not exist?
    childDirs.remove(name);
  }

  /**
   * Removes a child file with the given name if it exists
   *
   * @param name The name of the file to remove
   */
  public void removeFileByName(String name) {
    // TODO: maybe throw if the file does not exist?
    childFiles.remove(name);
  }

  /**
   * Checks if the file with the given name exits in this directory
   *
   * @param name The name of the file to look for
   * @return True if the file exists, False otherwise
   */
  public boolean containsFile(String name) {
    return this.childFiles.containsKey(name);
  }

  /**
   * Checks if the directory with the given name exits in this
   * directory
   *
   * @param name The name of the directory to look for
   * @return True if the directory exists, False otherwise
   */
  public boolean containsDir(String name) {
    return this.childDirs.containsKey(name);
  }

  /**
   * Returns a child directory by name
   *
   * @param name The name of the directory wanted
   * @return The directory with the given name
   */
  public Directory getDirByName(String name) {
    // TODO: Throw if the dir does not exist
    return childDirs.get(name);
  }

  /**
   * Returns a child file by name
   *
   * @param name The name of the file wanted
   * @return The file with the given name
   */
  public File getFileByName(String name) {
    // TODO: Throw if the file does not exist
    return childFiles.get(name);
  }

  /**
   * Lists all the directory names inside of this directory
   *
   * @return A list of all the directory names inside this directory
   */
  public ArrayList<String> listDirNames() {
    return new ArrayList<>(this.childDirs.keySet());
  }

  /**
   * Lists all the file names inside of this directory
   *
   * @return A list of all the file names inside this directory
   */
  public ArrayList<String> listFiles() {
    return new ArrayList<>(this.childFiles.keySet());
  }

  /**
   * Provides the name of this directory
   *
   * @return The name of this directory
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the parent of this directory
   *
   * @return The parent of this directory
   */
  public Directory getParent() {
    return parent;
  }
}
