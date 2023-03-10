package net.codebot.application

class CurrentView (private val model: Model){
        private val noteView = NoteView(model)
        var curView = noteView
}