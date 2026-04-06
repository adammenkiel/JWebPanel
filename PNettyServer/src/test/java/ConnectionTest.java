import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionTest {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());
        stream.writeInt(4);
        stream.writeInt(0);
        while(true) {}
    }
}
