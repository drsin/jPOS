package org.jpos.iso;


import org.jpos.util.*;
import org.jpos.iso.channel.*;
import org.jpos.iso.packager.*;

import java.io.IOException;

public class Test  implements ISORequestListener{
    public Test () {
        super();
    }
    public boolean process (ISOSource source, ISOMsg m) {
        try {
            m.setResponseMTI ();
            m.set (39, "00");
            source.send (m);
        } catch (ISOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void main (String[] args) throws Exception {
        Logger logger = new Logger ();
        logger.addListener (new SimpleLogListener (System.out));
        ServerChannel channel = new XMLChannel (new XMLPackager());
        ((LogSource)channel).setLogger (logger, "channel");
        ISOServer server = new ISOServer (8000, channel, null);
        server.setLogger (logger, "server");
        new Thread (server).start ();
    }
}