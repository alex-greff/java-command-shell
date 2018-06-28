package filesystem;

import java.util.Stack;

/**
 * A singleton stack of directories used for popd and pushd commands
 * Stores the paths of directories added to it
 */
public class DirectoryStack extends Stack<String> {

  private static DirectoryStack ourInstance = null;

  private DirectoryStack() {
  }

  public static DirectoryStack getInstance() {
    if (ourInstance == null) {
      ourInstance = new DirectoryStack();
    }
    return ourInstance;
  }
}
