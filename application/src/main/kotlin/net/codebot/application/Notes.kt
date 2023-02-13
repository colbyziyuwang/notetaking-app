package net.codebot.application
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// some of my opinion: maybe we should put create note, edit note and delete note inside this note class
// instead of putting them in a separate class (when you press the button create then a new note should
// be created using its constructor); similar ideas for edit and delete note

class Notes {
    // fields
    var noteName: String = "note"
    var creationDate: String = ""
    var lastModifiedDate: String = "" // to make lives easier let us use 2 dates
    var data: String = "" // text for now

    init {
        // we want to initialize both creationDate and lastModifiedDate when note is created
        val current = LocalDateTime.now() // current time: e.g. 2023/02/12

        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted: String = current.format(formatter).toString() // convert to string (20230212)

        creationDate = formatted
        lastModifiedDate = formatted
    }

    // modified the field lastModifiedDate
    fun modifiedDate(date: String) {
        this.lastModifiedDate = date
    }

    // update the data field (call this function when you edit note)
    fun updateData(text: String) {
        this.data = text
    }

    // delete the note (likely we do not need this function)
    // idea: implement delete inside the folder class
    // In theory when delete button is pressed we should go back to initial screen
    fun deleteNote() {

    }

    // other functions to be added

    // parseForCOde

    // parseForLatex

    // restore

    // save
}