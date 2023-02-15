package net.codebot.application
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.control.*
import javafx.scene.control.Label
import javafx.scene.control.ToolBar
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane

import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt

        val model = Model()
        val currentView = CurrentView(model)
        val outerBox = VBox()
        outerBox.children.add(currentView)
        stage.scene = Scene(outerBox, 250.0, 150.0)
        stage.show()
    }
}