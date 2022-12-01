package maven.demo.gui.parts;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import maven.demo.core.model.CustomElement;
import maven.demo.core.model.CustomObject;
import maven.demo.core.model.CustomScene;
import maven.demo.core.model.elements.CustomCube;
import maven.demo.core.model.elements.CustomSphere;
import maven.demo.gui.Part;
import maven.demo.gui.utils.Images;

/**
 * Darstellung und Modifizierung der Eigenschaften von Szenen und Szenenelementen.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
public final class RightPart extends Part {
	
	/**
	 * Objekte dieser Klasse repräsentieren Zeilen in der Eigenschaftentabelle für Datenobjekte (d.h. Szenen oder Szenenelemente).
	 * 
	 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
	 *
	 * @param <T> Der Typ der Eigenschaft des Datenobjekts, die in der jeweiligen Tabellenzeile angezeigt wird.
	 */
	private final class CustomProperty<T> {
		
		/**
		 * Der Name der Objekteigenschaft (z.B. Länge, Breite, Höhe, Radius, ...).
		 */
		private final String name;
		/**
		 * Die Objekteigenschaft selbst (als überwachbare JavaFX Eigenschaft).
		 */
		private final Property<T> value;
		/**
		 * Kann der Wert der Objekteigenschaft verändert werden (ja/nein)? 
		 */
		private final boolean editable;
		/**
		 * Objekt, das für die Konvertierung von Zeichenketten in Eigenschaftenwerte verantwortlich ist.
		 */
		private final StringConverter<? extends T> converter;
		
		public CustomProperty(String name, Property<T> value, boolean editable, StringConverter<? extends T> converter) {
			this.name = name;
			this.value = value;
			this.editable = editable;
			this.converter = converter;
		}
		
		public final String getName() {
			return name;
		}
		public final Property<T> getValue() {
			return value;
		}
		public final boolean getEditable() {
			return editable;
		}
		public final StringConverter<? extends T> getConverter() {
			return converter;
		}
		
	}
	
	private final Label label = new Label("Eigenschaften");
	
	private final ToolBar bar = new ToolBar(label, new Button(null, Images.load("help.png")));
	
	private final TableColumn<CustomProperty<?>, String> name = new TableColumn<>("Name");
	private final TableColumn<CustomProperty<?>, Object> value = new TableColumn<>("Wert");
	
	private final TableView<CustomProperty<?>> table = new TableView<>();
	
	public RightPart() {
		// Wert der ersten Tabellenspalte konfigurieren
		name.setCellValueFactory(row -> {
			CustomProperty<?> property = row.getValue();
			
			if (property != null) {
				return new SimpleStringProperty(property.getName());
			} else {
				return null;
			}
		});
		name.prefWidthProperty().bind(widthProperty().multiply(1./3).subtract(8));
		
		// Wert der zweiten Tabellenspalte konfigurieren
		value.setCellValueFactory(row -> {
			CustomProperty<?> property = row.getValue();
			
			if (property != null) {
				return new SimpleObjectProperty<>(property.getValue().getValue());
			} else {
				return null;
			}
		});
		// Darstellung der zweiten Tabellenspalte konfigurieren
		value.setCellFactory(row -> {
			return new TableCell<CustomProperty<?>, Object>() {
				@SuppressWarnings("unchecked")
				@Override
	            protected void updateItem(Object item, boolean empty){				
					// Objekteigenschaft auslesen, die in der aktuellen Tabellenzeile dargestellt werden soll
					CustomProperty<?> property = getTableRow().getItem();
					
					// Prüfen, ob die aktuelle Tabellenzeile leer ist oder nicht
					if (property != null) {
						// Wert der Objekteigenschaft als String auslesen
						String value = ((StringConverter<Object>) property.getConverter()).toString(item);
						
						// Textfeld für die Darstellung des Wertes erzeugen
						TextField field = new TextField(value);
						
						// Reagieren, wenn der Wert des Textfeldes verändert wird
						field.setOnAction(event -> {
							Object converted = property.getConverter().fromString(field.getText());
							
							if (!converted.equals(property.getValue())) {
								((Property<Object>) property.getValue()).setValue(converted);
								
								String transformed = ((StringConverter<Object>) property.getConverter()).toString(converted);
								
								field.setText(transformed);
							}
						});
						field.focusedProperty().addListener(event -> {
							Object converted = property.getConverter().fromString(field.getText());
							
							if (!converted.equals(property.getValue())) {
								((Property<Object>) property.getValue()).setValue(converted);
								
								String transformed = ((StringConverter<Object>) property.getConverter()).toString(converted);
								
								field.setText(transformed);
							}
						});
						
						// Textfeld editierbar machen, wen der Wert der Objekteigenschaft geändert werden darf
						field.setDisable(!property.getEditable());
						
						// Textfeld als Grafik (bzw. Inhalt) der Tabellenzelle setzen
						setGraphic(field);
					} else {
						// Grafik (bzw. Inhalt) der Tabellenzelle zurücksetzen
						setGraphic(null);
					}
	            }
			};
		});
		value.prefWidthProperty().bind(widthProperty().multiply(2./3).subtract(8));
		
		table.getColumns().add(name);
		table.getColumns().add(value);
		
		setTop(bar);
		setCenter(table);
		
		// Reagierte auf Änderungen am zuletzt selektierten Objekt (Szene oder Szenenelement)
		selectedObjectProperty().addListener(event -> {
			// Lösche die Tabellenzeilen für das vorher selektierte Objekt
			table.getItems().clear();
			
			// Aktuell selektiertes Objekt auslesen
			CustomObject object = getSelectedObject();
			
			// Prüfen, ob tatsächlich ein Objekt selektiert ist
			if (object != null) {
				// Prüfen, ob das zuletzt selektierte Objekt eine Szene oder ein Szenenelement ist
				if (object instanceof CustomScene) {
					// Tabellenzeilen für die Eigenschaften der Szene hinzufügen
					CustomScene scene = (CustomScene) object;
					
					table.getItems().add(new CustomProperty<>("ID", scene.idProperty(), false, new LongStringConverter()));
					table.getItems().add(new CustomProperty<>("Name", scene.nameProperty(), true, new DefaultStringConverter()));
				} else if (object instanceof CustomElement) {
					// Tabellenzeilen für die Eigenschaften des Szenenelements hinzufügens
					CustomElement element = (CustomElement) object;
					
					table.getItems().add(new CustomProperty<>("ID", element.idProperty(), false, new LongStringConverter()));
					table.getItems().add(new CustomProperty<>("Name", element.nameProperty(), true, new DefaultStringConverter()));
					
					table.getItems().add(null);
					
					table.getItems().add(new CustomProperty<>("X", element.xProperty(), true, new DoubleStringConverter()));
					table.getItems().add(new CustomProperty<>("Y", element.yProperty(), true, new DoubleStringConverter()));
					table.getItems().add(new CustomProperty<>("Z", element.zProperty(), true, new DoubleStringConverter()));
					
					table.getItems().add(null);
					
					table.getItems().add(new CustomProperty<>("Alpha", element.alphaProperty(), true, new DoubleStringConverter()));
					table.getItems().add(new CustomProperty<>("Beta", element.betaProperty(), true, new DoubleStringConverter()));
					table.getItems().add(new CustomProperty<>("Gamma", element.gammaProperty(), true, new DoubleStringConverter()));
					
					table.getItems().add(null);
					
					// TODO Color Picker für die Auswahl der Farbe verwenden 
					table.getItems().add(new CustomProperty<>("Rot", element.redProperty(), true, new IntegerStringConverter()));
					table.getItems().add(new CustomProperty<>("Grün", element.greenProperty(), true, new IntegerStringConverter()));
					table.getItems().add(new CustomProperty<>("Blau", element.blueProperty(), true, new IntegerStringConverter()));
					
					table.getItems().add(null);
					
					// Prüfen, ob das Szenenelement ein Würfel oder eine Kugel ist
					if (element instanceof CustomCube) {
						// Tabellenzeilen für die Eigenschaften des Würfel hinzufügen
						CustomCube cube = (CustomCube) element;
						
						table.getItems().add(new CustomProperty<>("Breite", cube.widthProperty(), true, new DoubleStringConverter()));
						table.getItems().add(new CustomProperty<>("Höhe", cube.heightProperty(), true, new DoubleStringConverter()));
						table.getItems().add(new CustomProperty<>("Länge", cube.lengthProperty(), true, new DoubleStringConverter()));
					} else if (element instanceof CustomSphere) {
						// Tabellenzeilen für die Eigenschaften des Kugel hinzufügen
						CustomSphere sphere = (CustomSphere) element;

						table.getItems().add(new CustomProperty<>("Radius", sphere.radiusProperty(), true, new DoubleStringConverter()));
					} else {
						// TODO Unterstütze auch Zylinder
						
						// Informiere den Entwickler über das Problem
						throw new IllegalStateException("Element type not supported!");
					}
				} else {
					// Informiere den Entwickler über das Problem
					throw new IllegalStateException("Object type not supported!");
				}
			}
		});
	}
	
}
