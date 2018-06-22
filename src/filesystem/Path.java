package filesystem;

import java.util.ArrayList;

/**
 * Represents a path in the filesystem
 *
 * @author anton
 */
public class Path {

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
   * Returns the next path token and removes it
   * @return the next path token
   */
  public String getNextToken() {
    return tokens.remove(0);
  }

  /**
   * Query empty status of the tokens
   * @return True if no more tokens false otherwise
   */
  public boolean isEmpty() {
    return this.tokens.isEmpty();
  }
}
