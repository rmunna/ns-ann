package com.example.nsann.scheduler;

import com.example.nsann.service.RssFetcherService;
import com.example.nsann.service.DeduplicationService;
import com.example.nsann.service.WhatsAppService;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(fixedRate = 60000) // Runs every 1 minute
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
