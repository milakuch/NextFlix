package mk.ukim.finki.wp.project.web;

import mk.ukim.finki.wp.project.model.Role;
import mk.ukim.finki.wp.project.model.Show;
import mk.ukim.finki.wp.project.model.User;
import mk.ukim.finki.wp.project.model.Watchlist;
import mk.ukim.finki.wp.project.service.ShowService;
import mk.ukim.finki.wp.project.service.UserService;
import mk.ukim.finki.wp.project.service.WatchlistService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShowController {

    private final ShowService service;
    private final WatchlistService ws;
    private final UserService us;

    public ShowController(ShowService service, WatchlistService ws, UserService us) {
        this.service = service;
        this.ws = ws;
        this.us = us;
    }

    @GetMapping({"/"})
    public String home(Model model) {
        Role role = Role.ROLE_ADMIN;
        Role user = Role.ROLE_USER;
        us.create("mila","mila",role);
        us.create("user","user",user);
        return "home";
    }

    @GetMapping({"/shows"})
    public String showShows(Model model) {
        List<Show> shows = service.listAll();
        model.addAttribute("shows",shows);
        return "admin_shows_list";
    }

    @GetMapping({"/search"})
    public String search(Model model) {
        return "search";
    }

    @GetMapping({"/search/result/{id}"})
    public String searchShow(@PathVariable Long id, Model model) throws Exception {
        Show show = this.service.findById(id);
        List<Show> recc = show.getRecommendations();
        model.addAttribute("show",show);
        model.addAttribute("recommendations",recc);
        return "search results";
    }

    @GetMapping({"/explore"})
    public String explore(Model model) throws Exception {
        List<Show> shows = service.listAll();
        model.addAttribute("shows",shows);
        return "explore";
    }


    @PostMapping({"/search/result"})
    public String searchResult(Model model,@RequestParam String title) {
        Show sh = service.findByTitle(title);

        if (sh!=null){
            Long id = sh.getId();
            return "redirect:/search/result/"+id;
        }

        else return "redirect:/";
    }

    @GetMapping("/shows/add")
    public String showAdd(Model model) {
        Show show = new Show();
        model.addAttribute("action","add");
        model.addAttribute("show",show);
        return "form";
    }

    @GetMapping("/shows/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) throws Exception {
        Show show= this.service.findById(id);
        model.addAttribute("action","edit");
        model.addAttribute("show",show);
        return "form";
    }

    @GetMapping("/shows/{id}/details")
    public String showDetails(@PathVariable Long id, Model model) throws Exception {
        Show show= this.service.findById(id);
        model.addAttribute("show",show);
        return "details";
    }

    @GetMapping("/shows/{id}/addwatchlist")
    public String addWatchList(@PathVariable Long id, Model model) throws Exception {
        Show show= this.service.findById(id);
        model.addAttribute("show",show);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) principal;
        String username = u.getUsername();

        if (ws.findByUser(username)!=null){
            List<Watchlist> watchlists = this.ws.listAllWatchlists();
            model.addAttribute("watchlists",watchlists); }

        return "addshowtowatchlist";
    }

    @GetMapping("/watchlists")
    public String watchList(Model model) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) principal;
        String username = u.getUsername();

        if (ws.findByUser(username)!=null){
        List<Watchlist> watchlists = this.ws.listAllWatchlists();
        model.addAttribute("watchlists",watchlists); }

        return "list_of_watchlists";
    }

    @GetMapping("/watchlists/{id}")
    public String watchListDetails(@PathVariable Long id,Model model) throws Exception {
        Watchlist wl = ws.findById(id);
        List<Show> shows = wl.getShows();
        model.addAttribute("shows",shows);
        model.addAttribute("watchlist",wl);
        return "watchlist";
    }

    @PostMapping("/watchlists/create")
    public String newWatchList(Model model,@RequestParam String name) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) principal;
        String username = u.getUsername();

        User user = us.find(username);
        Watchlist watchlist = new Watchlist();

        watchlist.setUser(user);
        ws.create(name);

        return "redirect:/watchlists";
    }

    @PostMapping("/watchlists/{id}/delete")
    public String deleteWatchlist(@PathVariable Long id, Model model) throws Exception {
        ws.remove(id);
        return "redirect:/watchlists";
    }

    @PostMapping("/watchlists/{wlid}/removeshow")
    public String deleteFromWatchlist(@PathVariable(value="wlid") Long wlid, @RequestParam(value="showid") Long showid,Model model) throws Exception {
        ws.removeShowFromWatchlist(showid,wlid);
        return "redirect:/watchlists/{wlid}";
    }

    @PostMapping("/watchlists/{wlid}/addshow")
    public String addToWatchList(@PathVariable(value="wlid") Long wlid, @RequestParam(value="showid") Long showid,Model model) throws Exception {
        ws.addShowToWatchlist(showid,wlid);
        return "redirect:/watchlists/{wlid}";
    }

    @GetMapping("/stats")
    public String stats(Model model) throws Exception {
        int number_shows = service.listAll().size();
        int number_lists = ws.listAllWatchlists().size();
        int number_profiles = us.listAll().size();
        model.addAttribute("shows",number_shows);
        model.addAttribute("lists",number_lists);
        model.addAttribute("profiles",number_profiles);
        return "stats";
    }

//
//    @PostMapping("/watchlist/add")
//    public String addToWatchlist(@RequestParam Long id) throws Exception {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User u = (User)principal;
//        String username = u.getUsername();
//        Watchlist watchlist = ws.findByUser(username);
//        ws.addShowToWatchlist(id,watchlist.getId());
//        return "redirect:/shows";
//    }


    @PostMapping("/shows")
    public String create(@RequestParam String title,
                         @RequestParam String actors,
                         @RequestParam Integer releaseYear,
                         @RequestParam String rating,
                         @RequestParam String duration,
                         @RequestParam String listedIn,
                         @RequestParam String description,
                         @RequestParam String imageUrl) {
        this.service.create(title, actors, releaseYear, rating, duration, listedIn, description,imageUrl);
        return "redirect:/shows";
    }

    @RequestMapping("/shows/{id}")
    public String update(@PathVariable ("id")  Long id,
                         @RequestParam(value="title", required=false) String title,
                         @RequestParam(value="actors", required=false) String actors,
                         @RequestParam(value="releaseYear", required=false) Integer releaseYear,
                         @RequestParam(value="rating", required=false) String rating,
                         @RequestParam(value="duration", required=false) String duration,
                         @RequestParam(value="listedIn", required=false) String listedIn,
                         @RequestParam(value="description", required=false) String description,
                         @RequestParam(value="description", required=false) String imageUrl) throws Exception {
        this.service.update(id,title,actors,releaseYear,rating,duration,listedIn,description,imageUrl);
        return "redirect:/shows";
    }

    @PostMapping("/shows/{id}/delete")
    public String delete(@PathVariable Long id) throws Exception {
        this.service.delete(id);
        return "redirect:/shows";
    }


}
