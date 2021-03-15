package com.telkom.training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_movie")
public class MovieModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private int movieId;
	@Column(name = "movie_title")
	private String movieTitle;
	@Column(name = "movieGenre")
	private String movieGenre;
	@Column(name = "movie_release_year")
	private String movieReleaseYear;
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public String getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	public String getMovieReleaseYear() {
		return movieReleaseYear;
	}
	public void setMovieReleaseYear(String movieReleaseYear) {
		this.movieReleaseYear = movieReleaseYear;
	}
	
	
}
