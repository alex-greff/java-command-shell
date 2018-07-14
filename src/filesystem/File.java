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

import io.Readable;
import io.Writable;

/**
 * This class represents a file object in the JShell It's contents can be read,
 * and written.
 *
 * @author anton
 */
public class File extends FSElement implements Writable, Readable {

  /**
   * The contents of the file.
   */
  private String contents;

  /**
   * Create a new file given a name and the contents of the file
   *
   * @param name The name by which the file is to be referred, may or may not
   * contain an extension
   * @param contents The text data stored inside the file
   * @param parent The parent directory of this file
   */
  public File(String name, String contents, Directory parent) {
    super(name, parent);
    this.contents = contents;
  }

  /**
   * Create a new file with the given name, and empty contents
   *
   * @param name The name by which the file is to be referred, may or may not
   * contain an extension
   * @param parent The parent directory of this file
   */
  public File(String name, Directory parent) {
    super(name, parent);
    this.contents = "";
  }

  /**
   * Writes the contents to the file. Does not change the old contents.
   *
   * @param contents The data to be added to the file.
   */
  @Override
  public void write(String contents) {
    this.contents += contents;
  }

  /**
   * Writes a line to the contents to the file. Does not change the old
   * contents.
   *
   * @param contents The data to be added to the file.
   */
  @Override
  public void writeln(String contents) {
    this.contents += contents + "\n";
  }

  /**
   * Allows for reading of data from the file.
   *
   * @return The full contents of the file
   */
  @Override
  public String read() {
    return this.contents;
  }

  /**
   * Empties the contents of the file.
   */
  public void clear() {
    this.contents = "";
  }
}
