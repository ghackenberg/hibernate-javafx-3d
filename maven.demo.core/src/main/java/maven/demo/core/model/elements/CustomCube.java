package maven.demo.core.model.elements;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import maven.demo.core.model.CustomElement;

/**
 * Klasse für die Repräsentation von Würfeln in einer Szene.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
@Entity
@Access(AccessType.PROPERTY)
@DiscriminatorValue("cube")
public final class CustomCube extends CustomElement {
	
	// Width

	private final DoubleProperty width = new SimpleDoubleProperty(1);
	
	@Column
	public double getWidth() {
		return width.get();
	}
	public void setWidth(double value) {
		width.set(value);
	}
	public DoubleProperty widthProperty() {
		return width;
	}
	
	// Height

	private final DoubleProperty height = new SimpleDoubleProperty(1);
	
	@Column
	public double getHeight() {
		return height.get();
	}
	public void setHeight(double value) {
		height.set(value);
	}
	public DoubleProperty heightProperty() {
		return height;
	}
	
	// Length

	private final DoubleProperty length = new SimpleDoubleProperty(1);
	
	@Column
	public double getLength() {
		return length.get();
	}
	public void setLength(double value) {
		length.set(value);
	}
	public DoubleProperty lengthProperty() {
		return length;
	}
	
}
