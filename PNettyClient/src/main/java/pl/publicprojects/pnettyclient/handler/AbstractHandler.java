package pl.publicprojects.pnettyclient.handler;

import pl.publicprojects.pcommon.protocol.packet.Packet;

public interface AbstractHandler {
    void handle(Packet packet);
}
