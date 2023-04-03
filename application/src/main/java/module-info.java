module application {
    requires kotlin.stdlib;
    requires javafx.controls;
    requires kotlinx.coroutines.core.jvm;
    requires shared;
    requires java.sql;
    requires exposed.core;
    requires kernel;
    requires layout;
    requires console;
    requires kotlinx.serialization.json;
    exports net.codebot.application;
}