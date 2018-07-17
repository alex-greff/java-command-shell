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
   * @throws FSElementNotFoundException Throws if the file/directory is not
   * found.
   */
  void changeWorkingDir(Path path)
      throws MalformedPathException, FSElementNotFoundException;

  /**
   * Gets the absolute path to an element in the filesystem.
   *
   * @param theElement The target fselement.
   * @return Returns a string with the absolute path.
   */
  String getAbsolutePathOfFSElement(FSElement theElement);

  File getFileByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException;

  Directory getDirByPath(Path path)
      throws MalformedPathException, FSElementNotFoundException;

  Directory getWorkingDir();

  String getWorkingDirPath();

  Directory getRoot();
}
