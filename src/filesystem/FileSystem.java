package filesystem;

/**
 * Manages files, directories and the current working directory.
 */
public class FileSystem {

  private static FileSystem ourInstance = new FileSystem();
  private Directory root = new Directory("root");
  private Directory workingDir = root;

  /**
   * Get the singleton instance of the filesystem
   *
   * @return This filesystem instance
   */
  public static FileSystem getInstance() {
    return ourInstance;
  }

  private FileSystem() {
  }

  /**
   * Change the working dir to the dir given by the path
   *
   * @param path A path to the dir to change to
   */
  public void changeWorkingDir(String path) {
  }

  /**
   * Add a new file to the working directory
   *
   * @param theFile The file to add to the working directory
   */
  public void addFile(File theFile) {
  }

  /**
   * Add a new directory to the working directory
   *
   * @param theDir The directory to add to the working directory
   */
  public void addDir(Directory theDir) {
  }
}
