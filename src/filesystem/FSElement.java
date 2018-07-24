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
 * Represents an element that can be stored in the filesystem
 */
public class FSElement {

  /**
   * The parent of this element in the filesystem
   */
  protected Directory parent;
  /**
   * The name of this element in the filesystem
   */
  protected String name;

  /**
   * Constructs a new element
   *
   * @param name The name of this element
   * @param parent The parent directory of this element
   */
  public FSElement(String name, Directory parent) {
    this.parent = parent;
    this.name = name;
  }

  /**
   * Provides the parent of this element
   *
   * @return The parent of the fselement
   */
  public Directory getParent() {
    return parent;
  }

  /**
   * Sets the current fselement's parent to newParent
   * 
   * @param newParent The new parent.
   */
  public void changeParent(Directory newParent) {
    this.parent = newParent;
  }

  /**
   * Provides the name of this element
   *
   * @return The name of the fselement
   */
  public String getName() {
    return name;
  }

  /**
   * Renames this element
   *
   * @param name The new name of this element
   */
  public void rename(String name) {
    if (this.parent != null)
      this.parent.notifyRename(this.name, name);
    this.name = name;
  }

  /**
   * Copies the current FSElement. Warning: when copying, the new instance is
   * unlinked (ie the parent directory has no record of it as its child).
   * 
   * @return Returns the cloned instance.
   */
  public FSElement copy() {
    return new FSElement(this.name, this.parent);
  }

  /**
   * Renames this fselement without notifying its parent
   * Make sure you know what you are doing if you want to use this
   * Most likely what you really need is rename
   * @param newName The new name of this fselement
   */
  public void setName(String newName) {
    this.name = newName;
  }
}
