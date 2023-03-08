package net.codebot.application

import javafx.scene.control.TextArea
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class Model {

    //All views of the model
    private val views: ArrayList<IView> = ArrayList()

    // Views use this function to register themselves with the Model, and then get updates and states from the model.
    fun addView(view: IView){
       views.add(view)
       //view.updateView()
    }

    // Notify all views that data has been changed
    private fun notifyObservers(){

       for (view in views){
           view.updateView()
       }
    }


    private var note = Notes()//For now Model will have one field that is the note

    //returns the items of the model (For demo1 this is the one note)
    fun getItems():Notes {
        return note
    }

    fun createNote(){ //Redundant?
        note = Notes()
        // insert into database
        transaction {
            LocalSettings.insert {
                it[noteName] = "New Note"
                it[width] = 500.0
                it[height] = 350.0
            }
        }
        notifyObservers()
    }

    fun updateData(data: TextArea){
        note.updateData(data)
        notifyObservers()
    }

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
}