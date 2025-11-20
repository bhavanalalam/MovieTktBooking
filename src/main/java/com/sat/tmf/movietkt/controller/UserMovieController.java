package com.sat.tmf.movietkt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sat.tmf.movietkt.entities.Movie;
import com.sat.tmf.movietkt.entities.Show;
import com.sat.tmf.movietkt.service.MovieService;
import com.sat.tmf.movietkt.service.ShowService;

@Controller
public class UserMovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowService showService;

    // User browsing movies
    @GetMapping("/user/movies")
    public String listMoviesForUser(@RequestParam(required = false) String search,
                                    @RequestParam(required = false) String language,
                                    Model model) {

        List<Movie> movies = (search != null && !search.isEmpty())
                ? movieService.searchMovies(search)
                : movieService.findAllMovies();

        model.addAttribute("movies", movies);
        model.addAttribute("search", search);
        model.addAttribute("contentPage", "/WEB-INF/views/movies.jsp");
        model.addAttribute("pageTitle", "Now Showing");
        return "layout/layout";
    }

    // User movie showtimes
    @GetMapping("/user/movies/{id}/shows")
    public String listShowsForMovie(@PathVariable Integer id, Model model) {

        Movie movie = movieService.findById(id);
        List<Show> shows = showService.findUpcomingShows(movie);

        model.addAttribute("movie", movie);
        model.addAttribute("shows", shows);
        model.addAttribute("contentPage", "/WEB-INF/views/movieShows.jsp");
        model.addAttribute("pageTitle", movie.getTitle() + " - Showtimes");
        return "layout/layout";
    }
}
