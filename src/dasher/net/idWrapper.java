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
 * Responsible for supplying free IDs to use as session IDs.
 * <p>
 * At present, simply returns a linear sequence and does not
 * re-use abandoned session IDs.
 */
public class idWrapper {
	int nextID = 0;

	public int getFreeID() {
		return nextID++;
	}
}
