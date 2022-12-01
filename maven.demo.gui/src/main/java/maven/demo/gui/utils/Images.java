package maven.demo.gui.utils;

import javafx.scene.image.ImageView;

/**
 * Hilfsfunktionen für das Laden von Bildern.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
public final class Images {
	
	/**
	 * Bild in einer Standardgröße laden.
	 * 
	 * @param url Pfad des Bildes.
	 * 
	 * @return Das skalierte Bildobjekt.
	 */
	public static ImageView load(String url) {
		return load(url, 16, 16);
	}

	/**
	 * Bild in einer definierten Größe laden.
	 * 
	 * @param url Pfad des Bildes.
	 * @param width Beite des Bildes.
	 * @param height Höhe des Bildes.
	 * 
	 * @return Das skalierte Bildobjekt.
	 */
	public static ImageView load(String url, double width, double height) {
		ImageView result = new ImageView(url);
		result.setFitWidth(width);
		result.setFitHeight(height);
		
		return result;
	}
	
}
