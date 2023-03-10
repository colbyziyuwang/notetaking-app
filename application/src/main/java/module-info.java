module application {
    requires kotlin.stdlib;
    requires javafx.controls;
    requires kotlinx.coroutines.core.jvm;
    requires shared;
    requires java.sql;
    requires exposed.core;
    exports net.codebot.application;
}