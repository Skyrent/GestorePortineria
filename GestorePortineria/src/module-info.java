module GestorePortineria {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires java.desktop;

	opens application to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens model.implementation to javafx.base;
}
