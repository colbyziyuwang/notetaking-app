package net.codebot.application

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction


object DataBase : Table() {
    //Each value below corresponds to the database column object for which it is named after

    //Unique integer id for every single note, set to be the primary key
    val noteID = integer("noteID").autoIncrement()
    override val primaryKey = PrimaryKey(noteID)

    //The note name must not exceed 50 characters
    // TODO: Error trap for note name
    val name = varchar("noteName", 50)

    val content = text("textData")
    val caratPosition = integer("caratPosition")
    val creationDate = varchar("creationDate", 50)
    val lastModifiedDate = varchar("lastModifiedDate", 50)
    val winWidth = double("noteWidth")
    val winHeight = double("noteHeight")

}

//Defines all the functions to interact with the DataBase modularly
class DataBaseDAO {
    //Connects to a pre-existing database and returns a reference to the database connection
    private fun connectDB(): Database{
        val db = Database.connect("jdbc:sqlite:noteData.db", "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(DataBase)
        }
        return db
    }

    // Update all fields of a note whenever any field is modified
    fun updateNote(note: Note) {
        val db = connectDB()
        transaction {
            //TODO: Check that note exists and if it doesnt insert it
            DataBase.update({DataBase.name eq note.getNoteName()}){ row ->
                row[name] = note.getNoteName()
                row[content] = note.getData().htmlText
                row[caratPosition] = note.getCarat()
                row[creationDate] = note.getCreationDate()
                row[lastModifiedDate] = note.getLastModifiedDate()
                row[winWidth] = note.getWinSize()[0] // corresponds to the width of a note
                row[winHeight] = note.getWinSize()[1]
            }
        }
        TransactionManager.closeAndUnregister(db) // closing the connection
    }


    // return the height and width of a note in the database
    fun getWidthHeight(note: Note): Array<Double> {
        return transaction {//returning the returned value of the transaction block
            val row = DataBase.select { DataBase.name eq note.getNoteName() }.single() // Names should be unique in database
            val width = row[DataBase.winWidth]
            val height = row[DataBase.winHeight]
            return@transaction arrayOf(width, height) //returning value from transaction
        }
    }

    //ADD a note to the DataBase
    fun addNote(note: Note){
        val db = connectDB()
        transaction {
            if(DataBase.select {DataBase.name eq note.getNoteName()}.empty()){ // Adding to the database only if note name doesnt already exist
                DataBase.insert { newRow ->
                    newRow[name] = note.getNoteName()
                    newRow[content] = note.getData().htmlText
                    newRow[caratPosition] = note.getCarat()
                    newRow[creationDate] = note.getCreationDate()
                    newRow[lastModifiedDate] = note.getLastModifiedDate()
                    newRow[winWidth] = note.getWinSize()[0] // corresponds to the width of a note
                    newRow[winHeight] = note.getWinSize()[1]
                }
            }

        }
        TransactionManager.closeAndUnregister(db)
    }

    //Delete a note from the database
    fun deleteNote(note: Note){
        val db = connectDB()
        transaction {
            DataBase.deleteWhere { DataBase.name eq note.getNoteName()}
        }
        TransactionManager.closeAndUnregister(db)
    }

    //Deletes all notes in the database
    fun deleteAllNotes(){
        val db = connectDB()
        transaction {
            DataBase.deleteAll()
        }
        TransactionManager.closeAndUnregister(db)
    }


    //Return note by name
    fun getNote(noteName: String): Note {
        val db = connectDB()
        val note = transaction {
            val note = DataBase.select{DataBase.name eq noteName}.single()
            Note(note[DataBase.name], note[DataBase.content], note[DataBase.creationDate],
                note[DataBase.lastModifiedDate]/*, note[DataBase.caratPosition]*/, note[DataBase.winWidth],
                note[DataBase.winHeight])
        }
        TransactionManager.closeAndUnregister(db)
        return note
    }
}

//TODO: USEFUL FOR UPCOMING FUNCTIONS
//accessing stuff from database //DEBUGGING
//        val dao = DataBaseDAO()
//        transaction {
//            val result = DataBase.select { DataBase.noteID eq 2 }.toList()
//
//            for (row in result){
//                width = row[DataBase.winWidth]
//                height = row[DataBase.winHeight]
//            }
//        }