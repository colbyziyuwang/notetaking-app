package net.codebot.application

class Model {

    //All views of the model
    private val views: ArrayList<IView> = ArrayList()

    // Views use this function to register themselves with the Model, and then get updates and states from the model.
    fun addView(view: IView){
       views.add(view)
       view.updateView()
    }

    // Notify all views that data has been changed
    private fun notifyObservers(){
       for (view in views){
           view.updateView()
       }
    }


    // the list of notes and folders alike
    private val items: MutableList<Item> = TODO()
    // Manage Folder logic below
    // Notes logic here





}