package com.sat.tmf.movietkt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sat.tmf.movietkt.entities.Movie;
import com.sat.tmf.movietkt.service.MovieService;

@Controller
@RequestMapping("/admin/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // List all movies (Admin)
    @GetMapping
    public String listMovies(Model model, @RequestParam(required = false) String search) {
     List<Movie> movies= (search != null && !search.isEmpty())
               ? movieService.searchMovies(search)
               : movieService.findAllMovies();

        if (search != null && !search.isEmpty()) {
            movies = movieService.searchMovies(search);
        } else {
            movies = movieService.findAllMovies();
        }

       
        model.addAttribute("movies", movies);
        model.addAttribute("search", search);
        model.addAttribute("contentPage", "admin/adminMovies.jsp");
        model.addAttribute("pageTitle", "Manage Movies");
        return "layout/layout";
    }

    // Add Movie form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("contentPage", "admin/addMovie.jsp");
        model.addAttribute("pageTitle", "Add Movie");
        return "layout/layout";
    }

    // Save movie
    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/admin/movies";
    }

    // Edit movie
    @GetMapping("/edit/{id}")
    public String editMovie(@PathVariable Integer id, Model model) {
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("contentPage", "admin/addMovie.jsp");
        model.addAttribute("pageTitle", "Edit Movie");
        return "layout/layout";
    }

    // Delete movie
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return "redirect:/admin/movies";
    }
}
