package com;

import java.util.*;

public class Movie {
	private int id;
	private String title;
	private int year;
	private String director;
	private List<String> genre;
	private List<String> stars;
	private int quantity = 1;
	
	public Movie() {
		title = null;
		director = null;
		genre = new ArrayList<>();
		stars = new ArrayList<>();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public void incQuantity() {
		this.quantity++;
	}
	
	public void addGenre(String genre) {
		this.genre.add(genre);
	}
	
	public void addStars(String star) {
		this.stars.add(star);
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getDirector() {
		return director;
	}
	
	public List<String> getGenre() {
		return genre;
	}
	
	public List<String> getStars() {
		return stars;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}