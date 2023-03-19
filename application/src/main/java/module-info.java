module application {
    requires kotlin.stdlib;
    requires javafx.controls;
    requires kotlinx.coroutines.core.jvm;
    requires shared;
    requires java.sql;
    requires exposed.core;
    requires kernel;
    requires layout;
    exports net.codebot.application;
}