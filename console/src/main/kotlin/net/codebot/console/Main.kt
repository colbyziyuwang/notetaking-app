package net.codebot.console

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.codebot.shared.SysInfo
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration


val SERVER_ADDRESS = "http://127.0.0.1:8080/messages"

@Serializable
data class Message(val id: String?, val text: String)


fun main() {
    println("Console Application:")
//    val post_response = post(Message("100", "Hello ${SysInfo.hostname}"))
//
//    val get_response = get()
//    println(get_response) // list of JSON objects from database
    println("Hello ${SysInfo.userName}")

}

fun get(): String {
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
    return response.body()
}

fun post(message: Message): String {
    val string = Json.encodeToString(message)

    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder()
        .uri(URI.create(SERVER_ADDRESS))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(string))
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body()
}

