package grapheneditor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String GERMAN  = "DE";
	private static final String ENGLISH = "EN";
	private static final String SPANISH = "SP";
	private static final String RUSSIAN = "RU";
	
	private static String sprache = "EN";
	// Local (Java Isel 10.4) beachten!!!
	// ...
	
	// ebenso mit Resource Bundle 
	// die Einstellungen des Programms speichern!!!
	// - Sprache
	// - Anzeige: Grid, Snap, Props, Labels, Backgrd, ...
	// - Symbolgröße, Schriftgröße
	// - Vorlagenverzeichnis, Datenverzeichnis
	// - Recent Files
	
	private static String BUNDLE_NAME = "grapheneditor.messages" + sprache; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static void setLanguage(String language) {
		sprache = language;
		BUNDLE_NAME = "pme.messages" + sprache;
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}
}
