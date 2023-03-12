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
        // add some notes for debugging
        notes.add(Note("Note 1", TextArea("This is the first note")))
        notes.add(Note("Note 2", TextArea("This is the second note")))
        notes.add(Note("Note 3", TextArea("This is the third note")))

    }

    //returns the items of the model (For demo1 this is the one note)
    fun getItems(): ArrayList<Note> {
        return notes
    }

    fun createNote(name: String){ //Redundant?
        notes.add(Note(name))
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


    fun createSaveFile(){
        val fileName = "NoteSave.txt"
        var file = File(fileName)
        file.writeText("Add your text here...")
    }

    fun loadData(noteName: String){
        for(n in notes){
            if (noteName == n.getNoteName()){
                n.loadData()
                notifyObservers()
                return
            }
        }

    }

    //Sets the current note
    fun setCurrentNote(note: Note?){
        currentNote = note
    }

    //Gets the current note
    fun getCurrentNote(): Note? {
        return currentNote
    }

}