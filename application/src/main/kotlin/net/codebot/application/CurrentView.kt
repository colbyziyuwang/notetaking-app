package net.codebot.application

import net.codebot.shared.Model

class CurrentView (private val model: Model, private val controller: ViewController){
        private val noteView = NoteView(model, controller)
        var curView = noteView
}