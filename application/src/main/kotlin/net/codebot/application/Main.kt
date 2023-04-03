package net.codebot.application
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import java.io.File

class Main : Application() {
    override fun start(stage: Stage) {

        // files for local settings
        val fileName = "NoteSettings.txt"
        val file = File(fileName)

        // load settings
        val content: List<String> = file.readLines()
        val wid: Double = content[0].toDouble()
        val hei: Double = content[1].toDouble()

        // files for stage positions
        val fileName2 = "StagePosition.txt"
        val file2 = File(fileName2)

        // load settings
        val content2: List<String> = file2.readLines()
        val x: Double = content2[0].toDouble()
        val y: Double = content2[1].toDouble()

        // files for whether stage is dark or light
        val fileName3 = "Mode.txt"
        val file3 = File(fileName3)

        // load settings
        val content3: List<String> = file3.readLines()
        val mode: String = content3[0]

        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt

        //CSS styling artifacts
        val darkComb = KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN)
        val lightComb = KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN)

        //Model generation
        val model = Model()
        val currentView = CurrentView(model)

        //default height and width values, put into variables for easy changing later
        //var wid = 500.0
        //var hei = 500.0

        //Applying window settings to load stored window size
        val scene = Scene(currentView.curView, wid, hei)

        // Default mode is light mode
        if (mode == "Light") {
            scene.stylesheets.add("defaultStyle.css")
        } else {
            scene.stylesheets.add("darkMode.css")
        }

        stage.scene = scene
        stage.title = "NoteTaking application"

        stage.x = x
        stage.y = y

        stage.show()

        // Toggling between dark mode and light mode
        scene.onKeyPressed = EventHandler { event ->
            if(darkComb.match(event)) {
                scene.stylesheets.clear()
                scene.stylesheets.add("darkMode.css")
                file3.writeText("Dark")
            } else if (lightComb.match(event)) {
                scene.stylesheets.clear()
                scene.stylesheets.add("defaultStyle.css")
                file3.writeText("Light")
            }
        }

        // track scene size changes
        // Add listener to scene width property

        scene.widthProperty().addListener { _, oldWidth, newWidth ->
            // Do something when the scene width changes
            println("Scene width changed from $oldWidth to $newWidth")

            // Read the contents of the file
            val fileContents = file.readText().lines().toMutableList()

            // Replace the first line with the new width value
            fileContents[0] = "$newWidth"

            // Write the updated contents back to the file
            file.writeText(fileContents.joinToString("\n"))
        }


        // Add listener to scene height property
        scene.heightProperty().addListener { _, oldHeight, newHeight ->
            // Do something when the scene height changes
            println("Scene height changed from $oldHeight to $newHeight")

            val fileContents = file.readText().lines().toMutableList()

            // Replace the first line with the new width value
            fileContents[1] = "$newHeight"

            // Write the updated contents back to the file
            file.writeText(fileContents.joinToString("\n"))
        }

        // track stage position
        stage.xProperty().addListener { _, oldX, newX ->
            // do something when the x property changes
            println("Stage x changed from $oldX to $newX")

            // Read the contents of the file
            val fileContents2 = file2.readText().lines().toMutableList()

            // Replace the first line with the new width value
            fileContents2[0] = "$newX"

            // Write the updated contents back to the file
            file2.writeText(fileContents2.joinToString("\n"))
        }

        // add a listener to the y property
        stage.yProperty().addListener { _, oldY, newY ->
            // do something when the y property changes
            println("Stage y changed from $oldY to $newY")

            // Read the contents of the file
            val fileContents2 = file2.readText().lines().toMutableList()

            // Replace the first line with the new width value
            fileContents2[1] = "$newY"

            // Write the updated contents back to the file
            file2.writeText(fileContents2.joinToString("\n"))
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