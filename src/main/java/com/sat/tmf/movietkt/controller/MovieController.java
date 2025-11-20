package com.sat.tmf.movietkt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sat.tmf.movietkt.entities.Movie;
import com.sat.tmf.movietkt.service.MovieService;

@Controller
@RequestMapping("/admin/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Admin — List Movies
    @GetMapping
    public String listMovies(@RequestParam(required = false) String search, Model model) {

        List<Movie> movies = (search != null && !search.isEmpty())
                ? movieService.searchMovies(search)
                : movieService.findAllMovies();

        model.addAttribute("movies", movies);
        model.addAttribute("search", search);
        model.addAttribute("contentPage", "/WEB-INF/views/admin/adminMovies.jsp");
        model.addAttribute("pageTitle", "Manage Movies");

        return "layout/layout";
    }

    // Admin — Add Form
    @GetMapping("/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("contentPage", "/WEB-INF/views/admin/addMovie.jsp");
        model.addAttribute("pageTitle", "Add Movie");

        return "layout/layout";
    }

    // Admin — Save Movie
    @PostMapping("/add")
    public String saveMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/admin/movies";
    }

    // Admin — Edit Movie
    @GetMapping("/edit/{id}")
    public String editMovie(@PathVariable Integer id, Model model) {

        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("contentPage", "/WEB-INF/views/admin/addMovie.jsp");
        model.addAttribute("pageTitle", "Edit Movie");

        return "layout/layout";
    }

    // Admin — Delete Movie
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return "redirect:/admin/movies";
    }
}
