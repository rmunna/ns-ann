import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessor {
    private final DeduplicationService deduplicationService;

    @Autowired
    public MessageProcessor(DeduplicationService deduplicationService) {
        this.deduplicationService = deduplicationService;
    }

    public void processMessages(List<String> messages) {
        for (String message : messages) {
            // Check if the message is a duplicate of the previous one
            if (!deduplicationService.isDuplicate(message)) {
                sendMessage(message);
            } else {
                System.out.println("Duplicate message skipped: " + message);
            }
        }
    }

    private void sendMessage(String message) {
        // Logic to send the message (e.g., via WhatsApp API)
        System.out.println("Sending message: " + message);
    }
}
