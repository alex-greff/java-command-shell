package unitTests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import commands.CmdEcho;
import containers.CommandArgs;
import utilities.Command;

public class CmdEchoTest {
  @Test
  public void testExecute1() {
    CommandArgs args =
        new CommandArgs("echo", new String[] {"nice sentence you got there"});

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    System.out.println(out_actual);

    assertEquals(true, out_actual.length() > 0);
  }
}
