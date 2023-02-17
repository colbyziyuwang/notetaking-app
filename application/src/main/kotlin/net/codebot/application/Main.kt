package net.codebot.application
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage?) {
        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt

        val model = Model()
        val currentView = CurrentView(model)
        stage?.scene = Scene(currentView.curView, 500.0, 350.0)
        stage?.title = "NoteTaking application"
        stage?.show()
    }
}