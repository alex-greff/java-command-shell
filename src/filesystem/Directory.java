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
  private HashMap<String, Directory> dirs = new HashMap<>();
  private HashMap<String, File> files = new HashMap<>();

  /**
   * Creates a new empty directory with the given name
   */
  public Directory(String name) {
    this.name = name;
  }

  /**
   * Adds a given directory as a child of this directory if the
   * directory does not already exist
   *
   * @param newDir The child directory
   */
  public void addDir(Directory newDir) {
    String dirName = newDir.getName();
    if (!dirs.containsKey(dirName)) {
      this.dirs.put(dirName, newDir);
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
    if (!files.containsKey(fileName)) {
      this.files.put(fileName, newFile);
    }
  }

  /**
   * Removes a child directory with the given name if it exists
   */
  public void removeDirByName(String name) {
    // TODO: maybe throw if the dir does not exist?
    dirs.remove(name);
  }

  /**
   * Removes a child file with the given name if it exists
   */
  public void removeFileByName(String name) {
    // TODO: maybe throw if the file does not exist?
    files.remove(name);
  }

  /**
   * Checks if the file with the given name exits in this directory
   *
   * @param name The name of the file to look for
   * @return True if the file exists, False otherwise
   */
  public boolean containsFile(String name) {
    return this.files.containsKey(name);
  }

  /**
   * Checks if the directory with the given name exits in this
   * directory
   *
   * @param name The name of the directory to look for
   * @return True if the directory exists, False otherwise
   */
  public boolean containsDir(String name) {
    return this.dirs.containsKey(name);
  }

  /**
   * Returns a child directory by name
   *
   * @param name The name of the directory wanted
   * @return The directory with the given name
   */
  public Directory getDirByName(String name) {
    // TODO: Throw if the dir does not exist
    return dirs.get(name);
  }

  /**
   * Returns a child file by name
   *
   * @param name The name of the file wanted
   * @return The file with the given name
   */
  public File getFileByName(String name) {
    // TODO: Throw if the file does not exist
    return files.get(name);
  }

  /**
   * Lists all the directories inside of this directory
   *
   * @return A list of all the directories inside this directory
   */
  public ArrayList<Directory> listDirs() {
    return new ArrayList<>(this.dirs.values());
  }

  /**
   * Lists all the files inside of this directory
   *
   * @return A list of all the files inside this directory
   */
  public ArrayList<File> listFiles() {
    return new ArrayList<>(this.files.values());
  }

  /**
   * Provides the name of this directory
   *
   * @return The name of this directory
   */
  public String getName() {
    return name;
  }
}
