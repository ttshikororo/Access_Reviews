package za.co.univen.its.reviews.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.mail.javamail.JavaMailSender;
import za.co.univen.its.reviews.repos.*;
import za.co.univen.its.reviews.entities.*;

import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ITSAccessReviewService {


    private final ITSAccessReviewerRepo itsAccessReviewerRepo;

    private  final ITSAccessReviewMenuRepo itsAccessReviewMenuRepo;

    private final UserRepository userRepository;

    private final CommunicationRepo communicationRepo;
    private final ITSAccessReviewNotesRepo itsAccessReviewNotesRepo;
    private final WebClient webClient;

    private final JavaMailSender javaMailSender;

    private  final PasswordEncoder passwordEncoder;




    public ITSAccessReviewer retrieveReviewer(String staffNumber)
    {
        ITSAccessReviewer reviewers = null;
        Optional<ITSAccessReviewer> accessReviewer;
        try {
            String its_url = "https://univenproduction-itsintegration.private.azuremicroservices.io/api/staff/";
            reviewers = webClient
                    .get()
                    .uri(its_url + staffNumber )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ITSAccessReviewer>() {})
                    .block();
            accessReviewer = itsAccessReviewerRepo.findByPersonNumber(reviewers.getPersonNumber());
        } catch (Exception e)
        {
            accessReviewer = itsAccessReviewerRepo.findByPersonNumber(staffNumber);
            if( accessReviewer.isEmpty() )
            {
                throw new IllegalArgumentException("Could not retrieve data");
            }
            return accessReviewer.get();
        }
        if( accessReviewer.isEmpty() )
        {
            reviewers.setReviewDate(new Date());
            reviewers = itsAccessReviewerRepo.save(reviewers);
            reviewers.setStatus(ITSAccessReviewStatus.PENDING);
        }
        else
        {
            reviewers = accessReviewer.get();
            reviewers.setDepartmentName(reviewers.getDepartmentName());
            reviewers.setPostName(reviewers.getPostName());
            reviewers.setSupervisor(reviewers.getSupervisor());
            reviewers = itsAccessReviewerRepo.save(reviewers);
        }

        return  reviewers;
    }

    public ITSAccessReviewer updateITSAccessReviewer( ITSAccessReviewer reviewer) throws Exception {


        Optional<ITSAccessReviewer> oldReviewer = itsAccessReviewerRepo.findById(reviewer.getId());

        Year year = Year.now();

        if(oldReviewer.isPresent()){
            ITSAccessReviewer itsAccessReviewer = oldReviewer.get();
            itsAccessReviewer.setStatus(reviewer.getStatus());


            String emailBody;



          if( itsAccessReviewer.getStatus() ==  ITSAccessReviewStatus.SUBMITTED )
           {
               //to manager
               emailBody = getEmailBody(itsAccessReviewer, getNames(itsAccessReviewer.getSupervisor()), false);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom("no-reply@univen.ac.za");
                mailMessage.setTo(getEmail(itsAccessReviewer.getSupervisor()));

                mailMessage.setText(emailBody);
                mailMessage.setSubject("ITS access reviews " + year);
                javaMailSender.send(mailMessage);

            } else {
                //to employee
                emailBody = getEmailBody(itsAccessReviewer, getNames(itsAccessReviewer.getPersonNumber()) , true);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom("no-reply@univen.ac.za");
                mailMessage.setTo(getEmail(itsAccessReviewer.getPersonNumber()));
                mailMessage.setText(emailBody);
                mailMessage.setSubject("ITS access reviews "+ year);
                javaMailSender.send(mailMessage);
           }




            return itsAccessReviewerRepo.save(itsAccessReviewer);

        } else
        {
            throw new RuntimeException("Status not found");
        }

    }

    public List<ITSAccessReviewMenu> getMenus(String staffNumber,boolean used) {
      return itsAccessReviewMenuRepo.findByPersonNumberAndNotUsed(staffNumber,used);

    }

    public List<ITSAccessReviewMenu> getNotUsed(String staffNumber,boolean used) {
        return itsAccessReviewMenuRepo.findByPersonNumberAndNotUsed(staffNumber,used);

    }


    public ITSAccessReviewMenu updateMenus(ITSAccessReviewMenu menu)
    {
      return itsAccessReviewMenuRepo.save(menu);
    }

    public List<ITSAccessReviewer> getReviews(String supervisor) {
        return itsAccessReviewerRepo.findBySupervisorAndStatus(supervisor,ITSAccessReviewStatus.SUBMITTED);
    }

    public void getCommunicationDetails()
    {
        String its_url = "https://univenproduction-itsintegration.private.azuremicroservices.io/api/communication";

        List<CommunicationDetails> communicationDetails = webClient
                .get()
                .uri(its_url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CommunicationDetails>>() {})
                .block();
        for(CommunicationDetails details : communicationDetails) {
            Optional<CommunicationDetails> detailsOptional = communicationRepo.findByPersonNumber(details.getPersonNumber());
            if(detailsOptional.isEmpty())
            {
                communicationRepo.save(details);
            } else
            {
                details.setContactType(detailsOptional.get().getContactType());
                details.setCommunicationType(detailsOptional.get().getCommunicationType());
                details.setCommunicationNumber(detailsOptional.get().getCommunicationNumber());
            }

        }

    }
    public void retrieveAllUser()
    {
        String its_url = "https://univenproduction-itsintegration.private.azuremicroservices.io/api/users";


        List<User> users = webClient
                .get()
                .uri(its_url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {})
                .block();

        for (User user : users) {
            Optional<User> optionalUser =  userRepository.findByUsername(user.getUsername());

            if(optionalUser.isEmpty())
            {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            } else
            {
                user.setRoles(optionalUser.get().getRoles());
                user.setFirstname(optionalUser.get().getFirstname());
                user.setPassword(passwordEncoder.encode(optionalUser.get().getPassword()));
            }

        }

    }


    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }



    public static String getEmailBody(ITSAccessReviewer reviewer, String names, boolean toApplicant) throws Exception
    {
        String string_url ="https://accessreview.univen.ac.za/";
        String emailBody = "";
        if (toApplicant)
        {
            emailBody = "Dear " + reviewer.getSurname() + " " + reviewer.getInitials();
        } else
        {

            emailBody = "Dear " + names;

        }

        emailBody = emailBody + "\n";
        emailBody = emailBody + "\n";

        switch (reviewer.getStatus())
        {
            case SUBMITTED:


                    emailBody = emailBody + "Kindly be informed that ITS access reviews  for " + reviewer.getSurname() + " " + reviewer.getInitials() + " has been assigned to you for approval, URL to login : " +string_url;

                break;
            case APPROVED:


                    emailBody = emailBody + "Kindly be informed that your ITS access reviews has been approved by your HOD, URL to login : " + string_url;

                break;
            case PENDING:

                    emailBody = emailBody + "Kindly be informed that ITS access reviews has been returned for corrections, URL to login : " + string_url;

                break;

            default:
                return null;
        }
        emailBody = emailBody + "\n";
        emailBody = emailBody + "\n";
        emailBody = emailBody + "Yours sincerely.";
        emailBody = emailBody + "\n";
        emailBody = emailBody + "\n";
        emailBody = emailBody + "Business Systems";

        return emailBody;
    }
    public void saveMenuOptions()
    {
        try {
            String its_url = "https://univenproduction-itsintegration.private.azuremicroservices.io/api/menus";
            List<ITSAccessReviewMenu> reviewMenu = webClient
                    .get()
                    .uri(its_url )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ITSAccessReviewMenu> >() {})
                    .block();

            for (ITSAccessReviewMenu menu: reviewMenu  )
            {
                Optional<ITSAccessReviewMenu> optional = itsAccessReviewMenuRepo.findByPersonNumberAndMenuNameAndMenuOption(menu.getPersonNumber(), menu.getMenuName(), menu.getMenuOption());
                if (optional.isEmpty())
                {
                    itsAccessReviewMenuRepo.save(menu);

                }else
                {
                    menu.setNotUsed(optional.get().isNotUsed());

                }
            }
        } catch( Exception ignored)
        {

        }

    }



    public User getUserByStaffNo(String userName) throws Exception {

        Optional<User> user = userRepository.findByUsername(userName);
        if(user.isPresent()){
            return user.get();
        } else
        {
            throw  new Exception("User not found");
        }


    }
    public List<ITSAccessReviewNotes> getNotes(String staffNumber)
    {
        return itsAccessReviewNotesRepo.findByPersonNumber(staffNumber);
    }

    public ITSAccessReviewNotes createNotes(String comment, User user,ITSAccessReviewer itsAccessReviewer)
    {

        ITSAccessReviewNotes notes = new ITSAccessReviewNotes();
        notes.setCreatedDate(new Date());
        notes.setPersonNumber(user.getUsername());
        notes.setPersonNames(user.getSurname() + " " + itsAccessReviewer.getInitials());
        notes.setMessage(comment);
        notes.setAccessReview(itsAccessReviewer);

        return itsAccessReviewNotesRepo.save(notes);
    }

    public String getEmail(String personNumber)  {
        String toEmailAddress = "";
        Optional<CommunicationDetails> communicationDetails = communicationRepo.findByPersonNumber(personNumber);

        if (communicationDetails.isPresent())
        {
            toEmailAddress = communicationDetails.get().getCommunicationNumber();
            return toEmailAddress;
        } throw new RuntimeException( "Email not found");



    }



    public String getNames(String personNumber)
    {

        String names = "";
        try{
            String its_url = "https://univenproduction-itsintegration.private.azuremicroservices.io/api/staff/";
            ITSAccessReviewer reviewers = webClient
                    .get()
                    .uri(its_url + personNumber )
                    .retrieve()

                    .bodyToMono(new ParameterizedTypeReference<ITSAccessReviewer>() {})
                    .block();

            names = reviewers.getSurname() + " " + reviewers.getInitials();
            return names;

        } catch (Exception e)
        {
            throw new IllegalArgumentException("Could not retrieve data");
        }


    }


    public User findUserByUsernameAndPassword( String username, String password ) throws IllegalAccessException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if( optionalUser.isEmpty() )
        {
            throw new IllegalAccessException("Wrong username and password");
        }
        return optionalUser.get();
    }


    public List<ITSAccessReviewMenu> findNotUsedMenu(boolean used) {

        return itsAccessReviewMenuRepo.findByNotUsed(true);
    }

    public User getUser(String username)
    {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            user.get().setPassword(user.get().getPassword());
            return user.get();
        } else {
            throw new SecurityException("User not found." + username);
        }
    }


}
