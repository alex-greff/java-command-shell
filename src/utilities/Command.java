package utilities;

import containers.CommandArgs;

public abstract class Command {
  public abstract String execute(CommandArgs args);
  public abstract String getName();
}
