package maven.demo.gui;

import org.hibernate.Session;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.BorderPane;
import maven.demo.core.model.CustomElement;
import maven.demo.core.model.CustomObject;
import maven.demo.core.model.CustomScene;

public abstract class Part extends BorderPane {

	private final ObjectProperty<Session> session = new SimpleObjectProperty<>();

	private final ObjectProperty<CustomObject> selectedObject = new SimpleObjectProperty<>();
	private final ObjectProperty<CustomScene> selectedScene = new SimpleObjectProperty<>();
	private final ObjectProperty<CustomElement> selectedElement = new SimpleObjectProperty<>();
	
	public Part() {
		disableProperty().bind(session.isNull());
	}
	
	// Session
	
	public final ObjectProperty<Session> sessionProperty() {
		return session;
	}
	public final Session getSession() {
		return session.get();
	}
	public final void setSession(Session value) {
		session.set(value);
	}
	
	// Selected object
	
	public final ObjectProperty<CustomObject> selectedObjectProperty() {
		return selectedObject;
	}
	public final CustomObject getSelectedObject() {
		return selectedObject.get();
	}
	public final void setSelectedObject(CustomObject value)  {
		selectedObject.set(value);
	}
	
	// Selected scene
	
	public final ObjectProperty<CustomScene> selectedSceneProperty() {
		return selectedScene;
	}
	public final CustomScene getSelectedScene() {
		return selectedScene.get();
	}
	public final void setSelectedScene(CustomScene value) {
		selectedScene.set(value);
	}
	
	// Selected element
	
	public final ObjectProperty<CustomElement> selectedElementProperty() {
		return selectedElement;
	}
	public final CustomElement getSelectedElement() {
		return selectedElement.get();
	}
	public final void setSelectedElement(CustomElement value) {
		selectedElement.set(value);
	}

}
