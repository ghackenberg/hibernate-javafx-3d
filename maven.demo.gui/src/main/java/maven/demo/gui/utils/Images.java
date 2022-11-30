package maven.demo.gui.utils;

import javafx.scene.image.ImageView;

public final class Images {
	
	public static ImageView load(String url) {
		return load(url, 16, 16);
	}

	public static ImageView load(String url, double width, double height) {
		ImageView result = new ImageView(url);
		result.setFitWidth(width);
		result.setFitHeight(height);
		
		return result;
	}
	
}
