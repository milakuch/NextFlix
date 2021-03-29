package mk.ukim.finki.wp.project.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Show {

    public Show() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Show(Long id, String title, String actors, Integer releaseYear, String rating, String duration, String listedIn, String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.actors = actors;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = listedIn;
        this.description = description;
        this.imageUrl = imageUrl;
        recommendations=new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String actors;

    private Integer releaseYear;

    private String rating;

    private String duration;

    private String listedIn;

    private String description;

    private String imageUrl;


    @ManyToMany
    private List<Show> recommendations;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String cast) {
        this.actors = actors;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer release_year) {
        this.releaseYear = release_year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getListedIn() {
        return listedIn;
    }

    public void setListedIn(String listed_in) {
        this.listedIn = listed_in;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Show> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Show> recommendations) {
        this.recommendations = recommendations;
    }
}
