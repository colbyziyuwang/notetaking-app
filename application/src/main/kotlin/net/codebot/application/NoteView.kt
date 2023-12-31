package net.codebot.application
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.control.ToolBar
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import javafx.scene.control.Button
import java.io.File
import javafx.scene.web.HTMLEditor
import org.jsoup.Jsoup
import java.nio.file.Files
import java.nio.file.Paths

class NoteView (private val model: Model) : VBox(), IView{

    private var currentNote: Note? = null

    private val createButton = Button("?").apply {
        text = "Create" //Text
        font = Font("Helvetica", 11.0) //Fonts

        //Button Actions
        setOnMouseClicked {
            ////TODO: Check for duplicate named note and throw appropriate errors

            if(nameTextBox.text.isEmpty()){
                model.createNote("New Note") //TODO
            } else {
                model.createNote(nameTextBox.text)
            }
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
    val dataArea = HTMLEditor() // holds the visual aspects of the data
    //val dataContainer = VBox()

    val saveButton = Button("")

    override fun updateView() {
        // this wouldn't run outside the function under the declaration for some reason
        val saveIcon = Image("save-icon.png")
        saveButton.graphic = ImageView(saveIcon)

        var curNote = model.getCurrentNote()

        if(curNote == null){
            children.setAll(directoryViewPane)
        }
        else {
            // display Note text
            val text = Text(curNote.getData().htmlText)
            text.font = Font("Helvetica", 12.0)
            text.wrappingWidth = 350.0

            dataArea.htmlText = text.text

            // debugging:
            /*println("text.text: " + text.text)
            println("dataArea.htmlText: " + dataArea.htmlText)
            println("dataArea.htmlText as str: " + Jsoup.parse("<html>hello</html>").text())*/


            //val temp = curNote.getCarat() //debugging
            //dataArea.positionCaret(temp)
            //println("Positioned caret at ${temp}") //debugging

            /*dataArea.onKeyPressed = EventHandler { event ->
                if (event.code == KeyCode.SPACE) {
                    println("updated str:" + Jsoup.parse(dataArea.htmlText).text()) // debugging
                    model.updateData(curNote!!.getNoteName(), dataArea/*, curNote!!.getCarat()*/)
                }

                //dataArea.positionCaret(dataArea.text.length) //fixing cursor postion
            }*/

            /*dataArea.onKeyPressed = EventHandler { event ->
                if (event.code == KeyCode.SPACE) {
                    curNote!!.saveState()
                    println("saved text") // debugging
                }
            }*/

            // buttons for note manipulation
            /*val undoButton = Button("")
            val redoButton = Button("")

            // adding icons
            val undoIcon = Image("undo-icon.png")
            undoButton.graphic = ImageView(undoIcon)
            val redoIcon = Image("redo-icon.png")
            redoButton.graphic = ImageView(redoIcon)

            undoButton.setOnMouseClicked {
                curNote!!.undoState()
                println("undo") // for debugging
            }

            redoButton.setOnMouseClicked {
                curNote!!.redoState()
                println("redo") // for debugging
            }*/

            val closeButton = Button("Close")
            closeButton.setOnMouseClicked {
                model.removeCurrentNote()
                model.updateNotes()
            }

            saveButton.setOnMouseClicked {
                // println("Caret position is ${curNote.getData().caretPosition}") //debugging
                model.updateData(curNote!!.getNoteName(), dataArea/*, curNote!!.getCarat()*/)
                model.saveData(curNote.getNoteName())
            }

            // export to pdf button
            val pdfButton = Button("Export to PDF")
            pdfButton.setOnMouseClicked {
                println("Exported to PDF")
                val note = model.getCurrentNote()
                val name = note?.getNoteName()
                val contents = Jsoup.parse(note?.getContent()).text().toString()

                // println(contents) // debugging


                if (name != null && contents.isNotEmpty()) {
                    val desktopPath = System.getProperty("user.home") + "/Desktop/"
                    val pdfFilePath = desktopPath + name + ".pdf"

                    try {
                        Files.createDirectories(Paths.get(desktopPath))
                        val pdfWriter = PdfWriter(pdfFilePath)
                        val pdfDocument = PdfDocument(pdfWriter)
                        val document = Document(pdfDocument)

                        document.add(Paragraph(contents))
                        document.close()

                        println("PDF saved to: $pdfFilePath")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        println("Error exporting PDF: ${e.message}")
                    }
                }
            }

            // key combinations for keyboard shortcuts
            // val undoComb = KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN)
            // val redoComb = KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN)
            val saveComb = KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)

            // event handler for shortcuts
            outmostPane.onKeyPressed = EventHandler { event ->
                /*if (undoComb.match(event)) {
                    curNote!!.undoState()
                    println("undo key comb") // debugging
                } else if (redoComb.match(event)) {
                    curNote!!.redoState()
                    println("redo key comb") // debugging
                } else */if (saveComb.match(event)) {
                    // println("Caret position is ${curNote.getData().caretPosition}") //debugging
                    model.saveData(curNote.getNoteName())
                }
            }
            //TODO: Once copy/paste is complete
            //val copyButton = Button("Copy")
            //val pasteButton = Button("Paste")

            val noteToolBar = ToolBar() //Toolbar
            noteToolBar.items.addAll(/*undoButton, redoButton,*/ closeButton, saveButton,
                pdfButton)//, copyButton, pasteButton)

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
            isFitToHeight = true
            isFitToWidth = true
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
