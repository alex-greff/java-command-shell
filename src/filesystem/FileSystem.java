package filesystem;

/**
 * Manages files, directories and the current working directory.
 */
public class FileSystem {

  private static FileSystem ourInstance = new FileSystem();
  private Directory root = new Directory("root");
  private Directory workingDir = root;

  private FileSystem() {
  }

  /**
   * Get the singleton instance of the filesystem
   *
   * @return This filesystem instance
   */
  public static FileSystem getInstance() {
    return ourInstance;
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

  /**
   * Provides current working directory to the caller
   *
   * @return The current working directory object
   */
  public Directory getWorkingDir() {
    return workingDir;
  }

  /**
   * Provides file located at given path to the caller
   *
   * @param path The path of the wanted file, can be absolute or
   * relative. Absolute path must start with / indicating root
   * directory.
   * @return The file located at the path
   */
  public File getFileByPath(String path) {
    return new File("none", "Not implemented");
  }

  /**
   * Provides directory located at given path to the caller
   *
   * @param path The path of the wanted file, can be absolute or
   * relative. Absolute path must start with / indicating root
   * directory.
   * @return The directory located at the path
   */
  public Directory getDirByPath(String path) {
    return new Directory("none");
  }

}
