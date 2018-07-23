package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import commands.CmdCd;
import commands.CmdEcho;
import commands.CmdExit;
import commands.CmdFind;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.BufferedConsole;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class RedirectSystemTest {
  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() throws FSElementAlreadyExistsException {
    tc = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc, tc_err, fs);
    cmd = new CmdEcho(fs, cm);

    // Setup base file system
    Directory root = fs.getRoot();
    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    File file1 = root.createAndAddNewFile("file1");
    file1.write("file1's contents");
    dir2.createAndAddNewDir("dir3");
    File file2 = dir2.createAndAddNewFile("file2");
    file2.write("file2's contents");
    Directory dir4 = dir1.createAndAddNewDir("dir4");
    File file3 = dir4.createAndAddNewFile("file3");
    file3.write("file3's contents");
    File file4 = dir4.createAndAddNewFile("file4");
    file4.write("file4's contents");
    File file1_2 = dir4.createAndAddNewFile("file1");
    file1_2.write("file1's contents");
    dir4.createAndAddNewDir("dir1");
  }

  @Test
  public void testWriteToExistingFileRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir1/dir4"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" > ./file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("hi", file.read());
  }

  @Test
  public void testAppendToExistingFileRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir1/dir4"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> ./file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("file1's contentshi", file.read());
  }

  @Test
  public void testWriteToExistingFileAbsolutePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir1/dir4"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" > /dir1/dir4/file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("hi", file.read());
  }

  @Test
  public void testAppendToExistingFileAbsolutePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir1/dir4"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> /dir1/dir4/file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("file1's contentshi", file.read());
  }

  @Test
  public void testWriteToNonExistingFileRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir2"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" > fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir2/fileNew"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("hi", file.read());
  }

  @Test
  public void testAppendToNonExistingFileRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir2"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir2/fileNew"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("hi", file.read());
  }

  @Test
  public void testWriteToNonExistingFileAbsolutePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir2"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" > /dir2/fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir2/fileNew"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("hi", file.read());
  }

  @Test
  public void testAppendToNonExistingFileAbsolutePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir2"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> /dir2/fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir2/fileNew"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("hi", file.read());
  }

  @Test
  public void testWriteToFileInNonExistentDirectory() {
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" > /dirWrong/fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: No file/directory found",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testWriteToDirectoryRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> dir2/");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: File/directory already exists",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testWriteToDirectoryAbsolutePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir2"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> /dir2");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: File/directory already exists",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testWriteToRootRelativePath() {
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> .");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: File/directory already exists",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testWriteToRootAbsolutePath() {
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> /");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: File/directory already exists",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testWriteToCurrentDirectory()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir2"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput("echo \"hi\" >> .");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: File/directory already exists",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testWriteToFileComplexRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir1/dir4/dir1"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args =
        Parser.parseUserInput("echo \"hi\" > ./../../../dir2/fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir2/fileNew"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("hi", file.read());
  }

  @Test
  public void testAppendToFileComplexRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir1/dir4/dir1"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput(
        "echo \"hi\" >> ./../../../dir2/../dir1/dir4/./../../dir2/file2");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir2/file2"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("file2's contentshi", file.read());
  }

  @Test
  public void testWriteToDirectoryComplexRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir1/dir4/dir1"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput(
        "echo \"hi\" > ./../../../dir2/../dir1/dir4/./../../dir2");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: File/directory already exists",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testAppendToDirectoryComplexRelativePath()
      throws MalformedPathException, FSElementNotFoundException {
    fs.changeWorkingDir(new Path("dir2/dir3"));
    cmd = new CmdEcho(fs, cm);

    CommandArgs args =
        Parser.parseUserInput("echo \"hi\" > ././././../../dir2");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals("Error: File/directory already exists",
        tc_err.getAllWritesAsString());
  }

  @Test
  public void testWriteToFileWithNoOutput()
      throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdCd(fs, cm);

    CommandArgs args = Parser.parseUserInput("cd /dir1/dir4 > file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("", file.read());
  }

  @Test
  public void testAppendToFileWithNoOutput()
      throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdCd(fs, cm);

    CommandArgs args = Parser.parseUserInput("cd /dir1/dir4 >> file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("file1's contents", file.read());
  }

  @Test
  public void testWriteToNonExistentFileWithNoOutput()
      throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdCd(fs, cm);

    CommandArgs args = Parser.parseUserInput("cd /dir1/dir4 > fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/fileNew"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("", file.read());
  }

  @Test
  public void testAppendToNonExistentFileWithNoOutput()
      throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdCd(fs, cm);

    CommandArgs args = Parser.parseUserInput("cd /dir1/dir4 >> fileNew");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/fileNew"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("", file.read());
  }

  @Test
  public void testWriteToFileComplexOutput()
      throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput(
        "echo \"   some\n\tcomplex string\n\n\" > /dir1/dir4/file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("   some\n\tcomplex string\n\n", file.read());
  }

  @Test
  public void testAppendToFileComplexOutput()
      throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdEcho(fs, cm);

    CommandArgs args = Parser.parseUserInput(
        "echo \"   some\n\tcomplex string\n\n\" >> /dir1/dir4/file1");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("file1's contents   some\n\tcomplex string\n\n", file.read());
  }

  @Test
  public void testRedirectWithInvalidCommandCall() throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdEcho(fs, cm);
    CommandArgs args = Parser.parseUserInput("invalidCommand > /dir1/dir4/file1");
    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);
    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));
    
    assertSame(ExitCode.FAILURE, exitVal);
    assertEquals("file1's contents", file.read());
  }

  @Test
  public void testRedirectWithExitCommand() throws MalformedPathException, FSElementNotFoundException {
    cmd = new CmdExit(fs, cm);
    CommandArgs args = Parser.parseUserInput("exit > /dir1/dir4/file1");
    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);
    File file = fs.getFileByPath(new Path("/dir1/dir4/file1"));
    
    assertSame(ExitCode.FAILURE, exitVal);
    assertEquals("file1's contents", file.read());
  }
}
