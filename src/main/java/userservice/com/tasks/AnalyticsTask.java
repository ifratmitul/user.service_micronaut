package userservice.com.tasks;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.micronaut.SchedulerLock;
import service.UserService;

@Singleton
@Slf4j
public class AnalyticsTask {

    private final UserService _userService;

    public AnalyticsTask(UserService userService) {
        this._userService = userService;
    }

    @Scheduled(cron = "*/5 * * * * *")
    @SchedulerLock(name="analyticsTask", lockAtLeastFor = "4s")
    void execute() {
        long userCount = _userService.getUserCount();
        log.info("User count is: {}", userCount);
    }
}
