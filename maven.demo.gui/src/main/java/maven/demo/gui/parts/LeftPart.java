package maven.demo.gui.parts;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import maven.demo.core.model.CustomElement;
import maven.demo.core.model.CustomScene;
import maven.demo.core.model.elements.CustomCube;
import maven.demo.core.model.elements.CustomSphere;
import maven.demo.gui.Part;
import maven.demo.gui.utils.Changes;
import maven.demo.gui.utils.Images;
import maven.demo.gui.utils.Layouts;

public final class LeftPart extends Part {
	
	private final Label sceneLabel = new Label("Szenen");
	private final Label elementLabel = new Label("Elemente");

	private final Button sceneAdd = new Button(null, Images.load("add.png"));
	private final Button elementAdd = new Button(null, Images.load("add.png"));
	
	private final Button sceneRemove = new Button(null, Images.load("remove.png"));
	private final Button elementRemove = new Button(null, Images.load("remove.png"));
	
	private final ToolBar sceneBar = new ToolBar(sceneLabel, sceneAdd, sceneRemove, new Button(null, Images.load("help.png")));
	private final ToolBar elementBar = new ToolBar(elementLabel, elementAdd, elementRemove, new Button(null, Images.load("help.png")));
	
	private final ListView<CustomScene> sceneList = new ListView<>();
	private final ListView<CustomElement> elementList = new ListView<>();
	
	private final BorderPane top = new BorderPane(sceneList, sceneBar, null, null, null);
	private final BorderPane bottom = new BorderPane(elementList, elementBar, null, null, null);
	
	private final GridPane grid = new GridPane();

	private final Alert sceneRemoveAlert = new Alert(AlertType.CONFIRMATION);
	private final Alert elementRemoveAlert = new Alert(AlertType.CONFIRMATION);
	
	private final ChoiceDialog<String> elementAddDialog = new ChoiceDialog<>("Würfel", "Würfel", "Kugel", "Zylinder");
	
	public LeftPart() {
		sceneList.setCellFactory(row -> {
			return new ListCell<>() {
				@Override
				public void updateItem(CustomScene scene, boolean editing) {
					// Rufe die Implementierung in der Basisklasse auf
					super.updateItem(scene, editing);
					
					// Prüfe ob Szene oder leere Zeile
					if (scene != null) {
						// Binde den Zellentext an den Szenennamen
						textProperty().bind(scene.nameProperty());
						
						// Lade das Szenensymbol
						ImageView image = Images.load("scene.png");;
						
						// Setze das Szenensymbol als Zellengrafik
						setGraphic(image);
					} else {
						// Löse die Bindung des Zellentexts
						textProperty().unbind();
						
						// Setze den Zellentext zurück
						setText(null);
						
						// Setze die Zellengrafik zurück
						setGraphic(null);
					}
				}
			};
		});
		sceneList.getSelectionModel().selectedItemProperty().addListener(event -> {
			// Ausgewählte Szene bestimmen
			CustomScene scene = sceneList.getSelectionModel().getSelectedItem();
			
			// Elemente der vorigen Szene entfernen
			elementList.getItems().clear();
			
			// Prüfen, ob tatsächlich eine Szene ausgewählt ist
			if (scene != null) {
				// Elemente der aktuellen Szene anzeigen
				elementList.getItems().addAll(scene.getElements());
			}
			
			// Selektiertes Objekt ändern
			setSelectedObject(scene);
		});
		sceneList.focusedProperty().addListener(event -> {
			if (sceneList.isFocused()) {
				// Ausgewählte Szene bestimmen
				CustomScene scene = sceneList.getSelectionModel().getSelectedItem();
				
				// Selektiertes Objekt ändern
				setSelectedObject(scene);
			}
		});
		
		elementList.setCellFactory(row -> {
			return new ListCell<>() {
				@Override
				public void updateItem(CustomElement element, boolean editing) {
					// Rufe die Implementierung in der Basisklasse auf
					super.updateItem(element, editing);
					
					// Prüfe ob Element oder leere Zeile
					if (element != null) {
						// Binde den Zellentext an den Elementnamen
						textProperty().bind(element.nameProperty());
						
						// Prüfe den Elementtyp
						if (element instanceof CustomCube) {
							// Lade das Würfelsymbol
							ImageView image = Images.load("cube.png");
							
							// Setze das Würfelsymbol als Zellengrafik
							setGraphic(image);
						} else if (element instanceof CustomSphere) {
							// Lade das Kugelsymbol
							ImageView image = Images.load("sphere.png");

							// Setze das Kugelsymbol als Zellengrafik
							setGraphic(image);
						} else {
							// TODO Unterstütze auch Zylinder
							
							// Informiere den Entwickler über das Problem
							throw new IllegalStateException("Element type not supported!");
						}
					} else {
						// Löse die Bindung des Zellentext
						textProperty().unbind();
						
						// Setze den Zellentext zurück
						setText(null);
						
						// Setze die Zellengrafik zurück
						setGraphic(null);
					}
				}
			};
		});
		elementList.getSelectionModel().selectedItemProperty().addListener(event -> {
			// Ausgewähltes Element bestimmen
			CustomElement element = elementList.getSelectionModel().getSelectedItem();
			
			// Selektiertes Objekt ändern
			setSelectedObject(element);
		});
		elementList.focusedProperty().addListener(event -> {
			if (elementList.isFocused()) {
				// Ausgewähltes Element bestimmen
				CustomElement element = elementList.getSelectionModel().getSelectedItem();
				
				// Selektiertes Objekt ändern
				setSelectedObject(element);
			}
		});
		
		sceneAdd.setOnAction(event -> {
			// Szenen-Objekt erstellen
			CustomScene scene = new CustomScene();
			scene.setName("Neue Szene");
			
			// Szene in Datenbank eintragen
			getSession().beginTransaction();
			getSession().persist(scene);
			getSession().getTransaction().commit();
			
			// Änderungen an Szene verfolgen
			Changes.track(getSession(), scene);
			
			// Szene in GUI-Komponente eintragen
			sceneList.getItems().add(scene);
			
			// Szene selektieren
			sceneList.getSelectionModel().select(scene);
		});
		
		sceneRemove.disableProperty().bind(sceneList.getSelectionModel().selectedItemProperty().isNull());
		sceneRemove.setOnAction(event -> {
			sceneRemoveAlert.showAndWait().ifPresent(action -> {
				if (action == ButtonType.OK) {
					// Ausgewählte Szene bestimmen
					CustomScene scene = sceneList.getSelectionModel().getSelectedItem();
					
					// Änderungen an Szene nicht mehr verfolgen
					Changes.untrack(scene);
					
					// Szene aus der Datenbank löschen
					getSession().beginTransaction();
					getSession().remove(scene);
					getSession().getTransaction().commit();
					
					// Szene aus der GUI-Komponente löschen
					sceneList.getItems().remove(scene);
				}
			});
		});
		
		elementAdd.disableProperty().bind(sceneList.getSelectionModel().selectedItemProperty().isNull());
		elementAdd.setOnAction(event -> {
			elementAddDialog.showAndWait().ifPresent(choice -> {
				CustomElement element; 
				
				if (choice.equals("Würfel")) {
					// Würfel-Objekt erstellen
					CustomCube cube = new CustomCube();
					cube.setName("Neuer Würfel");
					
					element = cube;
				} else if (choice.equals("Kugel")) {
					// Kugel-Objekt erstellen
					CustomSphere sphere = new CustomSphere();
					sphere.setName("Neue Kugel");
					
					element = sphere;
				} else {
					// TODO Unterstütze auch Zylinder
					
					// Informiere den Entwickler über das Problem
					throw new IllegalStateException("Element type not supported!");
				}
				
				// Ausgewählte Szene bestimmen
				CustomScene scene = sceneList.getSelectionModel().getSelectedItem();
				
				// Element sauber verknüpfen
				element.setScene(scene);
				
				scene.getElements().add(element);
				
				// Element in die Datenbank schreiben
				getSession().beginTransaction();
				getSession().persist(element);
				getSession().getTransaction().commit();
				
				// Änderungen an Element nachverfolgen
				Changes.track(getSession(), element);
				
				// Element zur GUI-Komponente hinzufügen
				elementList.getItems().add(element);
				
				// Element selektieren
				elementList.getSelectionModel().select(element);
			});
		});
		
		elementRemove.disableProperty().bind(elementList.getSelectionModel().selectedItemProperty().isNull());
		elementRemove.setOnAction(event -> {
			elementRemoveAlert.showAndWait().ifPresent(action -> {
				if (action == ButtonType.OK) {
					// Ausgewählte Szene bestimmen
					CustomScene scene = sceneList.getSelectionModel().getSelectedItem();
					
					// Ausgewähltes Element bestimmen
					CustomElement element = elementList.getSelectionModel().getSelectedItem();
					
					// Änderungen an Element nicht mehr nachverfolgen
					Changes.untrack(element);
					
					// Element aus der Datenbank löschen
					getSession().beginTransaction();
					getSession().remove(element);
					getSession().getTransaction().commit();
					
					// Verknüpfung zu Szene aufheben
					element.setScene(null);
					
					scene.getElements().remove(element);
					
					// Element aus der GUI-Komponente löschen
					elementList.getItems().remove(element);
				}
			});
		});
		
		sceneRemoveAlert.setTitle("Szene löschen");
		sceneRemoveAlert.setHeaderText("Willst du die Szene wirklich löschen?");
		sceneRemoveAlert.setContentText("Bitte entscheide dich:");
		
		elementRemoveAlert.setTitle("Element löschen");
		elementRemoveAlert.setHeaderText("Willst du das Element wirklich löschen?");
		elementRemoveAlert.setContentText("Bitte entscheide dich:");
		
		elementAddDialog.setTitle("Element hinzufügen");
		elementAddDialog.setHeaderText("Welche Form willst du hinzufügen?");
		elementAddDialog.setContentText("Bitte wähle eine Form:");
		
		top.prefWidthProperty().bind(grid.widthProperty());
		bottom.prefWidthProperty().bind(grid.widthProperty());
		
		grid.getRowConstraints().add(Layouts.createRowContraints(50));
		grid.getRowConstraints().add(Layouts.createRowContraints(50));
		grid.add(top, 0, 0);
		grid.add(bottom, 0, 1);
		grid.prefWidthProperty().bind(widthProperty());
		
		setCenter(grid);

		sessionProperty().addListener(event -> {
			// Leere die Szenenliste
			sceneList.getItems().clear();
			
			// Prüfe, ob eine Hibernate Session übergeben wurde
			if (getSession() != null) {
				// Lade die Szenen aus der Datenbank
				List<CustomScene> scenes = getSession().createQuery("from CustomScene", CustomScene.class).getResultList();
				
				// Verfolge Änderungen an Szenen und Elementen
				Platform.runLater(() -> {
					for (CustomScene scene : scenes) {
						Changes.track(getSession(), scene);
						for (CustomElement element : scene.getElements()) {
							Changes.track(getSession(), element);
						}
					}
				});
				
				// Füge die Szenen in die GUI hinzu
				sceneList.getItems().addAll(scenes);
			}
		});
		
		selectedSceneProperty().bind(sceneList.getSelectionModel().selectedItemProperty());
		selectedElementProperty().bind(elementList.getSelectionModel().selectedItemProperty());
	}
	
}
