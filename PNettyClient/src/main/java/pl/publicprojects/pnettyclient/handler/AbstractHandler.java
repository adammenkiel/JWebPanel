package pl.publicprojects.pnettyclient.handler;

import pl.publicprojects.pcommon.protocol.packet.Packet;

/**
 * Interface for implements packet handlers at another places in code
 */
public interface AbstractHandler {
    void handle(Packet packet);
}
