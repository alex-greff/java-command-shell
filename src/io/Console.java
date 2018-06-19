package io;

/**
 * Class representing the JShell console
 *
 * @author anton
 */
public class Console implements Writable {

  /**
   * Write text to the console
   *
   * @param contents The text to be written
   */
  @Override
  public void write(String contents) {
    System.out.print(contents);
  }
}
