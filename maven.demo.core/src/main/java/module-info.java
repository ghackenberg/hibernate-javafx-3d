module maven.demo.core {
	exports maven.demo.core;
	exports maven.demo.core.model;
	exports maven.demo.core.model.elements;
	
	opens maven.demo.core.model;
	opens maven.demo.core.model.elements;
	
	requires transitive org.hibernate.orm.core;
	requires transitive javafx.base;
	requires jakarta.persistence;
	requires javafx.graphics;
}