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


class Main : Application() {
    override fun start(stage: Stage?) {
        // need to create database

        // create database if it doesn't already exist
        Database.connect("jdbc:sqlite:localSettings.db")

        transaction {
            // create a table that reflects our table structurez
            SchemaUtils.create(LocalSettings)

            // remove previous values
            LocalSettings.deleteAll()

            //LocalSettings.insert {
                //it[noteName] = "New Note"
                //it[width] = 500
                //it[height] = 350
           //}
        }

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

        // detect size change
        val stageSizeListener: ChangeListener<Number> = ChangeListener<Number> { observable, oldValue, newValue ->
            // The next line is supposed to get the name of currently displayed note
            val name = model.getItems().getNoteName()
            model.updateSize(name, stage!!.getHeight(), stage!!.getWidth())
            print(stage!!.getHeight())
            print("\n")
            print(stage!!.getWidth())
        }

        stage?.widthProperty()?.addListener(stageSizeListener)
        stage?.heightProperty()?.addListener(stageSizeListener)

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