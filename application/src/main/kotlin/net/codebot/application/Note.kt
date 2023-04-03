package net.codebot.application
import javafx.scene.web.HTMLEditor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Note(
    private var noteName: String = "New Note",
    private var data: HTMLEditor = HTMLEditor()// The contents of the note. Will be text for now, but may become a whole class later.
){

    // Properties
    private var creationDate = getCurrentDate()
    private var lastModifiedDate = creationDate
    private var caratPOS = 0
    private var winWidth = 500.0
    private var winHeight = 500.0

    //Constructor that corresponds to a Database retrieval
    constructor(name: String, text: String, crDate: String, lmDate: String, /*carat: Int,*/ width: Double, height: Double) : this() {
        noteName = name
        data = HTMLEditor()
        data.htmlText = text
        creationDate = crDate
        lastModifiedDate = lmDate
        //caratPOS = carat
        winWidth = width
        winHeight = height
    }

    //Constructor for creating a file when a name is passed
    constructor(name: String): this(){
        noteName = name
        data = HTMLEditor()
    }

    // undo / redo handler
   /* private data class State(val data: String)

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
        UndoRedoManager.saveState(data = Jsoup.parse(this.data.htmlText).text())
    }

    fun redoState() { // restores the state before undo was called
        val state = UndoRedoManager.redoState()
        Jsoup.parse(this.data.htmlText).text() = state.data
    }

    fun undoState() { // undo the last action. The previous state is saved to be able to redo the action
        val state = UndoRedoManager.undoState()
        UndoRedoManager.saveUndo(data = Jsoup.parse(this.data.htmlText).text())
        Jsoup.parse(this.data.htmlText).text = state.data
    }*/


    // Returns the noteName
    fun getNoteName(): String {
        return this.noteName
    }

    // Returns the noteData (i.e. HTMLEditor)
    fun getData(): HTMLEditor {
        return this.data
    }

    // returns note content in a string
    fun getContent(): String {
        // println(this.data.htmlText) // debugging
        // println(Jsoup.parse(this.data.htmlText).text()) // debugging
        return this.data.htmlText
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

    fun updateData(text: HTMLEditor/*, carat: Int*/) {
        println("update data: " + this.data.htmlText) // debugging
        println("new data: " + text.htmlText) // debugging

        this.data = text
        this.data.htmlText = text.htmlText
        //this.caratPOS = carat
        //parseForCode()
        //parseForLatex()
    }

    // update note name
    fun updateNoteName(noteNa: String) {
        val oldName = this.noteName
        this.noteName = noteNa
        // update database HERE
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
    fun loadData(name: String): Note{
        val dao = DataBaseDAO()
        return dao.getNote(name)
    }

    // saves all fields of note to database
    fun save() {
        val dao = DataBaseDAO()
        dao.updateNote(this)
        println("${this.getNoteName()} updated in the database")
    }

    //Adds note to Data base
    fun addToDB(note: Note){
        val dao = DataBaseDAO()
        dao.addNote(note)
    }

    //returns the position of the carat of the text area
    fun getCarat(): Int{
        return caratPOS
    }

    fun getWinSize(): Array<Double>{
        return arrayOf(this.winWidth, this.winHeight)
    }
}
