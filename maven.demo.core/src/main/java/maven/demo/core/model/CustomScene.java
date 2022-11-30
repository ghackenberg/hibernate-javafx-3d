package maven.demo.core.model;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Klasse für die Repräsentation von Szenen, die wiederum aus Elementen bestehen.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
@Entity
@Table(name = "SCENES")
@Access(AccessType.PROPERTY)
public final class CustomScene extends CustomObject {
	
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
	
	// Elements
	
	private final ListProperty<CustomElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());

	@OneToMany(targetEntity = CustomElement.class, mappedBy = "scene", cascade = CascadeType.REMOVE)
	public List<CustomElement> getElements() {
		return elements.get();
	}
	public void setElements(List<CustomElement> value) {
		elements.set(new CustomList<>(value));
	}
	public ListProperty<CustomElement> elementsProperty() {
		return elements;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
