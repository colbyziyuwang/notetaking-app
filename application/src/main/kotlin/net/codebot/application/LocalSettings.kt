package net.codebot.application
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

// This table intends to save the width and height of the file (for now)
object LocalSettings : Table() {
    val noteName = varchar("noteName", 50) // noteName
    val width = integer("width") // width
    val height = integer("height") // height

    override val primaryKey = PrimaryKey(noteName) // noteName is the primary key
}