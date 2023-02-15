package net.codebot.application
import javafx.scene.control.TextArea
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



class Notes : Item{

    // Properties
    private var noteName: String = "New Note"
    private val creationDate: String = getCurrentDate()  // Corresponds to the date the Note was created, never changed post init
    private var lastModifiedDate: String = creationDate // Corresponds to the date the note was last modified
    private var data = TextArea() // The contents of the note. Will be text for now, but may become a whole class later.


    // Functions

    override fun createItem() {
        TODO("Not yet implemented")
    }
    // Returns the noteName
    fun getNoteName(): String {
        return this.noteName
    }

    // Returns the noteName
    fun getData(): String {
        return this.data.text
    }

    // Returns the lastModifiedDate
    fun getLastModifiedDate(): String {
        return lastModifiedDate
    }
    //Computes the current date and returns it as a string
    private fun getCurrentDate(): String {
        val currentDate = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.BASIC_ISO_DATE
        return currentDate.format(dateFormatter).toString()
    }

    // Updates the lastModifiedDate
    fun updateLastModifiedDate() {
        // TODO: Logic for when to call this function
        this.lastModifiedDate = getCurrentDate();
    }

    // Updates the data field
    //  Call when note is edited
    //  TODO: need a system to update text such that state of text in UI is accurately reflected in text
    fun updateData(text: String) {
        this.data.text = text
        parseForCode()
        parseForLatex()
    }


    // Deletes the current note (likely we do not need this function)
    // idea: implement delete inside the folder class
    // In theory when delete button is pressed we should go back to initial screen
    fun deleteNote() {
        // TODO
    }

    // Parses data for code blocks
    private fun parseForCode(){
        // TODO: use api or something to check for text encased in ``` ```
    }

    // Parses data for Latex
    private fun parseForLatex(){
        // TODO: use api or something to check for Latex encased in $$ $$
    }

    // restore
    fun restore(){
        // TODO: restores data to previous state?
    }

    // save
    fun save() {
        // TODO: Saves the current state of the data/note
    }
}