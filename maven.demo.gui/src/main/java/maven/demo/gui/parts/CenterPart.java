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

public final class CenterPart extends Part implements ListChangeListener<CustomElement> {
	
	private final Label label = new Label("Bild");
	
	private final ToolBar bar = new ToolBar(label, new Button(null, Images.load("help.png")));
	
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
	
	private final Group scene = new Group();
	
	private final Group root = new Group(camera, scene);
	
	private final SubScene canvas = new SubScene(root, 100, 100);
	
	private final Pane pane = new Pane(canvas);
	
	public CenterPart() {
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		camera.getTransforms().add(new Rotate(45, Rotate.Y_AXIS));
		camera.getTransforms().add(new Rotate(-22.5, Rotate.X_AXIS));
		camera.getTransforms().add(new Translate(0, 0, -100));
		
		canvas.setFill(Color.GRAY);
		canvas.setCamera(camera);
		canvas.setDepthTest(DepthTest.ENABLE);
		canvas.widthProperty().bind(pane.widthProperty());
		canvas.heightProperty().bind(pane.heightProperty());
		
		setTop(bar);
		setCenter(pane);
		
		selectedSceneProperty().addListener((observable, oldScene, newScene) -> {
			scene.getChildren().clear();
			if (oldScene != null) {
				oldScene.elementsProperty().removeListener(this);
			}
			if (newScene != null) {
				for (CustomElement element : newScene.getElements()) {
					scene.getChildren().add(Shapes.build(element));
				}
				newScene.elementsProperty().addListener(this);
			}
		});
	}

	@Override
	public void onChanged(Change<? extends CustomElement> c) {
		scene.getChildren().clear();
		for (CustomElement element : getSelectedScene().getElements()) {
			scene.getChildren().add(Shapes.build(element));
		}
	}

}
