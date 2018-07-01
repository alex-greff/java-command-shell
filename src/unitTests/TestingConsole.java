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
    String prev_line = "";

    if (!inputs.isEmpty()) {
      prev_line = inputs.pop();
    }

    inputs.push(prev_line + contents);
  }

  /**
   * Writes the content input to a list with the most recent at the head.
   * Newlines at the end of the content string are trimmed.
   * 
   * @param contents The contents to be written
   */
  @Override
  public void writeln(String contents) {
    inputs.push(contents);
  }

  /**
   * Gets the most recent write to the console.
   * 
   * @return returns the most recent write to the console or null if no writes
   *         exist.
   */
  public String getLastWrite() {
    if (inputs.isEmpty())
      return null;
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

  /**
   * Gets all the writes as one whole string.
   * 
   * @return Returns all the writes as a string.
   */
  public String getAllWritesAsString() {
    String[] input_arr = new String[inputs.size()];
    inputs.toArray(input_arr);

    StringBuilder ret_str = new StringBuilder();

    for (String s : input_arr) {
      ret_str.append(s + "\n");
    }

    return ret_str.toString();
  }
}
