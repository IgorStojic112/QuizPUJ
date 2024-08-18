package ba.sum.fsre.ednevnik.controllers;

import ba.sum.fsre.ednevnik.models.*;
import ba.sum.fsre.ednevnik.repositories.UserRepository;
import ba.sum.fsre.ednevnik.services.*;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    OdgovoriServiceImp odgovoriServiceImp;
    @Autowired
    PitanjaService pitanjaService;
    @Autowired
    QuizServiceImp quizServiceImp;
    @Autowired
    UserAnswersServiceImp userAnswersServiceImp;
    @Autowired
    UserRepository userRepository;



    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addQuiz(Model model){

        Quiz quiz = new Quiz();

        // Initialize with 5 Pitanja, each having 3 Odgovori
        for (int i = 0; i < 5; i++) {
            Pitanja pitanja = new Pitanja();
            for (int j = 0; j < 3; j++) {
                pitanja.getOdgovori().add(new Odgovori());
            }
            quiz.getPitanja().add(pitanja);
        }

        model.addAttribute("quiz", quiz);
        return "quiz/addQuiz"; // the view where the form is rendered
    }
    //treba napraviti da quiz/list izlista sve quizove
    @GetMapping("/list")
    public String QuizList(Model model){
        List<Quiz> quizovi = quizServiceImp.findAll();
        model.addAttribute("quizovi", quizovi);
        return "/quiz/list";
    }

    @GetMapping("/play/{quizId}")
    public String playQuiz(@PathVariable Long quizId/*,Long userId*/, Model model,Authentication authentication) {
        Quiz quiz = quizServiceImp.findById(quizId); // Assuming you have this method in your service
        //Optional<User> user = userRepository.findById(userId);
        model.addAttribute("quiz", quiz);
        model.addAttribute("pitanja", quiz.getPitanja()); // Assuming Quiz has a method getPitanja()
        //model.addAttribute("user", userId); // Assuming Quiz has a method getPitanja()
        return "quiz/playQuiz"; // Name of the Thymeleaf template to render
    }


    @GetMapping("/test")
    public String QuizTest(Model model){
        List<Quiz> quizovi1 = quizServiceImp.findAll();
        model.addAttribute("quizovi", quizovi1);
        return "/quiz/list";
    }

    @PostMapping("/submitAnswers")
    public String UserPostQuiz(@RequestParam("selectedAnswers") List<Long> selectedAnswerIds,
                               @RequestParam("quizId") Long quizId,
                               Model model,
                               Authentication authentication){
        // Retrieve the current user's details from the authentication object
        /*UserDetails userDetails = (UserDetails) authentication.getClass();
        Long userId = userDetails.getUser().getId();

        Quiz quiz = quizServiceImp.getQuizById(quizId);
        User user = userAnswersServiceImp.getUserById(userId);
        int correctAnswersCount = 0;
        for (Long answerId : selectedAnswerIds) {
            // Your logic to check if the answer is correct
            // Assuming you have an 'isCorrectAnswer' method in your service
            if (userAnswersServiceImp.isCorrectAnswer(answerId)) {
                correctAnswersCount++;
            }
        }

        model.addAttribute("correctAnswersCount", correctAnswersCount);
        model.addAttribute("totalQuestions", quiz.getPitanja().size());
*/
        return "redirect:quiz/list";

    }

    @PostMapping("/delete/{quizId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteQuiz(@PathVariable Long quizId){
        quizServiceImp.deleteById(quizId);
        return "redirect:/quiz/list";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addQuiz(@ModelAttribute Quiz quiz) {
        LocalDateTime  Datum1 = LocalDateTime.now();
        quiz.setDatum(Datum1);
        // Here you would save the quiz to the database
        quizServiceImp.saveQuiz(quiz);

        return "redirect:quiz/list"; // Redirect to some other page after saving
    }




}
