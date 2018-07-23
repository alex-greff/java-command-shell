package unitTests;

import filesystem.*;
import io.*;
import utilities.*;
import containers.*;
import commands.*;
import org.junit.Before;
import org.junit.Test;
import driver.JShell;

import java.util.ArrayList.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class JShellTest {
  JShell shelly;
  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;

  @Before
  public void setup(){
    shelly = new JShell();
    tc = new BufferedConsole<String>();
    tc_err = new BufferedConsole<String>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc, tc_err, fs);
  }

  @Test
  public void testStopRunning(){
    JShell.exit();
    assertFalse(JShell.getRunning());
  }



}
