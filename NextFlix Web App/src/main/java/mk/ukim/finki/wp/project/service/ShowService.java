package mk.ukim.finki.wp.project.service;

import mk.ukim.finki.wp.project.model.Show;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShowService {

    /**
     * @return List of all products in the database
     */
    List<Show> listAll();

    Show findById(Long id) throws Exception;

//    Show create(String title,String cast,Integer release_year, String rating,String duration,String listed_in,String description, List<Long> recommendations);
    Show create(String title,String cast,Integer release_year, String rating,String duration,String listed_in,String description, String imageUrl);

    Show update(Long id,String title,String cast,Integer release_year, String rating,String duration,String listed_in,String description,String imageUrl) throws Exception;
//    Show update(Long id,String title,String cast,Integer release_year, String rating,String duration,String listed_in,String description,List<Long> recommendations) throws Exception;

    Show delete(Long id) throws Exception;

    Show findByTitle(String name);

//    List<Show> listShowsByListedIn(String genre);
}
