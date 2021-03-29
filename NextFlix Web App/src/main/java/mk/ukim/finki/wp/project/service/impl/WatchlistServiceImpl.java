package mk.ukim.finki.wp.project.service.impl;

import mk.ukim.finki.wp.project.model.*;
import mk.ukim.finki.wp.project.repository.*;
import mk.ukim.finki.wp.project.service.WatchlistService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class WatchlistServiceImpl implements WatchlistService {
    private final WatchlistRepository wr;
    private final ShowRepository sr;
    private final UserRepository ur;

    public WatchlistServiceImpl(WatchlistRepository wr, ShowRepository sr, UserRepository ur) {
        this.wr  = wr ;
        this.sr = sr;
        this.ur = ur;
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        return wr.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<Watchlist> listAllWatchlists() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) principal;
        String username = u.getUsername();

        User user = ur.findByUsername(username).orElseThrow(Exception::new);

        return wr.findByUser(user);
    }

    @Override
    public List<Show> listAllShows(Long id) throws Exception {
        return wr.findById(id).orElseThrow(Exception::new).getShows();
    }

    @Override
    public Watchlist create(String name) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) principal;
        String username = u.getUsername();

        User user = ur.findByUsername(username).orElseThrow(Exception::new);

        long range = 1234567L;
        Random r = new Random();
        long number = (long)(r.nextDouble()*range);
        Watchlist wl = new Watchlist(number,name,user);
        return wr.save(wl);
    }

    @Override
    public Watchlist remove(Long id) throws Exception {
        Watchlist w = wr.findById(id).orElseThrow(Exception::new);
        wr.delete(w);
        return w;
    }

    @Override
    public List<Show> addShowToWatchlist(Long showId,Long watchlistId) throws Exception {
        Watchlist watchlist = wr.findById(watchlistId).orElseThrow(Exception::new);
        List<Show> shows = watchlist.getShows();
        Show show = sr.findById(showId).orElseThrow(Exception::new);
        shows.add(show);
        watchlist.setShows(shows);
        wr.save(watchlist);
        return shows;
    }
    @Override
    public List<Show> removeShowFromWatchlist(Long showId,Long watchlistId) throws Exception {
        Watchlist watchlist = wr.findById(watchlistId).orElseThrow(Exception::new);
        List<Show> shows = watchlist.getShows();
        Show show = sr.findById(showId).orElseThrow(Exception::new);
        shows.remove(show);
        watchlist.setShows(shows);
        wr.save(watchlist);
        return shows;
    }

    @Override
    public List<Watchlist> findByUser(String username) throws Exception {
        return wr.findByUser(ur.findByUsername(username).orElseThrow(Exception::new));
    }
    


}
