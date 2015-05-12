package zhi.model;

import java.util.Map;

public class Movie {
	private Long id;
	private String title;
	private String genres;
	private Map<Long, Double> similarMovies;
	private Map<Long, Double> ratings;

	public Movie() {

	}

	public Movie(String line) {
		String[] items = line.split(",");
		id = Long.parseLong(items[0]);
		title = items[1];
		genres = items[2];
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public Map<Long, Double> getRatings() {
		return ratings;
	}

	public void setRatings(Map<Long, Double> ratings) {
		this.ratings = ratings;
	}

	public Map<Long, Double> getSimilarMovies() {
		return similarMovies;
	}

	public void setSimilarMovies(Map<Long, Double> similarMovies) {
		this.similarMovies = similarMovies;
	}
}
