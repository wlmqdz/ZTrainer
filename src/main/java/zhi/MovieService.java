package zhi;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.sound.sampled.Line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import zhi.model.Movie;
import zhi.model.MovieRatingItem;

@Component("movieService")
public class MovieService {
	private Map<Long, Movie> movieMap;

	@Value("${similarityNum}")
	private Integer similarityNum;

	@Autowired
	private MovieSimilarityCalculator movieSimilarityCalculator;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	void init() {
		movieMap = new ConcurrentHashMap<Long, Movie>();
	}

	public void addMovieRating(String line) {
//		System.out.println("addMovieRating "+line);
//		System.out.println("movieMap "+movieMap.size());
		MovieRatingItem rating = new MovieRatingItem(line);
		
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
		System.out.println("calculateSimilarity");
		
		Long[] movieIds = movieMap.keySet().toArray(new Long[movieMap.size()]);
		for (int i = 0; i < movieIds.length - 1; i++) {
			Movie movieI = movieMap.get(movieIds[i]);
			for (int j = i + 1; j < movieIds.length; j++) {
				Movie movieJ = movieMap.get(movieIds[j]);
				Double similarScore = movieSimilarityCalculator.calculateSimilarity(movieI, movieJ);
				if (similarScore != 0) {
					if (movieI.getSimilarMovies() == null) {
						movieI.setSimilarMovies(new HashMap<Long, Double>());
					}
					if (movieJ.getSimilarMovies() == null) {
						movieJ.setSimilarMovies(new HashMap<Long, Double>());
					}
					movieI.getSimilarMovies().put(movieJ.getId(), similarScore);
					movieJ.getSimilarMovies().put(movieI.getId(), similarScore);
				}
			}
		}

		for (Movie movie : movieMap.values()) {
			movie.setSimilarMovies(filterSimilarMovies(movie.getSimilarMovies()));
		}
	}

	private Map<Long, Double> filterSimilarMovies(Map<Long, Double> unsortedMap) {
		if (unsortedMap == null) {
			return null;
		}
		// Convert Map to List
		List<Map.Entry<Long, Double>> list = new LinkedList<Map.Entry<Long, Double>>(unsortedMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Long, Double>>() {
			@Override
			public int compare(Map.Entry<Long, Double> o1, Map.Entry<Long, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<Long, Double> sortedMap = new LinkedHashMap<Long, Double>();
		for (int i = 0; i < this.similarityNum && i < list.size(); i++) {
			Map.Entry<Long, Double> entry = list.get(i);
			sortedMap.put(entry.getKey(), entry.getValue());
		}

//		System.out.println(sortedMap.toString());

		return sortedMap;
	}

	public void saveSimilarity() {
		System.out.println("saveSimilarity with map size "+movieMap.size());
		jdbcTemplate.execute("delete from zrecommender.movie_similarity;");

		for (Movie movie : movieMap.values()) {
			if (movie.getSimilarMovies() != null) {
				for (Map.Entry<Long, Double> entry : movie.getSimilarMovies().entrySet()) {
					jdbcTemplate.execute("insert into zrecommender.movie_similarity (movie_id1,movie_id2,similarity) values (" + movie.getId() + ","
							+ entry.getKey() + "," + entry.getValue() + ");");
				}
			}
		}
	}

	public void saveMovie(String line) {
		Movie movie = new Movie(line);
		Long num = jdbcTemplate.queryForObject("select count(id) from zrecommender.movie where id = " + movie.getId(), Long.class);
		if (num == 0) {
			jdbcTemplate.update("insert into zrecommender.movie (id, title, genres) values (?,?, ?)", movie.getId(), movie.getTitle(), movie.getGenres());
		} else {
			jdbcTemplate.update("update zrecommender.movie set title = ?, genres = ? where id = ?", movie.getTitle(), movie.getGenres(), movie.getId());
		}
	}

	public Integer getSimilarityNum() {
		return similarityNum;
	}

	public void setSimilarityNum(Integer similarityNum) {
		this.similarityNum = similarityNum;
	}
}
