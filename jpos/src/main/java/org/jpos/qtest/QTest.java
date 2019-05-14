package org.jpos.qtest;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.Q2;
import org.jpos.q2.QBean;
import org.jpos.util.Log;
public class QTest implements QTestMBean, Runnable, Configurable {
    volatile int state;
    long tickInterval = 1000;
    Log log;
    boolean debug;
    public QTest () {
        super();
        state = -1;
        log = Log.getLog(Q2.LOGGER_NAME, "qtest");
        log ("constructor");
    }
    public void init () {
        log ("init");
        state = STARTING;
    }
    public void start() {
        log ("start");
        state = STARTED;
        new Thread(this).start();
    }
    public void stop () {
        log ("stop");
        state = STOPPING;
    }
    public void destroy () {
        log ("destroy");
        state = STOPPED;
    }
    public void setTickInterval (long tickInterval) {
        this.tickInterval = tickInterval;
    }
    public long getTickInterval () {
        return tickInterval;
    }
    public void run () {
        for (int tickCount=0; running (); tickCount++) {
            log.info ("tick " + tickCount);
            ISOUtil.sleep (tickInterval);
        }
    }
    public int getState () {
        return state;
    }
    public String getStateAsString () {
        return state >= 0 ? stateString[state] : "Unknown";
    }
    public void setConfiguration (Configuration cfg) {
        debug = cfg.getBoolean("debug", true);
    }
    private boolean running() {
        return state == QBean.STARTING || state == QBean.STARTED;
    }
    private void log (String message) {
        if (debug)
            log (message);
    }
}