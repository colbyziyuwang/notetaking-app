package net.codebot.application

import org.jetbrains.exposed.sql.Table

object DataBase : Table() {
    //Each value below corresponds to the database column object for which it is named after

    //Unique integer id for every single note, set to be the primary key
    val noteID = integer("noteID").autoIncrement().primaryKey()

    //The note name must not exceed 50 characters
    // TODO: Error trap for note name
    val name = varchar("noteName", 50)

    val content = text("textData")
    val caratPosition = integer("caratPosition")
    val creationDate = varchar("creationDate", 50)
    val lastModifiedDate = varchar("lastModifiedDate", 50)


}