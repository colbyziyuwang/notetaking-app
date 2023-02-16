package net.codebot.application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.control.ToolBar
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage

class NoteView (private val model: Model) : VBox(), IView{

    //Starting off buttons in unknown state as advised by example MVC
    private val createButton = Button("?")
    private val editButton = Button("?")
    private val deleteButton = Button("?")

    private val toolBar = ToolBar() //Toolbar

    val outmostPane = BorderPane() //outermost container of view

    private val dataArea = TextArea() // holds the visual aspects of the data


    override fun updateView() {
        // get the items
        val note = model.getItems()

        // display Note text
        val text = Text(note.getData().text)
        text.font = Font("Helvetica", 12.0)
        text.wrappingWidth = 350.0

        dataArea.text = text.text

        //JERRY's TOOLBAR HERE : outmoustPane.top = Jerrys toolbar
        outmostPane.center = dataArea
    }


    init {
        //setting up the view
        this.alignment = Pos.CENTER
        this.minHeight = 100.0

        //init button actions
        createButton.text = "Create" //Text
        editButton.text = "Edit"
        deleteButton.text = "Delete"
        createButton.font = Font("Helvetica", 11.0) //Fonts
        editButton.font = Font("Helvetica", 11.0)
        deleteButton.font = Font("Helvetica", 11.0)

        toolBar.items.addAll(createButton, editButton, deleteButton) //adding to toolbar

        outmostPane.center = toolBar // Adding to the outer box


        //View Controller*************************************************************************************
        createButton.setOnMouseClicked {
            model.createNote()
        }

        dataArea.setOnKeyTyped {
            model.updateData(dataArea)
            dataArea.positionCaret(dataArea.text.length) //fixing cursor postion
        }


        //TODO : Implement Edit and Delete note actions


        //CSS
        

        //registering view with the model when ready to start receiving data
        model.addView(this)
    }

    /* Jerry's toolbar
    *         val toolBar = ToolBar(
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
    * */
}
