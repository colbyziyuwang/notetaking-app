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
        transaction {
            LocalSettings.insert {
                it[noteName] = "New Note"
                it[width] = 500.0
                it[height] = 350.0
            }
        }
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

    // edit note by name



    // update the size of the document in the database
    fun updateSize(noteNa: String, wid: Double, hei: Double) {
        transaction {
            LocalSettings.update({LocalSettings.noteName eq noteNa}) {
                it[width] = wid
                it[height] = hei
            }
        }
    }

    // find the size of a note in the database
    fun getSize(noteNa: String): ArrayList<Double> {
        val result = ArrayList<Double>(2)
        var width: Double = 0.0
        var height: Double = 0.0
        transaction {
            val select = LocalSettings.select{LocalSettings.noteName eq noteNa}
            val re = select.first()
            width = re.get<Double>(LocalSettings.width)
            height = re.get<Double>(LocalSettings.height)
        }
        result[0] = width
        result[1] = height
        return result
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

}