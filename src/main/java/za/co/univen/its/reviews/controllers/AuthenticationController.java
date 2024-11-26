package za.co.univen.its.reviews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.univen.its.reviews.dto.ReviewDTO;
import za.co.univen.its.reviews.dto.UserDTO;
import za.co.univen.its.reviews.services.ITSAccessReviewService;

@RestController
@RequestMapping("/auth/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final ITSAccessReviewService itsAccessReviewService;
    @GetMapping( value ="/login/{username}")
    public  ReviewDTO login(@PathVariable String username)  {
        ReviewDTO dto = new ReviewDTO();
        dto.setUser(itsAccessReviewService.getUser(username));
        return dto;
    }

}
