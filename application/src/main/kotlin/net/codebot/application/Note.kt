package net.codebot.application
import javafx.scene.control.TextArea
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Note(
    private var noteName: String = "New Note",
    private var data: TextArea = TextArea("Add your text here...")// The contents of the note. Will be text for now, but may become a whole class later.
){

    // Properties
    private val creationDate = getCurrentDate()
    private var lastModifiedDate = creationDate
    private var caratPOS = 0

    // undo / redo handler
    private data class State(val data: String)

    private object UndoRedoManager {
        private val states = mutableListOf<State>()
        private val undoneStates = mutableListOf<State>() // updated anytime undo is called

        fun saveState(data: String) {
            states.add(State(data = data))
        }

        fun saveUndo(data: String) {
            undoneStates.add(State(data = data))
        }

        fun undoState() = states.removeLast()
        fun redoState() = undoneStates.removeLast()
    }

    fun saveState() { // saves the current state of the document (should be called after space pressed)
        UndoRedoManager.saveState(data = this.data.text)
    }

    fun redoState() { // restores the state before undo was called
        val state = UndoRedoManager.redoState()
        this.data.text = state.data
    }

    fun undoState() { // undo the last action. The previous state is saved to be able to redo the action
        val state = UndoRedoManager.undoState()
        UndoRedoManager.saveUndo(data = this.data.text)
        this.data.text = state.data
    }


    // Returns the noteName
    fun getNoteName(): String {
        return this.noteName
    }

    // Returns the noteName
    fun getData(): TextArea {
        return this.data
    }

    // Returns the lastModifiedDate
    fun getLastModifiedDate(): String {
        return lastModifiedDate
    }

    // Returns the lastModifiedDate
    fun getCreationDate(): String {
        return creationDate
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

    fun updateData(text: TextArea, carat: Int) {
        this.data = text
        this.caratPOS = carat
        //parseForCode()
        //parseForLatex()
    }

    // update note name
    fun updateNoteName(noteNa: String) {
        val oldName = this.noteName
        this.noteName = noteNa
        // update database
        transaction {
            LocalSettings.update({LocalSettings.noteName eq oldName}) {
                it[noteName] = noteNa
            }
        }
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

    // loads saved version of data
    fun loadData(){
        val file = File("NoteSave.txt")
        var content:String = file.readText()
        data.text = content
    }

    // save
    fun save() {
        val fileName = "NoteSave.txt"
        var file = File(fileName)
        file.writeText(data.text) //saving current state of text to file
    }

    //returns the position of the carat of the text area
    fun getCarat(): Int{
        return caratPOS
    }
}
