package za.co.univen.its.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.univen.its.reviews.entities.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private ITSAccessReviewer reviewer;
    List<ITSAccessReviewer> reviewers;
    List<ITSAccessReviewMenu> menus;
    ITSAccessReviewMenu menu;
    List<ITSAccessReviewMenu> notUsed;
    private ITSAccessReviewStatus status;

    private List<ITSAccessReviewNotes> notes;

    private String comments;

    private List<User> users;

    private String  username;

    private List<CommunicationDetails> communicationDetails;

    private CommunicationDetails communicationDetail;
    private User user;

}