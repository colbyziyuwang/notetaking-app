package net.codebot.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}

@RestController
@RequestMapping("/notes")
class NoteResource(val service: NoteService) {
    @GetMapping
    fun index(): ArrayList<DBNote> = service.findNotes()

    @PostMapping
    fun post(@RequestBody note: DBNote) {
        service.post(note)
    }

    @DeleteMapping
    fun removeAll(){
        service.removeAllNotes()
    }

}

data class DBNote(
    val name: String,
    val data: String,
    val lmDate: String,
    /*val cDate: String*/)

@Service
class NoteService {
    var notes: ArrayList<DBNote> = arrayListOf()

    fun findNotes() = notes

    fun post(note: DBNote) {
        notes.add(note)
    }

    fun removeAllNotes(){
        notes.clear()
    }


}






