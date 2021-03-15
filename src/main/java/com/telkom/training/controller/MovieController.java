package com.telkom.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telkom.training.model.MovieModel;
import com.telkom.training.service.MovieService;

@RestController
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@PostMapping("/movie")
	public MovieModel createMovie(@RequestParam("title") String title, 
			@RequestParam("genre") String genre,
			@RequestParam("releaseYear") String releaseYear) {
		MovieModel movieModel = new MovieModel();
		movieModel.setMovieTitle(title);
		movieModel.setMovieGenre(genre);
		movieModel.setMovieReleaseYear(releaseYear);
		
		return movieService.createMovie(movieModel);
	}
	
	@GetMapping("/movie/{id}")
	public MovieModel getMovie(@PathVariable int id) {
		return movieService.getMovie(id);
	}
}
