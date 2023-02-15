package net.codebot.application
import javafx.scene.control.TextArea
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



class Notes : Item{

    // Properties
    private var noteName: String = "New Note"
    private val creationDate: String = getCurrentDate()  // Corresponds to the date the Note was created, never changed post init
    private var lastModifiedDate: String = creationDate // Corresponds to the date the note was last modified
    private var data: TextArea = TextArea("Add your text here...")// The contents of the note. Will be text for now, but may become a whole class later.



    // undo / redo handler
    private data class State(val data: TextArea)

    private object UndoRedoManager {
        private val states = mutableListOf<State>()
        private val undoneStates = mutableListOf<State>() // updated anytime undo is called

        fun saveState(data: TextArea) {
            states.add(State(data = data))
        }

        fun saveUndo(data: TextArea) {
            undoneStates.add(State(data = data))
        }

        fun undoState() = states.last()
        fun redoState() = undoneStates.last()
    }

    fun saveState() { // saves the current state of the document (should be called after space pressed)
        UndoRedoManager.saveState(data = this.data)
    }

    fun redoState() { // restores the state before undo was called
        val state = UndoRedoManager.redoState()
        this.data = state.data
    }

    fun undoState() { // undo the last action. The previous state is saved to be able to redo the action
        val state = UndoRedoManager.undoState()
        UndoRedoManager.saveUndo(data = this.data)
        this.data = state.data
    }

    // Functions

    override fun createItem() {
        TODO("Not yet implemented")
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

    fun updateData(text: TextArea) {
        this.data = text
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