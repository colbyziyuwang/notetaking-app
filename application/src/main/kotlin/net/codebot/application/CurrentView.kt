package net.codebot.application
import javafx.scene.layout.VBox

class CurrentView (private val model: Model){
        private var noteView: NoteView = NoteView(model)
        var curView: VBox = VBox()

        init {
            curView.children.add(noteView)
        }

}