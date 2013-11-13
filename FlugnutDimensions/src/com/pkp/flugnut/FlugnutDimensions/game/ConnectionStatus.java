package com.pkp.flugnut.FlugnutDimensions.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/9/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionStatus {

    private Status status;

    public enum Status {
        DISCONNECTED, CONNECTED, CONNECTING, CONNECTION_ERROR, CONNECTION_LOST, LOGGED, IN_A_ROOM
    }

    public ConnectionStatus() {
        status = Status.DISCONNECTED;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (this.status != status) {
            Status oldStatus = status;
            this.status = status;
            notifyListeners(this, "status", oldStatus.name(), status.name());
        }
    }


    /**
     * Listen for change
     */
    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(Object object, String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }
}
