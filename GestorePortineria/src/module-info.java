module GestorePortineria {
	exports controller;
	exports model.implementation;
	exports application;
	exports model;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens model.implementation to javafx.base;
    opens test to org.junit.jupiter.api;
}