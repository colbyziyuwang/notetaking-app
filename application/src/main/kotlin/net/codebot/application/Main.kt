package net.codebot.application

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {

        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt



        val model = Model()
        val currentView = CurrentView(model)
/*        val dirView = DirectoryView(model) //View will register itself with the Model
        val noteView = NoteView(model)*/
        val grid = VBox()
        grid.children.add(dirView) // JEFF QUESTION: Switching between views?
        grid.children.add(noteView)
        stage.scene = Scene(grid, 250.0, 150.0)
        stage.show()
    }
}