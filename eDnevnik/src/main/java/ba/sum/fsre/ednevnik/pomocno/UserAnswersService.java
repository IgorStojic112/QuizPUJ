package ba.sum.fsre.ednevnik.pomocno;

import ba.sum.fsre.ednevnik.models.User;

public interface UserAnswersService {
    public User getUserById(Long userId);
    public boolean isCorrectAnswer(Long answerId);
}
