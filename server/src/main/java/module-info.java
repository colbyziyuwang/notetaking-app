module server {
    requires kotlin.stdlib;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.web;
    exports net.codebot.server;
}
