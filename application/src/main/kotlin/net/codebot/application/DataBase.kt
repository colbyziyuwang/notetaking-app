package net.codebot.application

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*


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
    // Update all fields of a note whenever any field is modified
    // TODO
    fun updateNote() {
        transaction {

        }
    }

    // return the height and width of a note in the database
    //TODO

    //ADD a note to the DataBase
    //TODO

    //Return note by name
    //TODO

    //Delete a note from the database
    //TODO
}