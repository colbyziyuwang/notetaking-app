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
        val outerBox = VBox()
        outerBox.children.add(currentView.curView)
        stage.scene = Scene(outerBox, 400.0, 250.0)
        stage.title = "why isnt this working"
        stage.show()
    }
}