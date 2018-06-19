package io;

import java.util.Scanner;

/**
 * Class representing the JShell console
 *
 * @author anton
 */
public class Console implements Writable, Readable {

  private Scanner input = new Scanner(System.in);

  public Console() {
  }

  /**
   * Write text to the console
   *
   * @param contents The text to be written
   */
  @Override
  public void write(String contents) {
    System.out.print(contents);
  }

  /**
   * Read text from the console
   *
   * @return The text read from the console
   */
  @Override
  public String read() {
    return input.nextLine();
  }
}
