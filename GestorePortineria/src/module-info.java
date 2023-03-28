module GestorePortineria {
	exports controller;
	exports model.implementation;
	exports application;
	exports model;

	requires transitive java.sql;
	requires transitive javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens model.implementation to javafx.base;
    opens test to org.junit.jupiter.api;
}