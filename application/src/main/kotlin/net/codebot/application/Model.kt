package net.codebot.application
import javafx.scene.control.TextArea
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.io.File

class Model {

    //All views of the model
    private val views: ArrayList<IView> = ArrayList()

    // Views use this function to register themselves with the Model, and then get updates and states from the model.
    fun addView(view: IView){
       views.add(view)
       //view.updateView()
    }

    // Notify all views that data has been changed
    fun notifyObservers(){
       for (view in views){
           view.updateView()
       }
    }

    private var notes = ArrayList<Note>()
    private var currentNote: Note? = null

    init {
        val dao = DataBaseDAO()
        dao.deleteAllNotes() //emptying database
        // add some notes for debugging
        createNote("Note 1")
        addDataToDB("Note 1")
        createNote("Note 2")
        addDataToDB("Note 2")
        createNote("Note 3")
        addDataToDB("Note 3")
    }

    //returns the items of the model (For demo1 this is the one note)
    fun getItems(): ArrayList<Note> {
        return notes
    }

    fun createNote(name: String){
        var note = Note(name)
        notes.add(note)
        notifyObservers()
    }

    fun updateData(note: String, data: TextArea,  caratPOS: Int){
        // updates the data of the note for now
        for (n in notes){
            if (n.getNoteName() == note){
                n.updateData(data, caratPOS)
                notifyObservers()
                return //Debugging may need to be replaced
            }
        }
        notifyObservers()
    }

    // delete note by name
    fun deleteNoteByName(name: String){
        for (note in notes){
            if (note.getNoteName() == name){
                notes.remove(note)
                notifyObservers()
                return
            }
        }
    }


    //returns the position of the carat
    fun getCaratPOS(noteName: String): Int {
        var caratPOS = 0
        for(n in notes) {
            if (noteName == n.getNoteName()) {
                caratPOS = n.getCarat()
            }
        }

        return caratPOS
    }



    //will invoke the save function of the note
    fun saveData(noteName: String){
        for(n in notes){
            if (noteName == n.getNoteName()){
                n.save()
                return
            }
        }
    }

    //Adds note data to DB
    fun addDataToDB(name: String){
        for(n in notes) {
            if(name == n.getNoteName()){
                n.addToDB(n)
            }
        }
    }

    fun loadData(noteName: String){
        for(n in notes){
            if (noteName == n.getNoteName()){
                n.loadData(noteName)
                notifyObservers()
                return
            }
        }

    }

    //Sets the current note
    fun setCurrentNote(note: Note?){
        currentNote = getNote(note!!.getNoteName())
    }

    //Gets the current note
    fun getCurrentNote(): Note? {
        return currentNote
    }

    //returns the requisite note from the DB
    fun getNote(name: String): Note{
        val dao = DataBaseDAO()
        return dao.getNote(name)
    }

}