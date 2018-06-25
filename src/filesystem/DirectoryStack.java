package filesystem;

import java.util.Stack;

/**
 * A singleton stack of directories used for popd and pushd commands
 */
public class DirectoryStack extends Stack<Directory> {

  private static DirectoryStack ourInstance = new DirectoryStack();

  private DirectoryStack() {
  }

  public static DirectoryStack getInstance() {
    return ourInstance;
  }
}
