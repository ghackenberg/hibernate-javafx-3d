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
 * Klasse für die Repräsentation von Zylindern in einer Szene.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
@Entity
@Access(AccessType.PROPERTY)
@DiscriminatorValue("cylinder")
public final class CustomCylinder extends CustomElement {
	
	// Radius

	private final DoubleProperty radius = new SimpleDoubleProperty(1);
	
	@Column
	public double getRadius() {
		return radius.get();
	}
	public void setRadius(double value) {
		radius.set(value);
	}
	public DoubleProperty radiusProperty() {
		return radius;
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
	
}
