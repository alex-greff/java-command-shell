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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a path in the filesystem
 *
 * @author anton
 */
public class Path implements Iterable<String> {

  /**
   * The storage for all the tokens.
   */
  private ArrayList<String> tokens = new ArrayList<>();

  /**
   * Creates a new path given a path string
   *
   * @param pathString A string representing a path
   */
  public Path(String pathString) throws MalformedPathException {
    // if it's an absolute path add the root dir to the token list
    if (pathString.equals("/")) {
      tokens.add("/");
    } else if (pathString.startsWith("/")) {
      tokens.add("/");
      pathString = pathString.substring(1);
    }
    for (String segment : pathString.split("/")) {
      // Make sure segment is not empty or is a bunch of dots (3 or more)
      if (!segment.isEmpty() && !segment.matches("\\.{3,}")) {
        tokens.add(segment);
      } else {
        // can't have a slash after another slash or empty space
        throw new MalformedPathException();
      }
    }
  }

  /**
   * Copy constructor for path objects
   *
   * @param basePath The path to copy
   */
  public Path(Path basePath) {
    this.tokens = new ArrayList<>(basePath.tokens);
  }

  /**
   * Gets the number of tokens in the path
   *
   * @return Returns the number of tokens in the path
   */
  public int getNumberOfTokens() {
    return this.tokens.size();
  }

  /**
   * Removes the last token from the path and returns it.
   *
   * @return Returns the last token from the path or null if path is empty.
   */
  public String removeLast() {
    if (isEmpty()) {
      return null;
    }
    return this.tokens.remove(this.tokens.size() - 1);
  }

  /**
   * Removes the first token from the path and returns it.
   *
   * @return Returns the first token from the path or null if path is empty.
   */
  public String removeFirst() {
    if (isEmpty()) {
      return null;
    }
    return this.tokens.remove(0);
  }

  /**
   * Get the element at index n in the path
   *
   * @return the nth element of the path
   */
  public String get(int n) {
    return this.tokens.get(n);
  }

  /**
   * The iterator override for path.
   */
  @Override
  public Iterator<String> iterator() {
    return this.tokens.iterator();
  }

  public boolean isEmpty() {
    return this.tokens.isEmpty();
  }
}
