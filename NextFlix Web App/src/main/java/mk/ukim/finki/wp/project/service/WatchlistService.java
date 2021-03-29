package mk.ukim.finki.wp.project.service;

import mk.ukim.finki.wp.project.model.Show;
import mk.ukim.finki.wp.project.model.User;
import mk.ukim.finki.wp.project.model.Watchlist;

import java.util.List;
import java.util.Map;

public interface WatchlistService {

    Watchlist findById(Long id) throws Exception;

    List<Watchlist> listAllWatchlists() throws Exception;

    List<Show> listAllShows(Long id) throws Exception;

    Watchlist create(String name) throws Exception;

    Watchlist remove(Long id) throws Exception;

    List<Show> addShowToWatchlist(Long showId,Long watchlistId) throws Exception;

    List<Show> removeShowFromWatchlist(Long showId,Long watchlistId) throws Exception;

    List<Watchlist> findByUser(String username) throws Exception;

}
