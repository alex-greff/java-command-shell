package unitTests;

import static org.junit.Assert.assertTrue;

import commands.CmdMan;
import containers.CommandArgs;
import org.junit.Test;
import utilities.Command;

public class CmdManTest {

  @Test
  public void testExecute1() {
    CommandArgs args = new CommandArgs("man", new String[]{"man"});

    Command cmd = new CmdMan();
    String out_actual = cmd.execute(args);

    System.out.println(out_actual);

    assertTrue(out_actual.length() > 0);
  }

  @Test
  public void testExecute2() {
    CommandArgs args = new CommandArgs("man", new String[]{"echo"});

    Command cmd = new CmdMan();
    String out_actual = cmd.execute(args);

    System.out.println(out_actual);

    assertTrue(out_actual.length() > 0);
  }
}
