# Java Command Shell
A simple command shell made in Java utilizing a mock (in RAM) file system. 

## Getting Started

### Build File
An ant build file called **build2B.xml** is included in the root directory of the repository. Use this to compile and run the project in no time.

### IDE
Import the repository into an IDE such as Eclipse or IntelliJ to run and compile it from there.

## Implemented Commands

**exit** - Exits the shell.

**mkdir DIR...** - Creates all directory parameters.

**cd DIR** - Changes the current working directory.

**ls [-R] [PATH]** - Lists the files/directories in the given paths. -R lists recursively.

**pwd** - Prints the current working directory.

**mv OLD_PATH NEW_PATH** - moves the file/directory to a new location.

**cp OLD_PATH NEW_PATH** - copies the file/directory to a new location

**cat FILE** - prints the contents of the file.

**curl URL** - prints the contents of a web URL.

**echo STRING** - prints the string.

**man CMD_NAME** - displays a brief documentation for the given command.

**pushd DIR** - pushes the current working directory to a stack and sets the directory as the new working directory.

**popd** - pops the latest directory off the stack and sets it to be the working directory.

**history [NUMBER]** - displays NUMBERth most recent console inputs or 10 if NUMBER is not specified.

**!NUMBER** - attempts to execute the NUMBERth most recent command.

**grep [-R] REGEX PATH...** - searches the given file(s) for the given regular expression. -R searches the given directories recursively.


## Command Redirection
Alongside the implemented commands, a redirection system was also implemented allowing for the standard output of commands to be written/appended to files.

Note: if a non-existent file location is specified then a new file will be created.

**Writing to Files:**

* **COMMAND > FILE**

Example:
```
echo "Hello World" > myFile // Writes "Hello World"
```

**Appending to Files:**

* **COMMAND >> FILE**

Example:
```
mkdir myFile
ls >> myFile // Appends "myFile"
```
