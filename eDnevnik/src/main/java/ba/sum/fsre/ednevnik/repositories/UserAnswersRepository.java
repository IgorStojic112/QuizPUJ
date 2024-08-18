package ba.sum.fsre.ednevnik.repositories;

import ba.sum.fsre.ednevnik.models.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswersRepository extends JpaRepository<UserAnswers,Long> {
    //basic CRUD
}
