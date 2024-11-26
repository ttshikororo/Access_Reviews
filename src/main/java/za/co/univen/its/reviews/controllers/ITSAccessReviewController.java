package za.co.univen.its.reviews.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import za.co.univen.its.reviews.dto.ReviewDTO;
import za.co.univen.its.reviews.entities.*;

import za.co.univen.its.reviews.services.ITSAccessReviewService;
import za.co.univen.its.reviews.services.UsernameNotFoundException;

import java.util.List;


@RestController
@RequestMapping(value ="/api/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ITSAccessReviewController {

    private final ITSAccessReviewService itsAccessReviewService;
    private  final PasswordEncoder passwordEncoder;



    @PostMapping(value="/reviewer/update_status")
    public ITSAccessReviewer saveReviewer(@RequestBody ReviewDTO dto) throws Exception {

        if (dto.getComments() != null){


        itsAccessReviewService.createNotes(dto.getComments(),itsAccessReviewService.getUserByStaffNo(dto.getUsername()),dto.getReviewer());
        }

        return itsAccessReviewService.updateITSAccessReviewer(dto.getReviewer());
    }

    @PutMapping(value="/reviewer/menus/update")
    public ITSAccessReviewMenu getUpdateMenu(@RequestBody
    ITSAccessReviewMenu itsAccessReviewMenu)
    {
        return itsAccessReviewService.updateMenus(itsAccessReviewMenu);
    }


    @GetMapping("/reviewer/{personNumber}")
    public ResponseEntity<ReviewDTO> getReviewer(@PathVariable String personNumber) throws Exception {
        ReviewDTO dto = new ReviewDTO();
        String sorted = "menuName";
        dto.setMenus(itsAccessReviewService.getMenus(personNumber,false));

        dto.setReviewers(itsAccessReviewService.getReviews(personNumber));

        dto.setReviewer(itsAccessReviewService.retrieveReviewer(personNumber));
        dto.setNotes(itsAccessReviewService.getNotes(personNumber));

        dto.setNotes(itsAccessReviewService.getNotes(personNumber));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<ReviewDTO> login(@PathVariable String username,@PathVariable String password ) throws Exception {
        ReviewDTO dto = new ReviewDTO();

        dto.setUser(itsAccessReviewService.findUserByUsernameAndPassword( username, password ));
        dto.getUser().setPassword(passwordEncoder.encode(dto.getUser().getPassword()));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



    @GetMapping("/menu/{personNumber}")
    public ResponseEntity<ReviewDTO> getITSMenus(@PathVariable String personNumber) throws Exception {
        ReviewDTO dto = new ReviewDTO();
        dto.setMenus(itsAccessReviewService.getMenus(personNumber,false));

        dto.setNotes(itsAccessReviewService.getNotes(personNumber));

        dto.setReviewers(itsAccessReviewService.getReviews(personNumber));

        dto.setReviewer(itsAccessReviewService.retrieveReviewer(personNumber));

        dto.setNotUsed(itsAccessReviewService.getNotUsed(personNumber,true));

        dto.setNotes(itsAccessReviewService.getNotes(personNumber));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/allusers")
    public void saveEmail()
    {
        itsAccessReviewService.retrieveAllUser();
    }

    @GetMapping("/users/all")
    public  ResponseEntity<ReviewDTO> getUsers()
    {
        ReviewDTO dto = new ReviewDTO();
        dto.setUsers(itsAccessReviewService.getAllUsers());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/email/{personNumber}")
    public  String getCommunicationDetails(@PathVariable String personNumber) throws Exception {

        return itsAccessReviewService.getEmail(personNumber);

    }
    @GetMapping(value ="/notes/{personNumber}")
    public List<ITSAccessReviewNotes> getNotes(@PathVariable String personNumber){
        return itsAccessReviewService.getNotes(personNumber);
    }

    @GetMapping("/notusedmenu")
    public  List<ITSAccessReviewMenu> getNotUsedMenu(){
        return itsAccessReviewService.findNotUsedMenu(true);

    }

   @GetMapping("/names/{personNumber}")
   public String getNames(@PathVariable String personNumber)
   {
       return itsAccessReviewService.getNames(personNumber);
   }




}
