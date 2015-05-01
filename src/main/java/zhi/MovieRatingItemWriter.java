package zhi;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zhi.model.MovieRatingItem;

@Component("movieRatingItemWriter")
public class MovieRatingItemWriter implements ItemWriter<MovieRatingItem> {

	@Autowired
	MovieSimilarityService movieSimilarityService;

	@Override
	public void write(List<? extends MovieRatingItem> items) throws Exception {
		for (MovieRatingItem rating : items) {
			movieSimilarityService.addMovieRating(rating);
		}
	}
}