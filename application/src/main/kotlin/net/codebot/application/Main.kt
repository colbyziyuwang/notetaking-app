package net.codebot.application

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.Label
import javafx.scene.control.ToolBar
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import net.codebot.shared.SysInfo

class Main : Application() {
    override fun start(stage: Stage) {
        val toolBar = ToolBar(
            Button("New"),
            Button("Open"),
            Button("Save")
        )
        val border = BorderPane()
        border.top = toolBar

        stage.scene = Scene(
            border,//StackPane(Label("Hello ${SysInfo.userName}")),
            250.0,
            150.0)
        stage.isResizable = false
        val note1 = Notes()

        stage.title = note1.getLastModifiedDate() // Displaying the current date for now.

        stage.show()
    }
}