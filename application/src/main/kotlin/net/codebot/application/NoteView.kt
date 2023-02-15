package net.codebot.application

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToolBar
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage

class NoteView (private val model: Model) : VBox(), IView{
    override fun updateView(stage: Stage) {
        // get the items
        val note: Notes = Model.getItems()

        // display the note
        val toolbar = ToolBar()
        val button = Button("Create")
        button.font = Font("Helvetica", 11.0)
        toolbar.items.add(button)

        val button2 = Button("Edit")
        button.font = Font("Helvetica", 11.0)
        toolbar.items.add(button2)

        val button3 = Button("Delete")
        button.font = Font("Helvetica", 11.0)
        toolbar.items.add(button3)


        val text = Text(note.getData())

        text.font = Font("Helvetica", 12.0)
        text.wrappingWidth = 350.0
        val scroll = ScrollPane()
        scroll.content = text
        stage.scene = Scene(VBox(toolbar, scroll))

        stage.title = (note.getNoteName() + note.getLastModifiedDate())
        stage.show()
    }

    init {
        //somehow get items from model.data
        data.setOnKeyPressed {
            if key == spacebar
        }
    }
}