package net.codebot.application
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.VBox
import javafx.stage.Stage


class Main : Application() {
    override fun start(stage: Stage?) {
        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt

        val darkComb = KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN)
        val lightComb = KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN)

        val model = Model()
        val currentView = CurrentView(model)
        val scene = Scene(currentView.curView, 500.0, 350.0)

        //Styling using CSS
        scene.stylesheets.add("defaultStyle.css")
        //scene.stylesheets.add("darkMode.css")

        stage?.scene = scene
        stage?.title = "NoteTaking application"
        stage?.show()

        scene.onKeyPressed = EventHandler { event ->
            if(darkComb.match(event)) {
                scene.stylesheets.clear()
                scene.stylesheets.add("darkMode.css")
            } else if (lightComb.match(event)) {
                scene.stylesheets.clear()
                scene.stylesheets.add("defaultStyle.css")
            }
        }
    }
}