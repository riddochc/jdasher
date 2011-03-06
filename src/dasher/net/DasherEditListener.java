/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.net;

/**
 *
 * @author joshua
 */
/**
 * Interface to be implemented by any class wanting to play
 * host to NetDashers and be notified of edit events.
 */
interface DasherEditListener {

	/**
	 * Called by NetDasher whenever an EditEvent is raised.
	 *
	 * @param event Event being raised
	 */
	public void HandleEvent(dasher.events.CEditEvent event);

}
