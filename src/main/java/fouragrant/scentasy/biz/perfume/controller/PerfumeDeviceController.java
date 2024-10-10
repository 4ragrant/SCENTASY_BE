package fouragrant.scentasy.biz.perfume.controller;

import fouragrant.scentasy.biz.perfume.service.PerfumeDeviceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Perfume Device", description = "향수 기기 관련 API")
@RestController
@RequestMapping("/api/perfume/device")
public class PerfumeDeviceController {

    private final String ip;
    private final PerfumeDeviceService perfumeDeviceService;

    @Autowired
    public PerfumeDeviceController(
            @Value("${raspberrypi.ip}") String ip,
            PerfumeDeviceService perfumeDeviceService) {
        this.ip = ip;
        this.perfumeDeviceService = perfumeDeviceService;
    }

    @GetMapping("/{perfumeId}")
    public String sendPerfumeDataToRaspberryPi(@PathVariable Long perfumeId) {
        int raspberryPiPort = 8080; // Raspberry Pi 포트 번호

        try {
            // Perfume 데이터를 perfumeId로 조회
            String arrayData = perfumeDeviceService.getArrayByPerfumeId(perfumeId);
            if (arrayData == null) {
                return "No data found for the given perfumeId: " + perfumeId;
            }

            // TCP 통신을 통해 Raspberry Pi로 전송
            try (Socket socket = new Socket(ip, raspberryPiPort);
                 OutputStream outputStream = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(outputStream, true)) {

                writer.println(arrayData);
                return "Message sent to Raspberry Pi: " + arrayData;

            } catch (Exception e) {
                log.error("Failed to connect to Raspberry Pi", e);
                return "Failed to connect to Raspberry Pi";
            }

        } catch (Exception e) {
            log.error("Error occurred while retrieving perfume data", e);
            return "Error occurred while retrieving perfume data";
        }
    }
}
