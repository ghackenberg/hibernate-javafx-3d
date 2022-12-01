package maven.demo.gui.parts;

import javafx.collections.ListChangeListener;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import maven.demo.core.model.CustomElement;
import maven.demo.gui.Part;
import maven.demo.gui.utils.Images;
import maven.demo.gui.utils.Shapes;

/**
 * Darstellung der Szenenobjekte als 3D Bild.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
public final class CenterPart extends Part implements ListChangeListener<CustomElement> {
	
	private final Label label = new Label("Bild");
	
	private final ToolBar bar = new ToolBar(label, new Button(null, Images.load("help.png")));
	
	private final Rotate cameraRotateY = new Rotate(45, Rotate.Y_AXIS);
	private final Rotate cameraRotateX = new Rotate(-22.5, Rotate.X_AXIS);
	
	private final Translate cameraTranslate = new Translate(0,  0, -100);
	
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
	
	private final Group scene = new Group();
	
	private final Group root = new Group(camera, scene);
	
	private final SubScene canvas = new SubScene(root, 100, 100);
	
	private final Pane pane = new Pane(canvas);
	
	public CenterPart() {
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		camera.getTransforms().add(cameraRotateY);
		camera.getTransforms().add(cameraRotateX);
		camera.getTransforms().add(cameraTranslate);
		
		canvas.setFill(Color.GRAY);
		canvas.setCamera(camera);
		canvas.setDepthTest(DepthTest.ENABLE);
		canvas.widthProperty().bind(pane.widthProperty());
		canvas.heightProperty().bind(pane.heightProperty());
		
		setTop(bar);
		setCenter(pane);
		
		selectedSceneProperty().addListener((observable, oldScene, newScene) -> {
			// Bindung der Schnittdistanzen auflösen
			camera.nearClipProperty().unbind();
			camera.farClipProperty().unbind();
			
			// Bindung der Rotationswinkel für die Kamera auflösen
			cameraRotateY.angleProperty().unbind();
			cameraRotateX.angleProperty().unbind();
			
			// Bindung des Verschiebungsvektors für die Kamera auflösen
			cameraTranslate.xProperty().unbind();
			cameraTranslate.yProperty().unbind();
			cameraTranslate.zProperty().unbind();
			
			// Szenenelemente der vorigen Szene entfernen
			scene.getChildren().clear();
			
			// War bereits eine vorige Szene angezeigt?
			if (oldScene != null) {
				// Änderungen an Szenenelementen der vorigen Szene nicht mehr überwachen
				oldScene.elementsProperty().removeListener(this);
			}
			
			// Soll eine neue Szene angezeigt werden?
			if (newScene != null) {
				// Szenenelement der aktuellen Szene in JavaFX 3D Formen übersetzen und der Anzeige hinzufügen
				for (CustomElement element : newScene.getElements()) {
					// In JavaFX 3D Form übersetzen
					Group group = Shapes.build(element);
					// Die Form der Anzeige hinzufügen
					scene.getChildren().add(group);
				}
				
				// Hinzufügen und Löschen von Szenenelementen überwachen
				newScene.elementsProperty().addListener(this);
				
				// TODO Kameraeigenschaften an die Eigenschaften der neuen Szene binden
			}
		});
		selectedElementProperty().addListener((observable, oldElement, newElement) -> {
			// TODO Materialeigenschaften des vorherig selektierten Elements zurücksetzen
			// TODO Materialeigenschaften des aktuell selektierten Elements ändern
		});
	}

	@Override
	public void onChanged(Change<? extends CustomElement> c) {
		// Szenenelemente der vorigen Szene entfernen
		scene.getChildren().clear();
		// Szenenelement der aktuellen Szene in JavaFX 3D Formen übersetzen und der Anzeige hinzufügen
		for (CustomElement element : getSelectedScene().getElements()) {
			// In JavaFX 3D Form übersetzen
			Group group = Shapes.build(element);
			// Die Form der Anzeige hinzufügen
			scene.getChildren().add(group);
		}
	}

}
