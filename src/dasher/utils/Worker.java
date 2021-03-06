/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jdesktop.swingworker.SwingWorker;

public abstract class Worker<T,V> extends SwingWorker<T,V> {

    Set<WorkerListener<T,V>> listeners = new HashSet<WorkerListener<T,V>>();

    public void addWorkerListener(WorkerListener<T,V> lis) {
        listeners.add(lis);
    }

    public void removeWorkerListener(WorkerListener<T,V> lis) {
        listeners.remove(lis);
    }

    @Override
    protected void process(List<V> chunks) {
        PublishEvent<T,V> evt = new PublishEvent<T, V>(this, chunks);
        for (WorkerListener<T,V> l : listeners)
            l.workerPublish(evt);
    }
    
    @Override
    protected void done() {
        DoneEvent<T,V> evt = new DoneEvent<T, V>(this);
        for (WorkerListener<T,V> l : listeners)
            l.workerDone(evt);
    }

    public static class WorkerEvent<T,V> {
        Worker<T,V> worker;
        public WorkerEvent(Worker<T,V> worker) {
            this.worker = worker;
        }
        public Worker<T,V> getWorker() {
            return worker;
        }
    }
    
    public static class DoneEvent<T,V> extends WorkerEvent {
        public DoneEvent(Worker<T,V> worker) {
            super(worker);
        }
    }
    
    public static class PublishEvent<T,V> extends WorkerEvent {
        List<V> chunks;
        
        public PublishEvent(Worker<T,V> worker, List<V> chunks) {
            super(worker);
            this.chunks = chunks;
        }
        public List<V> getChunks() {
            return chunks;
        }
    }

    public static interface WorkerListener<T,V> {
        public void workerDone(DoneEvent<T,V> event);
        public void workerPublish(PublishEvent<T,V> event);
    }
}
