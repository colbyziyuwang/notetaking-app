package net.codebot.application

class Folder: Item {
    var folderName: String = "New Folder"
    val folderFiles = mutableListOf<Notes>()

    override fun createItem() {
        TODO("Not yet implemented")
    }

    //FUNCTIONS

    // change name of folder
    fun changeName(name: String) {
        this.folderName = name
    }

    // add a file to the folder
    fun addFile(file: Notes) {
        this.folderFiles.add(file)
    }

    // delete a file from the folder
    fun deleteFile(file: Notes) {
        this.folderFiles.remove(file)
    }

    // search a file from the folder given file name
    // return list of notes
    fun searchFileByName(fileName: String): MutableList<Notes> {
        val result = mutableListOf<Notes>()
        for (i in this.folderFiles.indices) {
            if (this.folderFiles[i].getNoteName() == fileName) {
                result.add(this.folderFiles[i])
            }
        }
        return result
    }

    // search a file from the folder given a string
    // return a list of notes whose contents contains the given string
    fun searchFileByContent(content: String): MutableList<Notes> {
        val result = mutableListOf<Notes>()
        for (i in this.folderFiles.indices) {
            if (this.folderFiles[i].getData().toString().indexOf(content) != -1) {
                result.add(this.folderFiles[i])
            }
        }
        return result
    }

    // sort the folder by title
    fun sortByTitle() {
        val titleComparator = Comparator{ note1: Notes, note2: Notes ->
            note1.getNoteName().compareTo(note2.getNoteName())}
        this.folderFiles.sortedWith(titleComparator)
    }

    // sort the folder by creation date
    fun sortByCreationDate() {
        val dateComparator = Comparator{ note1: Notes, note2: Notes ->
            note1.getCreationDate().compareTo(note2.getCreationDate())}
        this.folderFiles.sortedWith(dateComparator)
    }

    // sort the folder by last modified date
    fun sortByLastModifiedDate() {
        val dateComparator = Comparator{ note1: Notes, note2: Notes ->
            note1.getLastModifiedDate().compareTo(note2.getLastModifiedDate())}
        this.folderFiles.sortedWith(dateComparator)
    }
}