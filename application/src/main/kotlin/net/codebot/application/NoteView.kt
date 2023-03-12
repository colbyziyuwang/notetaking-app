package net.codebot.application
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text


class NoteView (private val model: Model) : VBox(), IView{

    private var currentNote: Note? = null

    private val createButton = Button("?").apply {
        text = "Create" //Text
        font = Font("Helvetica", 11.0) //Fonts

        //Button Actions
        setOnMouseClicked {
            model.createNote(nameTextBox.text)
        }
    }

    private val editButton = Button("?")
    private val deleteButton = Button("?")
    private val loadButton = Button("?")

    private val toolBar = ToolBar() //Toolbar
    private val directoryToolBar = ToolBar() //Toolbar

    val outmostPane = BorderPane() //outermost container of view
    val directoryViewPane = BorderPane()

    val nameTextBox = TextField()
    val dataArea = TextArea() // holds the visual aspects of the data
    //val dataContainer = VBox()


    val saveButton = Button("Save")

    override fun updateView() {
        var curNote = model.getCurrentNote()

        if(curNote == null){
            children.setAll(directoryViewPane)
        }
        else {
            // display Note text
            val text = Text(curNote.getData().text)
            text.font = Font("Helvetica", 12.0)
            text.wrappingWidth = 350.0

            dataArea.text = text.text

            dataArea.onKeyPressed = EventHandler { event ->
                if (event.code == KeyCode.SPACE) curNote!!.saveState()
            }

            dataArea.setOnKeyTyped {
                model.updateData(curNote!!.getNoteName(), dataArea, curNote!!.getCarat())
                dataArea.positionCaret(dataArea.text.length) //fixing cursor postion
            }

            // buttons for note manipulation
            val undoButton = Button("Undo")
            undoButton.setOnMouseClicked {
                curNote!!.undoState()
                println("undo")
            }
            val redoButton = Button("Redo")
            redoButton.setOnMouseClicked {
                curNote!!.redoState()
                println("redo")
            }

            val closeButton = Button("Close")
            closeButton.setOnMouseClicked {
                model.setCurrentNote(null)
                model.notifyObservers()
            }

            //TODO: Once copy/paste is complete
            //val copyButton = Button("Copy")
            //val pasteButton = Button("Paste")

        val noteToolBar = ToolBar() //Toolbar
        noteToolBar.items.addAll(undoButton, redoButton, saveButton, closeButton)//, copyButton, pasteButton)

            outmostPane.top = noteToolBar
            outmostPane.center = dataArea

            children.setAll(outmostPane)
        }

    }


    init {
        model.addView(this)

        //setting up the view
        this.alignment = Pos.CENTER
        this.minHeight = 100.0


        directoryToolBar.items.setAll(nameTextBox, createButton) //adding to toolbar

        directoryViewPane.top = directoryToolBar // Adding to the outer box
        directoryViewPane.center = ScrollPane(DirectoryView(model)).apply {
            // only show vertical scroll bar
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            // pref height
            prefHeight = 300.0
            isFitToWidth = true;
        }

        children.setAll(directoryViewPane)


        //TODO : Implement Edit and Delete note actions

        //registering view with the model when ready to start receiving data
        model.addView(this)
    }

}

// Deprecated code for figuring out size changes
//// detect size change
//val stageSizeListener: ChangeListener<Number> = ChangeListener<Number> { observable, oldValue, newValue ->
//    val name = curNote!!.getNoteName()
//    model.updateSize(name, outmostPane.getHeight(), outmostPane.getWidth())
//}
