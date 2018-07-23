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
   * Clones the current FSElement.
   * 
   * @return Returns the cloned instance.
   */
  public FSElement clone() {
    return new FSElement(this.name, this.parent);
  }
}
