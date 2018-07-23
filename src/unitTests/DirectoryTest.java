package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import filesystem.Directory;
import filesystem.FSElement;
import filesystem.FSElementAlreadyExistsException;
import filesystem.File;

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
    assertTrue(d.listAllChildrenNames().size() == 0);
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
    assertTrue(d.listAllChildrenNames().size() == 0);
  }

  @Test
  public void testListAllChildNamesWithChildren() {
    Directory d = new Directory("myName", null);
    d.addChild(new FSElement("childElement", d));
    d.addChild(new Directory("childDir", d));
    d.addChild(new File("childFile", d));
    assertTrue(d.listAllChildrenNames().size() == 3);
  }

  @Test
  public void testListDirNamesNoDirectories() {
    Directory d = new Directory("myName", null);
    assertTrue(d.listDirNames().size() == 0);
  }

  @Test
  public void testListDirNamesWithDirectories()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("childDir1");
    d.createAndAddNewDir("childDir2");
    d.createAndAddNewFile("childFile");
    assertTrue(d.listDirNames().size() == 2);
  }

  @Test
  public void testListFileNamesWithNoFiles()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("childDir");
    assertTrue(d.listFileNames().size() == 0);
  }

  @Test
  public void testListFileNamesWithFiles()
      throws FSElementAlreadyExistsException {
    Directory d = new Directory("myName", null);
    d.createAndAddNewDir("childDir");
    d.createAndAddNewFile("childFile1");
    d.createAndAddNewFile("childFile2");
    assertTrue(d.listFileNames().size() == 2);
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
}
