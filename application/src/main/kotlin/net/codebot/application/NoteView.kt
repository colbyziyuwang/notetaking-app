package net.codebot.application
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.ToolBar
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text

class NoteView (private val model: Model) : VBox(), IView{

    //Starting off buttons in unknown state as advised by example MVC

    //Jerry: Make this more abstract with a dynamic array of buttons,
    //       when context change happens push new buttons onto array
    //       (generalize adding buttons to pushing in array)
    private val createButton = Button("?")
    private val editButton = Button("?")
    private val deleteButton = Button("?")

    private val toolBar = ToolBar() //Toolbar

    val outmostPane = BorderPane() //outermost container of view

    val dataArea = TextArea() // holds the visual aspects of the data
    val dataContainer = VBox()

    override fun updateView() {
        // get the items
        val note = model.getItems()

        // display Note text
        val text = Text(note.getData().text)
        text.font = Font("Helvetica", 12.0)
        text.wrappingWidth = 350.0

        dataArea.text = text.text

        dataArea.onKeyPressed = EventHandler { event ->
            if (event.code == KeyCode.SPACE) model.getItems().saveState()
        }

        // buttons for note manipulation
        val undoButton = Button("Undo")
        undoButton.setOnMouseClicked {
            model.getItems().undoState()
            println("undo")
        }
        val redoButton = Button("Redo")
        redoButton.setOnMouseClicked {
            model.getItems().redoState()
            println("redo")
        }

        //TODO: Once copy/paste is complete
        //val copyButton = Button("Copy")
        //val pasteButton = Button("Paste")

        val noteToolBar = ToolBar() //Toolbar
        noteToolBar.items.addAll(undoButton, redoButton)//, copyButton, pasteButton)

        outmostPane.top = noteToolBar
        outmostPane.center = dataArea

        // detect size change
        val stageSizeListener: ChangeListener<Number> = ChangeListener<Number> { observable, oldValue, newValue ->
            val name = note.getNoteName()
            model.updateSize(name, outmostPane.getHeight(), outmostPane.getWidth())
        }

        outmostPane.widthProperty().addListener(stageSizeListener)
        outmostPane.heightProperty().addListener(stageSizeListener)

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
            model.updateData(dataArea, dataArea.caretPosition)
            dataArea.positionCaret(model.getCaratPOS()) //fixing cursor postion
        }


        //TODO : Implement Edit and Delete note actions

        //registering view with the model when ready to start receiving data
        model.addView(this)
    }

}
