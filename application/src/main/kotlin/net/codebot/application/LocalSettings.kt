package net.codebot.application
import org.jetbrains.exposed.sql.Table

// This table intends to save the width and height of the file (for now)
object LocalSettings : Table() {
    val id = integer("id").autoIncrement()
    val noteName = varchar("noteName", 50) // noteName
    val width = double("width") // width
    val height = double("height") // height

    override val primaryKey = PrimaryKey(id) // noteName is the primary key
}