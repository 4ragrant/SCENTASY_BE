// Spring Controller for TCP communication with Raspberry Pi
package fouragrant.scentasy.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

@RestController
public class TcpTestController  {

    private final String ip;

    @Autowired
    public TcpTestController(
                                @Value("${raspberrypi.ip}") String ip) {
        this.ip = ip;
    }

    @GetMapping("/auth/test-raspberry")
    public String testRaspberryPiCommunication(@RequestParam String data) {
        int raspberryPiPort = 8080; // Raspberry Pi port number
        try (Socket socket = new Socket(ip, raspberryPiPort);
             OutputStream outputStream = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(outputStream, true)) {

            // Send dynamic message
            writer.println(data);
            return "Message sent to Raspberry Pi: " + data;

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to connect to Raspberry Pi";
        }
    }
}