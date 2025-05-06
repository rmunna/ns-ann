import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class DeduplicationService {
    private static final String DEDUPLICATION_FILE = "deduplication.txt";
    private Set<String> processedMessages = new HashSet<>();

    public DeduplicationService() {
        loadProcessedMessages();
    }

    public boolean isDuplicate(String title, String pubDate) {
        // Create a unique identifier for the message based on title and pubDate
        String uniqueMessageKey = title + "|" + pubDate;
        if (processedMessages.contains(uniqueMessageKey)) {
            return true;
        }
        processedMessages.add(uniqueMessageKey);
        saveProcessedMessages();
        return false;
    }

    private void loadProcessedMessages() {
        try {
            if (Files.exists(Paths.get(DEDUPLICATION_FILE))) {
                processedMessages.addAll(Files.readAllLines(Paths.get(DEDUPLICATION_FILE)));
            }
        } catch (IOException e) {
            System.err.println("Failed to load processed messages: " + e.getMessage());
        }
    }

    private void saveProcessedMessages() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEDUPLICATION_FILE))) {
            for (String message : processedMessages) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save processed messages: " + e.getMessage());
        }
    }
}
