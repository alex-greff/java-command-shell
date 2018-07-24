// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: ursualex
// UT Student #: 1004357199
// Author: Alexander Ursu
//
// Student2:
// UTORID user_name: greffal1
// UT Student #: 1004254497
// Author: Alexander Greff
//
// Student3:
// UTORID user_name: sankarch
// UT Student #: 1004174895
// Author: Chedy Sankar
//
// Student4:
// UTORID user_name: kamins42
// UT Student #: 1004431992
// Author: Anton Kaminsky
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import filesystem.Directory;
import filesystem.FSElement;
import filesystem.FSElementAlreadyExistsException;
import filesystem.File;
import org.junit.Test;

public class DirectoryTest {

  @Test
  public void testGetName() {
    Directory d = new Directory("myName", null);
    assertEquals("myName", d.getName());
  }

  @Test
  public void testGetParentWithoutParent() {
    Directory d = new Directory("myName", null);
    assertNull(d.getParent());
  }

  @Test
  public void testGetParentWithParent() {
    Directory parent = new Directory("myParent", null);
    Directory d = new Directory("myName", parent);
    assertEquals(parent, d.getParent());
  }

  @Test
  public void testAddChildNull() {
    Directory d = new Directory("myName", null);
    d.addChild(null);
    assertNull(d.getChildByName("someName"));
  }

  @Test
  public void testAddChildFSE() {
    Directory d = new Directory("myName", null);
    FSElement child = new FSElement("myChildName", d);
    d.addChild(child);
    assertEquals(child, d.getChildByName("myChildName"));
  }

  @Test
  public void testAddChildDirectory() {
    Directory d = new Directory("myName", null);
    Directory child = new Directory("myChildDir", d);
    d.addChild(child);
    assertEquals(child, d.getChildDirectoryByName("myChildDir"));
  }

  @Test
  public void testAddChildFile() {
    Directory d = new Directory("myName", null);
    File child = new File("myChildFile", d);
    d.addChild(child);
    assertEquals(child, d.getChildFileByName("myChildFile"));
  }

  @Test
  public void testMoveIntoNull() throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.moveInto(null);
    assertEquals(0, d.listAllChildrenNames().size());
  }

  @Test
  public void testMoveIntoDirectory() throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    Directory newChild = new Directory("myNewChildDir", null);
    d.moveInto(newChild);
    assertEquals(d, newChild.getParent());
    assertEquals(newChild, d.getChildByName("myNewChildDir"));
  }

  @Test
  public void testMoveIntoFile() throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    File newChild = new File("myNewChildFile", null);
    d.moveInto(newChild);
    assertEquals(d, newChild.getParent());
    assertEquals(newChild, d.getChildByName("myNewChildFile"));
  }

  @Test(expected = FSElementAlreadyExistsException.class)
  public void testMoveIntoAlreadyExistingChild()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    FSElement existingChild = new FSElement("myChild", null);
    d.addChild(existingChild);

    FSElement newChild = new FSElement("myChild", null);
    d.moveInto(newChild);
  }

  @Test
  public void testCreateAndAddDir() throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("newChildDir");

    Directory newDir = d.getChildDirectoryByName("newChildDir");
    assertNotNull(newDir);
    assertEquals("newChildDir", newDir.getName());
    assertEquals(d, newDir.getParent());
  }

  @Test(expected = FSElementAlreadyExistsException.class)
  public void testCreatAndAddDirAlreadyExistingChildDir()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("newChilDir");
    d.createAndAddNewDir("newChilDir");
  }

  @Test
  public void testCreateAndAddFile() throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewFile("newChildFile");
    File newFile = d.getChildFileByName("newChildFile");
    assertNotNull(newFile);
    assertEquals("newChildFile", newFile.getName());
    assertEquals("", newFile.read());
  }

  @Test
  public void testCreateAndAddFileWithInitialContents()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewFile("newChildFile", "initial contents");

    File newFile = d.getChildFileByName("newChildFile");
    assertNotNull(newFile);
    assertEquals("newChildFile", newFile.getName());
    assertEquals(d, newFile.getParent());
    assertEquals("initial contents", newFile.read());
  }

  @Test(expected = FSElementAlreadyExistsException.class)
  public void testCreateAndAddFileAlreadyExistingChildFile()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewFile("newChilFile");
    d.createAndAddNewFile("newChilFile", "initial contents");
  }

  @Test
  public void testRemoveChildByNameExistentChild() {
    Directory d = new Directory("myName", null);
    FSElement child = new FSElement("myChild", d);
    d.addChild(child);
    d.removeChildByName("myChild");
    assertNull(d.getChildByName("myChild"));
  }

  @Test
  public void testRemoveChildByNameNonExistentChild() {
    Directory d = new Directory("myName", null);
    assertNull(d.getChildByName("myChild"));
    d.removeChildByName("myChild");
    assertNull(d.getChildByName("myChild"));
  }

  @Test
  public void testGetChildByNameExistentChild()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    File childFile = d.createAndAddNewFile("myChildFile");
    assertEquals(childFile, d.getChildByName("myChildFile"));
  }

  @Test
  public void testGetChildByNameNonExistentChild() {
    Directory d = new Directory("myName", null);
    assertNull(d.getChildByName("myChildFile"));
  }

  @Test
  public void testGetChildDirectoryByNameExistentChildDirectory()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    Directory childDir = d.createAndAddNewDir("myChildDir");
    assertEquals(childDir, d.getChildDirectoryByName("myChildDir"));
  }

  @Test
  public void testGetChildDirectoryNyNameNonExistentChildDirectory() {
    Directory d = new Directory("myName", null);
    assertNull(d.getChildDirectoryByName("myChildDir"));
  }

  @Test
  public void testGetChildFileByNameExistentChildFile()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    File childFile = d.createAndAddNewFile("myChildFile");
    assertEquals(childFile, d.getChildFileByName("myChildFile"));
  }

  @Test
  public void testGetChildFileByNameNonExistentChildFile() {
    Directory d = new Directory("myName", null);
    assertNull(d.getChildFileByName("myChildFile"));
  }

  @Test
  public void testContainsChildElementExistentChildElement() {
    Directory d = new Directory("myName", null);
    d.addChild(new FSElement("myChild", d));
    assertTrue(d.containsChildElement("myChild"));
  }

  @Test
  public void testContainsChildElmentNonExistentChildElement() {
    Directory d = new Directory("myName", null);
    assertFalse(d.containsChildElement("myChild"));
  }

  @Test
  public void testContainsChildDirectoryExistentChildDirectory()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("myChildDir");
    assertTrue(d.containsChildDirectory("myChildDir"));
  }

  @Test
  public void testContainsChildDirectoryNonExistentChildDirectory() {
    Directory d = new Directory("myName", null);
    assertFalse(d.containsChildDirectory("myChildDir"));
  }

  @Test
  public void testContainsChildFileExistentChildFile()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewFile("myChildFile");
    assertTrue(d.containsChildFile("myChildFile"));
  }

  @Test
  public void testContainsChildFileNonExistentChildFile() {
    Directory d = new Directory("myName", null);
    d.addChild(new FSElement("myChild", d));
    assertFalse(d.containsChildFile("myName"));
  }

  @Test
  public void testListAllChildNamesNoChildren() {
    Directory d = new Directory("myName", null);
    assertEquals(0, d.listAllChildrenNames().size());
  }

  @Test
  public void testListAllChildNamesWithChildren() {
    Directory d = new Directory("myName", null);
    d.addChild(new FSElement("childElement", d));
    d.addChild(new Directory("childDir", d));
    d.addChild(new File("childFile", d));
    assertEquals(3, d.listAllChildrenNames().size());
  }

  @Test
  public void testListDirNamesNoDirectories() {
    Directory d = new Directory("myName", null);
    assertEquals(0, d.listDirNames().size());
  }

  @Test
  public void testListDirNamesWithDirectories()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("childDir1");
    d.createAndAddNewDir("childDir2");
    d.createAndAddNewFile("childFile");
    assertEquals(2, d.listDirNames().size());
  }

  @Test
  public void testListFileNamesWithNoFiles()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("childDir");
    assertEquals(0, d.listFileNames().size());
  }

  @Test
  public void testListFileNamesWithFiles()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("childDir");
    d.createAndAddNewFile("childFile1");
    d.createAndAddNewFile("childFile2");
    assertEquals(2, d.listFileNames().size());
  }

  @Test
  public void testNotifyRenameExistentChild() {
    Directory d = new Directory("myName", null);
    FSElement child = new FSElement("myName", d);
    d.addChild(child);
    d.notifyRename("myName", "myRenamedName");
    assertEquals(child, d.getChildByName("myRenamedName"));
  }

  @Test
  public void testNotifyRenameNonExistentChild() {
    Directory d = new Directory("myName", null);
    d.notifyRename("myName", "myRenamedName");
    assertNull(d.getChildByName("myName"));
    assertNull(d.getChildByName("myRenamedName"));
  }

  @Test
  public void testClone() {
    Directory parent = new Directory("myParent", null);
    Directory dir = new Directory("myName", parent);
    Directory child1 = new Directory("myChild1", dir);
    File<String> child2 =
        new File<>("myChild2", "child file contents", dir);
    dir.addChild(child1);
    dir.addChild(child2);

    Directory newDir = (Directory) dir.copy();
    assertEquals("myName", newDir.getName());
    assertEquals("myParent", newDir.getParent().getName());
    assertEquals("myChild1", newDir.getChildByName("myChild1").getName());
    assertEquals(newDir, newDir.getChildByName("myChild1").getParent());
    assertEquals("myChild2", newDir.getChildByName("myChild2").getName());
    assertEquals("child file contents",
                 ((File<String>) newDir.getChildByName("myChild2")).read());
  }
}
