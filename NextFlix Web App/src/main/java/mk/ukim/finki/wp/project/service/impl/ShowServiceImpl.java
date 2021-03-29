package mk.ukim.finki.wp.project.service.impl;

import mk.ukim.finki.wp.project.model.*;
import mk.ukim.finki.wp.project.repository.*;
import mk.ukim.finki.wp.project.service.ShowService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class ShowServiceImpl implements ShowService {
    private final ShowRepository sr;
//    private final WatchlistRepository wr;
//    private final UserRepository ur;

    public ShowServiceImpl(ShowRepository sr) {
        this.sr = sr;
    }

    @Override
    public List<Show> listAll() {
        return sr.findAll();
    }

    @Override
    public Show findById(Long id) throws Exception {
        return sr.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public Show create(String title, String cast, Integer release_year, String rating, String duration, String listed_in, String description, String imageUrl) {
        long range = 1234567L;
        Random r = new Random();
        long number = (long)(r.nextDouble()*range);
        Show show = new Show(number,title,cast,release_year,rating,duration,listed_in,description,imageUrl);
        return sr.save(show);
    }

    @Override
    public Show update(Long id, String title, String cast, Integer release_year, String rating, String duration, String listed_in, String description,String imageUrl) throws Exception {
       Show show = sr.findById(id).orElseThrow(Exception::new);
       show.setTitle(title);
       show.setActors(cast);
       show.setDescription(description);
       show.setReleaseYear(release_year);
       show.setDuration(duration);
       show.setListedIn(listed_in);
       show.setRating(rating);
       show.setImageUrl(imageUrl);
       return sr.save(show);
    }

    @Override
    public Show delete(Long id) throws Exception {
        Show show = sr.findById(id).orElseThrow(Exception::new);
        sr.delete(show);
        return show;
    }

    @Override
    public Show findByTitle(String name) {

        return  sr.findAllByTitleIgnoreCase(name);
    }

//    @Override
//    public List<Show> listShowsByListedIn(String genre) {
//        return null;
//    }


}
