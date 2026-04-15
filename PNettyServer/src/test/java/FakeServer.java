import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FakeServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9876);
        Socket firstS = serverSocket.accept();

        while(true) {
            System.out.println(firstS.getInputStream().read());
        }

    }
}
