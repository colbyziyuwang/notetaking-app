package net.codebot.application

import net.codebot.shared.Note

class Folder: Item {
    var folderName: String = "New Folder"
    val folderFiles = mutableListOf<Note>()

    override fun createItem() {
        TODO("Not yet implemented")
    }

    //FUNCTIONS

    // change name of folder
    fun changeName(name: String) {
        this.folderName = name
    }

    // add a file to the folder
    fun addFile(file: Note) {
        this.folderFiles.add(file)
    }

    // delete a file from the folder
    fun deleteFile(file: Note) {
        this.folderFiles.remove(file)
    }

    // search a file from the folder given file name
    // return list of notes
    fun searchFileByName(fileName: String): MutableList<Note> {
        val result = mutableListOf<Note>()
        for (i in this.folderFiles.indices) {
            if (this.folderFiles[i].getNoteName() == fileName) {
                result.add(this.folderFiles[i])
            }
        }
        return result
    }

    // search a file from the folder given a string
    // return a list of notes whose contents contains the given string
    fun searchFileByContent(content: String): MutableList<Note> {
        val result = mutableListOf<Note>()
        for (i in this.folderFiles.indices) {
            if (this.folderFiles[i].getData().toString().indexOf(content) != -1) {
                result.add(this.folderFiles[i])
            }
        }
        return result
    }

    // sort the folder by title
    fun sortByTitle() {
        val titleComparator = Comparator{ note1: Note, note2: Note ->
            note1.getNoteName().compareTo(note2.getNoteName())}
        this.folderFiles.sortedWith(titleComparator)
    }

    // sort the folder by creation date
    fun sortByCreationDate() {
        val dateComparator = Comparator{ note1: Note, note2: Note ->
            note1.getCreationDate().compareTo(note2.getCreationDate())}
        this.folderFiles.sortedWith(dateComparator)
    }

    // sort the folder by last modified date
    fun sortByLastModifiedDate() {
        val dateComparator = Comparator{ note1: Note, note2: Note ->
            note1.getLastModifiedDate().compareTo(note2.getLastModifiedDate())}
        this.folderFiles.sortedWith(dateComparator)
    }
}