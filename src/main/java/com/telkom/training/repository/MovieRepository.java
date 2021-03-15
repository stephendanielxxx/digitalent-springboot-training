package com.telkom.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.telkom.training.model.MovieModel;

public interface MovieRepository extends CrudRepository<MovieModel, Integer>{

}
