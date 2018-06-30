package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdCat;
import containers.CommandArgs;
import io.Console;
import io.ErrorConsole;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdCatTest {

  private Console out = Console.getInstance();
  private ErrorConsole errOut = ErrorConsole.getInstance();
  private Command cmd = new CmdCat();

}
