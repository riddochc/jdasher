/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.core;

/**
 *
 * @author joshua
 */
/**
 * Interface to be implemented by classes which wish to be
 * notified of the completion of asynchronous getProbs requests
 * by CRemotePPM.
 * <p>
 * To actually receive notifications, one must also register with
 * the LanguageModel using its RegisterProbsListener method.
 */
public interface ProbsListener {

	public void probsArrived(long[] probs);

}