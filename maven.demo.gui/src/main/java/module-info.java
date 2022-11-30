module maven.demo.gui {
	exports maven.demo.gui;

	requires transitive maven.demo.core;
	requires transitive javafx.graphics;
	requires javafx.controls;
}