import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RssFeedRunner implements CommandLineRunner {
    private final SchedulerService schedulerService;
    private final OnlineAnnouncementsFetcherService onlineAnnouncementsFetcherService;

    public RssFeedRunner(SchedulerService schedulerService, OnlineAnnouncementsFetcherService onlineAnnouncementsFetcherService) {
        this.schedulerService = schedulerService;
        this.onlineAnnouncementsFetcherService = onlineAnnouncementsFetcherService;
    }

    @Override
    public void run(String... args) {
        if (args.length > 0) {
            if (args[0].equals("processRssFeed")) {
                schedulerService.processRssFeed();
            } else if (args[0].equals("fetchOnlineAnnouncements")) {
                onlineAnnouncementsFetcherService.fetchOnlineAnnouncements();
            }
        }
    }
}
