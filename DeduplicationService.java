import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DeduplicationService {
    private static final String LAST_MESSAGE_FILE = "last-message.txt";
    private String lastMessageContent = null;

    public DeduplicationService() {
        loadLastMessage();
    }

    public boolean isDuplicate(String currentMessage) {
        // If the current message matches the last message content, it's a duplicate
        if (currentMessage.equals(lastMessageContent)) {
            return true;
        }

        // Update the last message and save it to the file
        lastMessageContent = currentMessage;
        saveLastMessage();
        return false;
    }

    private void loadLastMessage() {
        try {
            if (Files.exists(Paths.get(LAST_MESSAGE_FILE))) {
                lastMessageContent = new String(Files.readAllBytes(Paths.get(LAST_MESSAGE_FILE)));
            }
        } catch (IOException e) {
            System.err.println("Failed to load last message: " + e.getMessage());
        }
    }

    private void saveLastMessage() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_MESSAGE_FILE))) {
            writer.write(lastMessageContent);
        } catch (IOException e) {
            System.err.println("Failed to save last message: " + e.getMessage());
        }
    }
}
