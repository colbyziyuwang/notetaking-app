module server {
    requires kotlin.stdlib;
    requires shared;
    requires kotlinx.serialization.core;
    requires java.net.http;
    requires kotlinx.serialization.json;
    exports net.codebot.server;
}