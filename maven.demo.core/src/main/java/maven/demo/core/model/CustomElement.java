package maven.demo.core.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Basisklasse für Elemente in der Szene.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
@Entity
@Table(name = "ELEMENTS")
@Access(AccessType.PROPERTY)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class CustomElement extends CustomObject {
	
	// ID
	
	private final LongProperty id = new SimpleLongProperty();

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false)
	public long getId() {
		return id.get();
	}
	public void setId(long value) {
		id.set(value);
	}
	public LongProperty idProperty() {
		return id;
	}
	
	// Scene
	
	private final ObjectProperty<CustomScene> scene = new SimpleObjectProperty<>();
	
	@ManyToOne(targetEntity = CustomScene.class, optional = false)
	public CustomScene getScene() {
		return scene.get();
	}
	public void setScene(CustomScene value) {
		scene.set(value);
	}
	public ObjectProperty<CustomScene> sceneProperty() {
		return scene;
	}
	
	// Name
	
	private final StringProperty name = new SimpleStringProperty();
	
	@Column(nullable = false)
	public String getName() {
		return name.get();
	}
	public void setName(String value) {
		name.set(value);
	}
	public StringProperty nameProperty() {
		return name;
	}
	
	// Red

	private final IntegerProperty red = new SimpleIntegerProperty(128);
	
	@Column(nullable = false)
	public int getRed() {
		return red.get();
	}
	public void setRed(int value) {
		red.set(value);
	}
	public IntegerProperty redProperty() {
		return red;
	}
	
	// Green

	private final IntegerProperty green = new SimpleIntegerProperty(128);
	
	@Column(nullable = false)
	public int getGreen() {
		return green.get();
	}
	public void setGreen(int value) {
		green.set(value);
	}
	public IntegerProperty greenProperty() {
		return green;
	}
	
	// Blue

	private final IntegerProperty blue = new SimpleIntegerProperty(128);
	
	@Column(nullable = false)
	public int getBlue() {
		return blue.get();
	}
	public void setBlue(int value) {
		blue.set(value);
	}
	public IntegerProperty blueProperty() {
		return blue;
	}
	
	// X

	private final DoubleProperty x = new SimpleDoubleProperty(0);
	
	@Column(nullable = false)
	public double getX() {
		return x.get();
	}
	public void setX(double value) {
		x.set(value);
	}
	public DoubleProperty xProperty() {
		return x;
	}
	
	// Y

	private final DoubleProperty y = new SimpleDoubleProperty(0);
	
	@Column(nullable = false)
	public double getY() {
		return y.get();
	}
	public void setY(double value) {
		y.set(value);
	}
	public DoubleProperty yProperty() {
		return y;
	}
	
	// Z

	private final DoubleProperty z = new SimpleDoubleProperty(0);
	
	@Column(nullable = false)
	public double getZ() {
		return z.get();
	}
	public void setZ(double value) {
		z.set(value);
	}
	public DoubleProperty zProperty() {
		return z;
	}
	
	// Alpha

	private final DoubleProperty alpha = new SimpleDoubleProperty(0);
	
	@Column(nullable = false)
	public double getAlpha() {
		return alpha.get();
	}
	public void setAlpha(double value) {
		alpha.set(value);
	}
	public DoubleProperty alphaProperty() {
		return alpha;
	}
	
	// Beta

	private final DoubleProperty beta = new SimpleDoubleProperty(0);
	
	@Column(nullable = false)
	public double getBeta() {
		return beta.get();
	}
	public void setBeta(double value) {
		beta.set(value);
	}
	public DoubleProperty betaProperty() {
		return beta;
	}
	
	// Gamma

	private final DoubleProperty gamma = new SimpleDoubleProperty(0);
	
	@Column(nullable = false)
	public double getGamma() {
		return gamma.get();
	}
	public void setGamma(double value) {
		gamma.set(value);
	}
	public DoubleProperty gammaProperty() {
		return gamma;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
