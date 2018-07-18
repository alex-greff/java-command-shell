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
import java.util.stream.Collectors;

/**
 * Represents a directory which can contain files and directories
 *
 * @author anton
 */
public class Directory extends FSElement {

  /**
   * The map containing all the child elements.
   */
  private HashMap<String, FSElement> children = new HashMap<>();

  /**
   * Creates a new directory given the name of the directory and its parent
   *
   * @param name The name of the new directory
   * @param parent The parent of this directory
   */
  public Directory(String name, Directory parent) {
    super(name, parent);
  }

  /**
   * Adds the given fselement child to itself overwriting if something with the
   * same name existed before
   */
  public void addChild(FSElement child) {
    this.children.put(child.getName(), child);
  }

  /**
   * Adds a given directory as a child of this directory if the directory does
   * not already exist
   *
   * @param name The name of the new child directory
   * @return Returns the new created directory
   * @throws FSElementAlreadyExistsException Thrown when there is already an
   * element with this name
   */
  public Directory createAndAddNewDir(String name)
      throws FSElementAlreadyExistsException {
    Directory newDir = new Directory(name, this);
    if (!children.containsKey(name)) {
      this.children.put(name, newDir);
    } else {
      throw new FSElementAlreadyExistsException();
    }
    return newDir;
  }

  /**
   * Adds a given file as a child of this directory if the file does not already
   * exist otherwise raises error
   *
   * @param name The name of the child file to create
   * @return The new file object that was created
   * @throws FSElementAlreadyExistsException Thrown when the file already
   * exists
   */
  public File createAndAddNewFile(String name)
      throws FSElementAlreadyExistsException {
    if (!children.containsKey(name)) {
      File newFile = new File(name, this);
      this.children.put(name, newFile);
      return newFile;
    } else {
      throw new FSElementAlreadyExistsException();
    }
  }

  /**
   * Adds a given file as a child of this directory if the file does not already
   * exist otherwise raises error
   *
   * @param name The name of the child file to create
   * @param contents The initial contents of the new file
   * @return The new file object that was created
   * @throws FSElementAlreadyExistsException Thrown when the file already
   * exists
   */
  public File createAndAddNewFile(String name, String contents)
      throws FSElementAlreadyExistsException {

    File f = createAndAddNewFile(name);
    f.write(contents);

    return f;
  }

  /**
   * Removes a child directory with the given name if it exists
   *
   * @param name The name of the child to remove
   */
  public void removeChildByName(String name) {
    children.remove(name);
  }

  /**
   * Returns a child FS element by name
   *
   * @param name The name of the child wanted
   * @return The child with the given name or null if it does not exist
   */
  public FSElement getChildByName(String name) {
    return children.get(name);
  }

  /**
   * Returns a child directory by name
   *
   * @param name The name of the child directory wanted
   * @return The child directory with the given name or null if it does not exit
   */
  public Directory getChildDirectoryByName(String name) {
    FSElement child = children.get(name);
    if (child instanceof Directory) {
      return (Directory) child;
    } else {
      return null;
    }
  }

  /**
   * Returns a child file by name
   *
   * @param name The name of the child file wanted
   * @return The child file with the given name or null if it does not exist
   */
  public File getChildFileByName(String name) {
    FSElement child = children.get(name);
    if (child instanceof File) {
      return (File) child;
    } else {
      return null;
    }
  }

  /**
   * Gets if a directory with name is a child directory.
   *
   * @param name The wanted name.
   * @return Returns true iff a child directory with name exists.
   */
  public boolean containsChildDirectory(String name) {
    return getChildDirectoryByName(name) != null;
  }

  /**
   * Gets if a file with name is a child file.
   *
   * @param name The wanted name.
   * @return Returns true iff a child file with name exists.
   */
  public boolean containsChildFile(String name) {
    return getChildFileByName(name) != null;
  }

  /**
   * Lists all the directory names inside of this directory
   *
   * @return A list of all the directory names inside this directory
   */
  public ArrayList<String> listDirNames() {
    return children.keySet().stream()
        .filter(name -> children.get(name) instanceof Directory)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Lists all the file names inside of this directory
   *
   * @return A list of all the file names inside this directory
   */
  public ArrayList<String> listFileNames() {
    return children.keySet().stream()
        .filter(name -> children.get(name) instanceof File)
        .collect(Collectors.toCollection(ArrayList::new));
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

  public boolean containsDir(String name) {
    return children.containsKey(name);
  }

  public Directory getDirByName(String name) {
    FSElement maybeDir = children.get(name);
    if (maybeDir instanceof Directory) {
      return (Directory) maybeDir;
    } else {
      return null;
    }
  }
}
