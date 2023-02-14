package net.codebot.application

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import net.codebot.shared.SysInfo

class Main : Application() {
    override fun start(stage: Stage) {

        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt


        val Model = Model()
        val dirView = DirectoryView(Model) //View will register itself with the Model
        val noteView = NoteView(Model)
        val grid = VBox()
        grid.children.add(dirView) // JEFF QUESTION: Switching between views?
        grid.children.add(noteView)
        stage.scene = Scene(grid, 250.0, 150.0)
        stage.show()
    }
}