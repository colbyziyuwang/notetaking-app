package net.codebot.application

import net.codebot.shared.Model

class ViewController (var model: Model) {

    fun createNote(name: String){
        var new_name = name
        var noteList = model.getItems()
        // check for duplicates
        var dup = false
        var tally = 0
        for (item in noteList) {
            var na = item.getNoteName()
            val pos = na.lastIndexOf(' ')
            na = na.substring(0, pos)
            if (na == name) {
                dup = true
                tally = tally + 1
            }
        }

        if (!dup) {
            new_name = new_name + " (0)"
        } else {
            new_name = new_name + " (" + tally + ")"
        }
        var note = Note(new_name)
        notes.add(note)
        addDataToDB(note)
        notifyObservers()

    }
}