package mk.ukim.finki.wp.project.repository;

import mk.ukim.finki.wp.project.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show,Long> {
    Show findAllByTitleIgnoreCase(String title);
}

