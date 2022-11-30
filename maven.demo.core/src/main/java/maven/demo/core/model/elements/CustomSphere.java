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
 * Klasse für die Repräsentation von Kugeln in einer Szene.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
@Entity
@Access(AccessType.PROPERTY)
@DiscriminatorValue("sphere")
public final class CustomSphere extends CustomElement {
	
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
	
}
