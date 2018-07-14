package filesystem;

/**
 * Represents an element that can be stored in the filesystem
 */
public class FSElement {

  public Directory getParent() {
    return parent;
  }

  public String getName() {
    return name;
  }

  protected Directory parent;
  protected String name;

  public FSElement(String name, Directory parent) {
    this.parent = parent;
    this.name = name;
  }

}
