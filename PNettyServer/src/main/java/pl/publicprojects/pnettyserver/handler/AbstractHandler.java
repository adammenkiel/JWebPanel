package pl.publicprojects.pnettyserver.handler;

import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pnettyserver.session.Session;

/**
 * Interface for implements packet handlers at another places in code
 */
public interface AbstractHandler {
    void handle(Session session, Packet packet);
}
