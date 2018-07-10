package filesystem;

/**
 * The file system interface.
 *
 * @author greff
 */
public interface FileSystem {

  /**
   * Changes the current working directory to the location at path
   *
   * @param path The path of the new working directory.
   * @throws MalformedPathException Throws if the path is invalid.
   * @throws FileNotFoundException Throws if the file/directory is not found.
   */
  public void changeWorkingDir(Path path)
      throws MalformedPathException, FileNotFoundException;

  /**
   * Gets the absolute path to a directory.
   *
   * @param theDir The target directory.
   * @return Returns a string with the absolute path.
   */
  public String getAbsolutePathOfDir(Directory theDir);

  public File getFileByPath(Path path)
      throws MalformedPathException, FileNotFoundException;

  public Directory getDirByPath(Path path)
      throws MalformedPathException, FileNotFoundException;

  public Directory getWorkingDir();

  public String getWorkingDirPath();

  public Directory getRoot();
}
