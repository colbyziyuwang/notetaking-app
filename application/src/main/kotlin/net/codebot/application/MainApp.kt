package net.codebot.application
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class MainApp : Application() {
    override fun start(stage: Stage?) {
        // need to create database


        // MVC design based off of
        // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/MVC/03.MVC2/src/main/kotlin/MVC2.kt

        val model = Model()
        val currentView = CurrentView(model)
        stage?.scene = Scene(currentView.curView, 500.0, 350.0)
        stage?.title = "Imposter NoteTaking application"
        stage?.show()
    }
}