package za.co.univen.its.reviews.schedulars;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import za.co.univen.its.reviews.services.ITSAccessReviewService;


@Component
@RequiredArgsConstructor
public class DataFetchingScheduler {
    private final ITSAccessReviewService itsAccessReviewService;

    @Scheduled(cron = "0 0 20 * * *")
    public void fetchDataAndSave() {
        itsAccessReviewService.retrieveAllUser();
        itsAccessReviewService.getCommunicationDetails();
    }

    @Scheduled(cron = "0 0 00 * * *")
    public void fetchMenuAndSave(){
        itsAccessReviewService.saveMenuOptions();
    }


}
