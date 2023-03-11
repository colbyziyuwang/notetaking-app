package net.codebot.application
import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.DriverManager

class Main : Application() {
    override fun start(stage: Stage) {

        //TODO: Replace below with a database


        // TODO  :need to create database
        //Establishing Database connection
        Database.connect("jdbc:sqlite:noteData.db", "org.sqlite.JDBC")

        transaction {
            SchemaUtils.create(DataBase)
            DataBase.insert { newRow ->
                newRow[name] = "Test File"
                newRow[content] = "This is testing text content for my test file"
                newRow[caratPosition] = 69
                newRow[creationDate] = "yo mama"
                newRow[lastModifiedDate] = "joe mama"
                newRow[winWidth] = 700.0
                newRow[winHeight] = 900.0
            }
        }

        //accessing stuff from database//DEBUGGING
        transaction {
            val result = DataBase.select { DataBase.noteID eq 2 }.toList()

            for (row in result){
                println("Note Name: ${row[DataBase.name]}, CreationDate: ${row[DataBase.creationDate]}")
            }
        }



        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt

        //CSS styling artifacts
        val darkComb = KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN)
        val lightComb = KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN)

        //Model generation
        val model = Model()
        val currentView = CurrentView(model)

        //default height and width values
        var width = 500.0
        var height = 500.0

        //accessing stuff from database //DEBUGGING
        transaction {
            val result = DataBase.select { DataBase.noteID eq 2 }.toList()

            for (row in result){
                width = row[DataBase.winWidth]
                height = row[DataBase.winHeight]
            }
        }

        //Applying window settings to load stored window size
        val scene = Scene(currentView.curView, width, height)

        //Default mode is light mode
        scene.stylesheets.add("defaultStyle.css")
        //scene.stylesheets.add("darkMode.css")

        stage.scene = scene
        stage.title = "NoteTaking application"


        stage.show()

        //Toggling between dark mode and lightmode
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

// USEFUL FOR LATER detecting scene size changes, however too difficult to work in rn
// Detects changes in the window size and updates it in the file
//        // TODO: Change listeners to listen to the scene not the stage
//        val stageSizeListener: ChangeListener<Number> = ChangeListener<Number> { observable, oldValue, newValue ->
//            // The next line is supposed to get the name of currently displayed note
//            val name = currentView.curView.getCurNote().note!!.getNoteName()
//            model.updateSize(name, stage!!.getHeight(), stage.getWidth())
//            print(stage!!.getHeight()) //prints to log for developer usage
//            print("\n")
//            print(stage!!.getWidth())
//            val s1: String = "" + stage.getHeight()
//            val s2: String = "" + stage.getWidth()
//            val s3: String = s1 + "\n" + s2
//            file.writeText(s3)
//        }
//
//        stage.widthProperty().addListener(stageSizeListener)
//        stage.heightProperty().addListener(stageSizeListener)