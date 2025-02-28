package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

@SpringBootApplication
public class SSLServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSLServerApplication.class, args);
    }
}

@RestController
@RequestMapping("/hash")
class HashController {

    @GetMapping
    public Map<String, String> generateChecksum() {
        String uniqueString = "Bernard Van Housten"; // Replace with your actual name
        String algorithm = "SHA-256"; // The algorithm used for hashing

        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            
            // Generate the hash (checksum)
            byte[] hashBytes = digest.digest(uniqueString.getBytes(StandardCharsets.UTF_8));

            // Convert to hexadecimal format
            String checksum = bytesToHex(hashBytes);

            // Return the structured JSON response
            Map<String, String> response = new HashMap<>();
            response.put("name", uniqueString);
            response.put("algorithm", algorithm);
            response.put("checksum", checksum);

            return response;

        } catch (NoSuchAlgorithmException e) {
            return Collections.singletonMap("error", "Error generating checksum: " + e.getMessage());
        }
    }

    // Helper function to convert bytes to hex
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
