package filesystem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a path in the filesystem
 *
 * @author anton
 */
public class Path implements Iterable<String> {

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
      if (!segment.isEmpty()) {
        tokens.add(segment);
      } else {
        // can't have a slash after another slash or empty space
        throw new MalformedPathException();
      }
    }
  }

  /**
   * Removes the last token from the path and returns it
   *
   * @return The last token from the path
   */
  public String removeLast() {
    return this.tokens.remove(this.tokens.size() - 1);
  }

  /**
   * Removes the first token from the path and returns it
   *
   * @return The first token from the path
   */
  public String removeFirst() {
    return this.tokens.remove(this.tokens.size() - 1);
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
