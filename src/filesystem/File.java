package filesystem;

import io.Readable;
import io.Writable;

/**
 * @author anton
 *
 * This class represents a file object in the JShell
 * It's contents can be read, and written
 */
public class File implements Readable, Writable {

  private final String name;
  private String contents;

  /**
   * Create a new file given a name and the contents of the file
   * @param name The name by which the file is to be referred, may
   * or may not contain an extension
   * @param contents The text data stored inside the file
   */
  public File(String name, String contents) {
    this.name = name;
    this.contents = contents;
  }

  /**
   * Create a new file with the given name, and empty contents
   * @param name The name by which the file is to be referred, may
   * or may not contain an extension
   */
  public File(String name) {
    this.name = name;
    this.contents = "";
  }

  /**
   * Writes the contents to the file. Does not change the old
   * contents.
   * @param contents The data to be added to the file.
   */
  @Override
  public void write(String contents) {
    this.contents += contents;
  }

  /**
   * Allows for reading of data from the file.
   * @return The full contents of the file
   */
  @Override
  public String read() {
    return this.contents;
  }
}
