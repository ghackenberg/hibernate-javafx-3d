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

public final class RightPart extends Part {
	
	private final class CustomProperty<T> {
		
		private final String name;
		private final Property<T> value;
		private final boolean editable;
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
		bar.prefWidthProperty().bind(widthProperty());
		
		name.setCellValueFactory(row -> {
			CustomProperty<?> property = row.getValue();
			
			if (property != null) {
				return new SimpleStringProperty(property.getName());
			} else {
				return null;
			}
		});
		name.prefWidthProperty().bind(widthProperty().multiply(1./3).subtract(8));
		
		value.setCellValueFactory(row -> {
			CustomProperty<?> property = row.getValue();
			
			if (property != null) {
				return new SimpleObjectProperty<>(property.getValue().getValue());
			} else {
				return null;
			}
		});
		value.setCellFactory(row -> {
			return new TableCell<CustomProperty<?>, Object>() {
				@SuppressWarnings("unchecked")
				@Override
	            protected void updateItem(Object item, boolean empty){				
					CustomProperty<?> property = getTableRow().getItem();
						
					if (property != null) {
						String value = ((StringConverter<Object>) property.getConverter()).toString(item);
						
						TextField field = new TextField(value);
						
						field.setOnAction(event -> {
							Object converted = property.getConverter().fromString(field.getText());
							
							if (!converted.equals(property.getValue())) {
								((Property<Object>) property.getValue()).setValue(converted);
								
								String transformed = ((StringConverter<Object>) property.getConverter()).toString(converted);
								
								field.setText(transformed);
							}
						});
						
						field.setDisable(!property.getEditable());
						
						setGraphic(field);
					} else {
						setGraphic(null);
					}
	            }
			};
		});
		value.prefWidthProperty().bind(widthProperty().multiply(2./3).subtract(8));
		
		table.getColumns().add(name);
		table.getColumns().add(value);
		table.setEditable(true);
		table.prefWidthProperty().bind(widthProperty());
		
		setTop(bar);
		setCenter(table);
		
		selectedObjectProperty().addListener(event -> {
			table.getItems().clear();
			
			CustomObject object = getSelectedObject();
			
			if (object != null) {
				if (object instanceof CustomScene) {
					CustomScene scene = (CustomScene) object;
					
					table.getItems().add(new CustomProperty<Number>("ID", scene.idProperty(), false, new LongStringConverter()));
					table.getItems().add(new CustomProperty<>("Name", scene.nameProperty(), true, new DefaultStringConverter()));
				} else if (object instanceof CustomElement) {
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
					
					table.getItems().add(new CustomProperty<>("Rot", element.redProperty(), true, new IntegerStringConverter()));
					table.getItems().add(new CustomProperty<>("Grün", element.greenProperty(), true, new IntegerStringConverter()));
					table.getItems().add(new CustomProperty<>("Blau", element.blueProperty(), true, new IntegerStringConverter()));
					
					table.getItems().add(null);
					
					if (element instanceof CustomCube) {
						CustomCube cube = (CustomCube) element;
						
						table.getItems().add(new CustomProperty<>("Breite", cube.widthProperty(), true, new DoubleStringConverter()));
						table.getItems().add(new CustomProperty<>("Höhe", cube.heightProperty(), true, new DoubleStringConverter()));
						table.getItems().add(new CustomProperty<>("Länge", cube.lengthProperty(), true, new DoubleStringConverter()));
					} else if (element instanceof CustomSphere) {
						CustomSphere sphere = (CustomSphere) element;

						table.getItems().add(new CustomProperty<>("Radius", sphere.radiusProperty(), true, new DoubleStringConverter()));
					} else {
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
