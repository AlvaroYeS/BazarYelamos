module com.bazar.bazaryelamos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
	requires java.sql;
    requires org.postgresql.jdbc;

    opens com.bazar.bazaryelamos to javafx.fxml;
    exports com.bazar.bazaryelamos;
}