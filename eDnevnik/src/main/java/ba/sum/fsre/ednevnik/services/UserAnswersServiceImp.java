package ba.sum.fsre.ednevnik.services;

import ba.sum.fsre.ednevnik.models.Odgovori;
import ba.sum.fsre.ednevnik.models.User;
import ba.sum.fsre.ednevnik.pomocno.UserAnswersService;
import ba.sum.fsre.ednevnik.repositories.OdgovoriRepository;
import ba.sum.fsre.ednevnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserAnswersServiceImp implements UserAnswersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OdgovoriRepository odgovoriRepository;

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public boolean isCorrectAnswer(Long odgovorId) {
        Optional<Odgovori> odgovoriOptional = odgovoriRepository.findById(odgovorId);
        if (odgovoriOptional.isPresent()) {
            return odgovoriOptional.get().isCorrect();
        } else {
            throw new RuntimeException("Answer not found with id: " + odgovorId);
        }
    }
}
