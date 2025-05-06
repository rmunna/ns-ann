
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DeduplicationService {
    private static final String DEDUPLICATION_FILE = "deduplication.txt";
    private String lastMessageContent = null;

    public DeduplicationService() {
        loadLastMessage();
    }

    public boolean isDuplicate(String currentMessage) {
        if (currentMessage.equals(lastMessageContent)) {
            return true;
        }
        lastMessageContent = currentMessage;
        saveLastMessage();
        return false;
    }

    private void loadLastMessage() {
        try {
            if (Files.exists(Paths.get(DEDUPLICATION_FILE))) {
                lastMessageContent = new String(Files.readAllBytes(Paths.get(DEDUPLICATION_FILE)));
            }
        } catch (IOException e) {
            System.err.println("Failed to load last message: " + e.getMessage());
        }
    }

    private void saveLastMessage() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEDUPLICATION_FILE))) {
            writer.write(lastMessageContent);
        } catch (IOException e) {
            System.err.println("Failed to save the last message: " + e.getMessage());
        }
    }
}
