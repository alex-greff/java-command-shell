package unitTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import filesystem.FileSystem;
import org.junit.Test;
import commands.CmdPwd;
import containers.CommandArgs;
import utilities.Command;

public class CmdPwdTest {
  @Test
  public void textExecute1() {
    CommandArgs args = new CommandArgs("pwd");

    Command cmd = new CmdPwd();
    String out_actual = cmd.execute(args);

    FileSystem FS = FileSystem.getInstance();

    System.out.println(out_actual);

    assertEquals(FS.getWorkingDirPath() + "\n", out_actual);
  }
}
