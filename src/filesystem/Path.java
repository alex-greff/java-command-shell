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
    if (pathString.startsWith("/")) {
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
   * @return The last token from the path
   */
  public String removeLast() {
    return this.tokens.remove(this.tokens.size() - 1);
  }

  @Override
  public Iterator<String> iterator() {
    return this.tokens.iterator();
  }
}
