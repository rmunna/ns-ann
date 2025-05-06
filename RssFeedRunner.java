import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RssFeedRunner implements CommandLineRunner {
    private final SchedulerService schedulerService;

    public RssFeedRunner(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public void run(String... args) {
        if (args.length > 0 && args[0].equals("processRssFeed")) {
            schedulerService.processRssFeed();
        }
    }
}
