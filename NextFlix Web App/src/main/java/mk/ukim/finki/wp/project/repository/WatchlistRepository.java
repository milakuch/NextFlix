package mk.ukim.finki.wp.project.repository;

import mk.ukim.finki.wp.project.model.Show;
import mk.ukim.finki.wp.project.model.User;
import mk.ukim.finki.wp.project.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    public List<Watchlist> findByUser(User user);
}
