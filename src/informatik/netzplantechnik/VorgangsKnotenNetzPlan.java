package informatik.netzplantechnik;

import informatik.strukturen.graphen.GewichteterBipartiterGraph;

/**
 * <P><B>VorgangsKnotenNetzPlan</B> <I>(engl. ...)</I>:
 * Ein VorgangsKnotenNetzPlan ist eine Form der Darstellung eines NetzPlans
 * bei der die Vorg‰nge als Knoten und die ‹berg‰nge als Kanten dargestellt 
 * werden.</P>
 * 
 * @author klinger
 */
public class VorgangsKnotenNetzPlan extends NetzPlanDarstellung {

    
    // (Name)
    // (Beschreibung)
    // Anfangszeitpunkt
    // Bewerteter bipartiter Graph
    private GewichteterBipartiterGraph graph;
    
    public VorgangsKnotenNetzPlan() {
        // Bewertung: 
    	//		besser: minDauer, maxDauer	-> minPuffer, maxPuffer
        //  - Vorgang(dauer, fa, fe, sa, se, pf)
        //  - ‹bergang(folge, versatz)
        //  - KanteV‹(-)
        //  - Kante‹V(-)
        graph = new GewichteterBipartiterGraph(6, 2, 0, 0);
    }
}

// -----------------------------------------------------------------------------
