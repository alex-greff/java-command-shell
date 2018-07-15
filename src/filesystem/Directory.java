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
   * Adds a given directory as a child of this directory if the directory does
   * not already exist
   *
   * @param name The name of the new child directory
   * @return Returns the new created directory
   * @throws FSElementAlreadyExistsException Thrown when the directory already
   *         exists
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
   * @throws FSElementAlreadyExistsException Thrown when the file already exists
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
   * @throws FSElementAlreadyExistsException Thrown when the file already exists
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
   * @throws FSElementNotFoundException Thrown if the directory is not found
   */
  public void removeChildByName(String name) throws FSElementNotFoundException {
    if (!children.containsKey(name)) {
      throw new FSElementNotFoundException();
    }
    children.remove(name);
  }

  /**
   * Returns a child FS element by name
   *
   * @param name The name of the child wanted
   * @return The child with the given name
   * @throws FSElementNotFoundException Thrown if the child FS element is not
   *         found
   */
  public FSElement getChildByName(String name)
      throws FSElementNotFoundException {
    if (!children.containsKey(name)) {
      throw new FSElementNotFoundException();
    }
    return children.get(name);
  }

  /**
   * Returns a child directory by name
   * 
   * @param name The name of the child directory wanted
   * @return The child directory with the given name
   * @throws FSElementNotFoundException Thrown if the child directory is not
   *         found
   */
  public Directory getChildDirectoryByName(String name)
      throws FSElementNotFoundException {
    FSElement fse = getChildByName(name);

    if (!(fse instanceof Directory))
      throw new FSElementNotFoundException();

    return (Directory) fse;
  }

  /**
   * Returns a child file by name
   * 
   * @param name The name of the child file wanted
   * @return The child file with the given name
   * @throws FSElementNotFoundException Thrown if the child file is not found
   */
  public File getChildFileByName(String name)
      throws FSElementNotFoundException {
    FSElement fse = getChildByName(name);

    if (!(fse instanceof File))
      throw new FSElementNotFoundException();

    return (File) fse;
  }

  /**
   * Lists all the directory names inside of this directory
   *
   * @return A list of all the directory names inside this directory
   */
  public ArrayList<String> listDirNames() {
    return new ArrayList<>(this.children.keySet());
  }

  /**
   * Lists all the file names inside of this directory
   *
   * @return A list of all the file names inside this directory
   */
  public ArrayList<String> listFiles() {
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
}
