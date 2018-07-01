package unitTests;

import static org.junit.Assert.assertEquals;
import commands.CmdHistory;
import containers.CommandArgs;
import driver.JShell;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import io.Console;
import io.ErrorConsole;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdHistoryTest {

  @BeforeClass
  public static void setup() {
    ArrayList<String> hist = JShell.getHistory();
  }

  @Test
  public void testHistory1() {
    String[] params = new String[1];
    params[0] = "1";
    CommandArgs args = new CommandArgs("history", params);
    Command cmd = new CmdHistory();

    ExitCode exc =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());
    assertEquals(exc, ExitCode.SUCCESS);
  }
}
