package unitTests;

import java.util.Stack;
import io.Writable;

/**
 * A console for testing commands. Allows for easy lookup for the output of
 * commands.
 * 
 * @author ajg
 *
 */
public class TestingConsole implements Writable {
  private Stack<String> inputs = new Stack<String>();

  /**
   * Writes the content input to a list with the most recent at the head.
   * Newlines at the end of the content string are trimmed.
   * 
   * @param contents The contents to be written
   */
  @Override
  public void write(String contents) {
    inputs.push(contents.trim());
  }

  /**
   * Writes the content input to a list with the most recent at the head.
   * Newlines at the end of the content string are trimmed.
   * 
   * @param contents The contents to be written
   */
  @Override
  public void writeln(String contents) {
    inputs.push(contents.trim());
  }

  /**
   * Gets the most recent write to the console.
   * 
   * @return returns the most recent write to the console
   */
  public String getLastWrite() {
    return inputs.peek();
  }

  /**
   * Gets a string array of all the writes to the console. The most recent
   * writes are stored first in the array.
   * 
   * @return Returns the list of all writes to the console.
   */
  public String[] getAllWrites() {
    String[] ret = new String[inputs.size()];
    inputs.toArray(ret);

    return ret;
  }
}
