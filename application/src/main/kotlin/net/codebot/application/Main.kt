package net.codebot.application
import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class Main : Application() {
    override fun start(stage: Stage) {

        //TODO: Replace below with a database
        // Text file for settings
        val fileName = "NoteSettings.txt"
        var file = File(fileName)

        // TODO  :need to create database

        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt

        //CSS styling artifacts
        val darkComb = KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN)
        val lightComb = KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN)

        //Model generation
        val model = Model()
        val currentView = CurrentView(model)

        //Applying window settings to load stored window size
        val content: List<String> = file.readLines()
        val width: Double = content[0].toDouble()
        val height: Double = content[1].toDouble()
        val scene = Scene(currentView.curView, width, height)

        //Styling using CSS
        scene.stylesheets.add("defaultStyle.css")
        //scene.stylesheets.add("darkMode.css")

        stage.scene = scene
        stage.title = "NoteTaking application"

        // Detects changes in the window size and updates it in the file
        // TODO: Change listeners to listen to the scene not the stage
        val stageSizeListener: ChangeListener<Number> = ChangeListener<Number> { observable, oldValue, newValue ->
            // The next line is supposed to get the name of currently displayed note
            val name = currentView.curView.getCurNote().note!!.getNoteName()
            model.updateSize(name, stage!!.getHeight(), stage.getWidth())
            print(stage!!.getHeight()) //prints to log for developer usage
            print("\n")
            print(stage!!.getWidth())
            val s1: String = "" + stage.getHeight()
            val s2: String = "" + stage.getWidth()
            val s3: String = s1 + "\n" + s2
            file.writeText(s3)
        }

        stage.widthProperty().addListener(stageSizeListener)
        stage.heightProperty().addListener(stageSizeListener)

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