package informatik.strukturen.graphen;

/**
 * <P><B>Netz</B> <I>(engl. net)</I>: Ein Netz ist ein 
 * zusammenhängender bewerteter Graph mit einer Quelle 
 * und einer Senke. Eine Quelle ist ein Knoten ohne 
 * Vorgänger und eine Senke ist ein Knoten ohne Nachfolger.</P>  
 * 
 * @author Axel
 *
 */
public class Netz<K> extends GewichteterGraph<K> {
    
    public Netz(int werteProKnoten, int werteProKante) {
        super(werteProKnoten, werteProKante);
    }

    // Auslagern in hilfsmittel.NetzEigenschaften
    private Double[][] getInzidenzMatrix() {
        Double[][] S = new Double[anzahlKnoten()][anzahlKanten()];
        
        return S;
    }
    
    public Double maximalerFluss() {
    
        return new Double(0.0);
    }
    
    public Double maximalerFlussMinimaleKosten() {
        
        return new Double(0.0);
    }
        
}
