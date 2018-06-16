package filesystem;

public class FileSystem {

  private static FileSystem ourInstance = new FileSystem();

  public static FileSystem getInstance() {
    return ourInstance;
  }

  private FileSystem() {
  }
}
