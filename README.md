# Java Command Shell
A simple command shell made in Java utilizing a mock (in RAM) file system. 

## Getting Started

### Build File
An ant build file called **build2B.xml** is included in the root directory of the repository. Use this to compile and run the project in no time.

### IDE
Import the repository into an IDE such as Eclipse or IntelliJ to run and compile it from there.

## Implemented Commands

**exit** - Exits the shell.

**mkdir DIR...** - Creates directories in all given directory locations.

**cd DIR** - Changes the current working directory to the given directory.

**ls [-R] [PATH]** - Lists the files/directories in the given paths. -R lists recursively.

**pwd** - Prints the current working directory path.

**mv OLD_PATH NEW_PATH** - Moves the file/directory to a new location.

**cp OLD_PATH NEW_PATH** - Copies the file/directory to a new location

**cat FILE** - Prints the contents of the file.

**curl URL** - Prints the contents of a web URL.

**echo STRING** - Prints the string.

**man CMD_NAME** - Displays a brief documentation for the given command.

**pushd DIR** - Pushes the current working directory to a stack and sets the directory as the new working directory.

**popd** - Pops the latest directory off the stack and sets it to be the working directory.

**history [NUMBER]** - Displays NUMBERth most recent console inputs or 10 if NUMBER is not specified.

**!NUMBER** - Attempts to execute the NUMBERth most recent console input.

**grep [-R] REGEX PATH...** - Searches the given file(s) contents for the given regular expression. -R searches any given directories recursively.


## Command Redirection
Alongside the implemented commands, a redirection system was also implemented allowing for the standard output of commands to be written/appended to files.

Note: if a non-existent file location is specified then a new file will be created.

### Writing to Files

* **COMMAND > FILE**

Example:
```
echo "Hello World" > myFile // Writes "Hello World" to myFile
```

### Appending to Files

* **COMMAND >> FILE**

Example:
```
mkdir myFile // Creates myFile
ls >> myFile // Appends "myFile" to myFile
```
