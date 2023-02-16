package net.codebot.application
import javafx.scene.layout.VBox

class CurrentView (private val model: Model){
        private val noteView = NoteView(model)
        var curView = VBox(noteView)
}