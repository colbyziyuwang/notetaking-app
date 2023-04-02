package net.codebot.server

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration




@Serializable
data class DBNote(
    val name: String,
    val data: String,
    val lmDate: String/*, val cDate: String*/)

fun main() {
    val test = webService()
    println("Console Application:")
    //val post_response = test.post(DBNote("TestNote1", "Hello ${SysInfo.hostname}", "creationDateHere", "lmDateHere"))

    val get_response = test.get()
    println(get_response) // list of JSON objects from database

}



class webService{
    init {

    }
    private val SERVER_ADDRESS = "http://127.0.0.1:8080/notes"

    //Get list of notes from online database
    fun get(): ArrayList<DBNote> {
        val client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(Duration.ofSeconds(20))
            .build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(SERVER_ADDRESS))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val json = Json { ignoreUnknownKeys = true }
        println(response.body())
        return json.decodeFromString<ArrayList<DBNote>>(response.body())
    }


    //Add note to online database list
    fun post(note: DBNote): String {
        val string = Json.encodeToString(note)

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(SERVER_ADDRESS))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(string))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body()
    }

    fun deleteAll(): String{
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(SERVER_ADDRESS))
            .header("Content-Type", "application/json")
            .DELETE()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body()
    }




}





