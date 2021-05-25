module pl.sda {
    requires javafx.controls;
    requires java.logging;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires org.hibernate.orm.core;
    exports pl.sda;
    exports pl.sda.client;
    exports pl.sda.server.database.entities;
}