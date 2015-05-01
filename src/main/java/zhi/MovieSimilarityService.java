package zhi;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import zhi.model.Movie;
import zhi.model.MovieRatingItem;

@Component("movieSimilarityService")
public class MovieSimilarityService {
	private Map<Long, Movie> movieMap;
	
	@PostConstruct
	void init() {
		movieMap = new HashMap<Long, Movie>();
	}

	public void addMovieRating(MovieRatingItem rating) {
		if (!movieMap.containsKey(rating.getMovieId())) {
			Movie movie = new Movie();
			movie.setId(rating.getMovieId());
			Map<Long, Double> ratingOfMovie = new HashMap<Long, Double>();
			ratingOfMovie.put(rating.getUserId(), rating.getRating());
			movie.setRatings(ratingOfMovie);
			movieMap.put(movie.getId(), movie);
		}
		Movie movie = movieMap.get(rating.getMovieId());
		movie.getRatings().put(rating.getUserId(), rating.getRating());
	}
	
	public void calculateSimilarity() {
		
	}
}
