package net.codebot.application
import net.codebot.shared.Note
import org.testng.annotations.Test
class FolderTest {
    @Test
    fun testAddDelete() {
        val folder1 = Folder()
        val file1 = Note()

        folder1.addFile(file1)

        assert(folder1.folderFiles.size == 1)

        val folder2 = Folder()
        val file2 = Note()

        folder1.addFile(file2)
        folder1.deleteFile(file2)

        assert(folder2.folderFiles.size == 0)
    }

    @Test
    fun testSearch() {
        val folder1 = Folder()
        val file1 = Note()
        file1.updateNoteName("note for cs346")
        folder1.addFile(file1)

        val result = folder1.searchFileByName("note for cs346")

        assert(result.size == 1)

        val folder2 = Folder()
        val file2 = Note()
        folder2.addFile(file2)

        val result2 = folder2.searchFileByContent("Add your text")

        assert(result2.size == 1)
    }

    @Test
    fun testSort() {
        val folder = Folder()
        val file1 = Note()
        file1.updateNoteName("note for cs346")
        val file2 = Note()
        file2.updateNoteName("cs346 note")
        folder.addFile(file1)
        folder.addFile(file2)

        folder.sortByTitle()

        assert(folder.folderFiles[0].getNoteName() == "cs346 note")
    }
}