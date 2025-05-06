
import RssFetcherService;
import DeduplicationService;
import WhatsAppService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerService {
    private final RssFetcherService rssFetcherService;
    private final DeduplicationService deduplicationService;
    private final WhatsAppService whatsAppService;

    public SchedulerService(RssFetcherService rssFetcherService, DeduplicationService deduplicationService, WhatsAppService whatsAppService) {
        this.rssFetcherService = rssFetcherService;
        this.deduplicationService = deduplicationService;
        this.whatsAppService = whatsAppService;
    }

    public void processRssFeed() {
        List<RssItem> rssItems = rssFetcherService.fetchRssFeed();
        for (RssItem item : rssItems) {
            String message = "Title: " + item.getTitle() + "\n" +
                             "Link: " + item.getLink() + "\n" +
                             "Published On: " + item.getPubDate();
            if (!deduplicationService.isDuplicate(message)) {
                whatsAppService.sendMessage(message);
            }
        }
    }
}
