package maven.demo.gui.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import maven.demo.core.model.CustomElement;
import maven.demo.core.model.elements.CustomCube;
import maven.demo.core.model.elements.CustomSphere;

public final class Shapes {
	
	private static Map<CustomElement, Group> cache = new HashMap<>();

	public static Group build(CustomElement element) {
		if (!cache.containsKey(element)) {
			PhongMaterial material = new PhongMaterial(Color.rgb(element.getRed(), element.getGreen(), element.getBlue()));
			element.redProperty().addListener(nested -> {
				material.setDiffuseColor(Color.rgb(element.getRed(), element.getGreen(), element.getBlue()));
			});
			element.greenProperty().addListener(nested -> {
				material.setDiffuseColor(Color.rgb(element.getRed(), element.getGreen(), element.getBlue()));
			});
			element.blueProperty().addListener(nested -> {
				material.setDiffuseColor(Color.rgb(element.getRed(), element.getGreen(), element.getBlue()));
			});
			
			Shape3D shape;
			
			if (element instanceof CustomCube) {
				shape = build((CustomCube) element);
			} else if (element instanceof CustomSphere) {
				shape = build((CustomSphere) element);
			} else {
				// TODO Unterstütze auch Zylinder
				throw new IllegalStateException("Element type not supported!");
			}
			
			shape.setMaterial(material);
			shape.setDrawMode(DrawMode.FILL);
			shape.setDepthTest(DepthTest.ENABLE);
			
			Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
			rotateX.angleProperty().bind(element.alphaProperty());
			
			Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
			rotateY.angleProperty().bind(element.betaProperty());
			
			Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
			rotateZ.angleProperty().bind(element.gammaProperty());
			
			Translate translate = new Translate();
			translate.xProperty().bind(element.xProperty());
			translate.yProperty().bind(element.yProperty());
			translate.zProperty().bind(element.zProperty());
			
			Group group = new Group(shape);
			group.getTransforms().add(translate);
			group.getTransforms().add(rotateZ);
			group.getTransforms().add(rotateY);
			group.getTransforms().add(rotateX);
			group.setUserData(element);
			
			cache.put(element, group);
		}
		return cache.get(element);
	}
	
	private static Shape3D build(CustomCube cube) {
		Box shape = new Box();
		shape.widthProperty().bind(cube.widthProperty());
		shape.heightProperty().bind(cube.heightProperty());
		shape.depthProperty().bind(cube.lengthProperty());
		
		return shape;
	}
	
	private static Shape3D build(CustomSphere sphere) {
		Sphere shape = new Sphere(0, 100);
		shape.radiusProperty().bind(sphere.radiusProperty());
		
		return shape;
	}
	
}
