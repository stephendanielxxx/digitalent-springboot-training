package com.telkom.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telkom.training.model.MovieModel;
import com.telkom.training.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	public MovieModel createMovie(MovieModel movieModel) {
		return movieRepository.save(movieModel);
	}
	
	public MovieModel getMovie(int movieId) {
		return movieRepository.findById(movieId).get();
	}
}
