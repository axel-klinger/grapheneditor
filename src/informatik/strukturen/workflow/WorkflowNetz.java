
package informatik.strukturen.workflow;

import informatik.petrinetze.BedingungsEreignisNetz;

/**
 *	Ein <b>Workflow-Graph</b> ist (nach v.d.Aalst) ...
 */
public class WorkflowNetz extends BedingungsEreignisNetz {

	public WorkflowNetz() {
		super();
	}
	
//	public WorkflowNetz(BipartiterGraph<Stelle, Transition> bg) {
//		super(bg);
//
//	}

	public boolean isConsistent() {
		return false;
	}
	
	public boolean isLive() {
		return false;
	}
}
