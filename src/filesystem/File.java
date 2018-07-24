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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a file object in the JShell It's contents can be read,
 * and written.
 *
 * @author anton
 */
public class File<T> extends FSElement implements Writable<T>, Readable {

  /**
   * The contents of the file. Each item in the parent queue represents a "line"
   * of data with the sub-queue items each representing a "part" of that line.
   * Example: we implement with type String. The following string look like the
   * following representation in the queue: The String: "First line Second line"
   * Contents: { { "First", "line" }, { "Second line" } }
   */
  private List<List<T>> contents = new ArrayList<>();

  /**
   * Create a new file given a name and the contents of the file
   *
   * @param name The name by which the file is to be referred, may or may not
   * contain an extension
   * @param contents The text data stored inside the file
   * @param parent The parent directory of this file
   */
  public File(String name, T contents, Directory parent) {
    super(name, parent);
    this.contents.add(new LinkedList<>(Arrays.asList(contents)));
  }

  /**
   * A private constructor initializing with the actual contents list array.
   * Useful for the copy method.
   *
   * @param name The name of the file.
   * @param content The internal content list.
   * @param parent The parent.
   */
  private File(String name, List<List<T>> content, Directory parent) {
    super(name, parent);
    this.contents = content;
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
  }

  /**
   * Writes the contents to the file. Does not change the old contents.
   *
   * @param contents The data to be added to the file.
   */
  @Override
  public void write(T contents) {
    if (this.contents.isEmpty()) {
      this.contents.add(new ArrayList<>(Arrays.asList(contents)));
    } else {
      this.contents.get(0).add(contents);
    }
  }

  /**
   * Writes a line to the contents to the file. Does not change the old
   * contents.
   *
   * @param contents The data to be added to the file.
   */
  @Override
  public void writeln(T contents) {
    this.contents.add(new LinkedList<>(Arrays.asList(contents)));
  }

  /**
   * Allows for reading of the data as a string from the file.
   *
   * @return The full contents of the file. Returns an empty string if the file
   * is empty.
   */
  @Override
  public String read() {
    StringBuilder ret_str_bldr = new StringBuilder();

    // Iterate through each line
    for (List<T> line : contents) {
      // Iterate through each item in the current line
      for (T item : line) {
        // Add the item to the return string
        ret_str_bldr.append(item);
      }
      // Add a newline to the return string
      ret_str_bldr.append("\n");
    }

    String ret_str = ret_str_bldr.toString();

    // Remove last newline if there is one
    if (ret_str.length() > 0) {
      ret_str = ret_str.substring(0, ret_str.length() - 1);
    }

    // Return the string representation of the file data
    return ret_str;
  }

  /**
   * Empties the contents of the file.
   */
  public void clear() {
    this.contents = new ArrayList<>();
  }

  /**
   * Gets if the file is empty.
   *
   * @return Return true iff the file is empty.
   */
  public boolean isEmpty() {
    return this.contents.size() == 0;
  }

  /**
   * Clones the current file. Warning: when cloning, the new instance is
   * unlinked (ie the parent directory has no record of it as its child).
   *
   * @return Returns the cloned instance.
   */
  @Override
  public FSElement copy() {
    // Make the new instance of the file
    return new File<>(this.name, this.contents, this.parent);
  }
}
