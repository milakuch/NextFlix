package mk.ukim.finki.wp.project.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Watchlist {

    public Watchlist() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private List<Show> shows;

    @ManyToOne
    private User user;


//    public Watchlist(Long id, List<Show> shows) {
//        this.id = id;
//        this.shows = shows;
//    }

    public Watchlist(Long id, String name, User user) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.shows = new ArrayList<Show>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


//    public Watchlist(Long id, String name, List<Show> shows) {
//        this.id = id;
//        this.name = name;
//        this.shows = shows;
//    }
//
//    public Watchlist(Long id, String name) {
//        this.id = id;
//        this.name = name;
//        this.shows = new ArrayList<Show>();
//    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
