package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdPwd;
import containers.CommandArgs;
import filesystem.FileSystem;
import org.junit.Test;
import utilities.Command;

public class CmdPwdTest {

  @Test
  public void testRootDir() {
    CommandArgs args = new CommandArgs("pwd");

    Command cmd = new CmdPwd();
    String out_actual = cmd.execute(args);

    FileSystem FS = FileSystem.getInstance();

    System.out.println(out_actual);

    assertEquals(FS.getWorkingDirPath() + "\n", out_actual);
  }
}
