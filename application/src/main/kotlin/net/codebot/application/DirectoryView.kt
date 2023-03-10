package net.codebot.application

import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import kotlin.Double.Companion.POSITIVE_INFINITY


internal class DirectoryView(private val model: Model, private var curNote: CurNote) : VBox(), IView{
    init {
        model.addView(this)
        updateView()
    }

    inner class deleteButton(noteName: String): Button("Delete") {
        init {
            setOnAction {
                model.deleteNoteByName(noteName)
            }
        }
    }

    // edit button
    inner class editButton(note: Note): Button("Edit") {
        init {
            setOnAction {
                // go into note view
                curNote.note = note
                model.notifyObservers()
                println("Edit button pressed wiht Note name: ${note.getNoteName()}")
            }
        }
    }

    override fun updateView() {
        children.clear()
        // get list of nodes from model
        val notes = model.getItems()
        for(i in notes.indices){
            if (i != 0) {
                children.add(Separator(Orientation.HORIZONTAL))
            }
            children.add(HBox(
                Label(notes[i].getNoteName()).apply {
                    HBox.setHgrow(this, Priority.ALWAYS)
                    maxWidth = POSITIVE_INFINITY},
                Label(notes[i].getCreationDate()),
                Label(notes[i].getLastModifiedDate()),
                editButton(notes[i]),
                deleteButton(notes[i].getNoteName())
            ) .apply {
                // add some padding
                padding = javafx.geometry.Insets(10.0, 10.0, 10.0, 10.0)
                children.forEach() {
                    // add some padding to each child
                    HBox.setMargin(it, javafx.geometry.Insets(0.0, 10.0, 0.0, 0.0))
                    alignment = javafx.geometry.Pos.CENTER_LEFT
                }
            })

        }
    }
}


