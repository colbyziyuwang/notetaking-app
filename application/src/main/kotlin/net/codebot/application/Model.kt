package net.codebot.application

import javafx.scene.control.TextArea

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
        notifyObservers()
    }

    fun updateData(data: TextArea){
        note.updateData(data)
        notifyObservers()
    }







}