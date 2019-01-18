package informatik.hilfsmittel.gui;


/**
 * Ein Generator lieﬂt immer eine Definitionsdatei ein und setzt die Werte in 
 * ein Template (eine Vorlage) ein. Das Ergebnis ist der Quelltext einer 
 * Java-Klasse.
 * 
 * @author Axel
 */
public abstract class AbstractGenerator {

	public abstract String generate(String definition, String vorlage);

	
}
