package maven.demo.gui.parts;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import maven.demo.gui.Part;
import maven.demo.gui.utils.Images;

public final class CenterPart extends Part {
	
	private final Label label = new Label("Bild");
	
	private final ToolBar bar = new ToolBar(label, new Button(null, Images.load("help.png")));
	
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
	
	private final Group root = new Group(camera);
	
	private final SubScene scene = new SubScene(root, 100, 100);
	
	private final Pane pane = new Pane(scene);
	
	public CenterPart() {
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		camera.getTransforms().add(new Rotate(45, Rotate.Y_AXIS));
		camera.getTransforms().add(new Rotate(-22.5, Rotate.X_AXIS));
		camera.getTransforms().add(new Translate(0, 0, -100));
		
		Box box1 = new Box(5, 5, 5);
		box1.setMaterial(new PhongMaterial(Color.RED));
		box1.setDrawMode(DrawMode.FILL);
		box1.getTransforms().add(new Translate(-10, 0, 0));
		
		Box box2 = new Box(5, 5, 5);
		box2.setMaterial(new PhongMaterial(Color.GREEN));
		box2.setDrawMode(DrawMode.FILL);
		box2.getTransforms().add(new Translate(0, 0, 0));
		
		Box box3 = new Box(5, 5, 5);
		box3.setMaterial(new PhongMaterial(Color.BLUE));
		box3.setDrawMode(DrawMode.FILL);
		box3.getTransforms().add(new Translate(10, 0, 0));
		
		root.getChildren().add(box1);
		root.getChildren().add(box2);
		root.getChildren().add(box3);
		
		scene.setFill(Color.GRAY);
		scene.setCamera(camera);
		scene.widthProperty().bind(pane.widthProperty());
		scene.heightProperty().bind(pane.heightProperty());
		
		setTop(bar);
		setCenter(pane);
	}

}
