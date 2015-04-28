package zhi;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import zhi.model.MovieRating;

@Component("movieRatingItemWriter")
public class MovieRatingItemWriter implements ItemWriter<MovieRating> {

	@Override
	public void write(List<? extends MovieRating> items) throws Exception {
		for (MovieRating rating : items) {
			System.out.println("user id " + rating.getUserId() + " movie id " + rating.getMovieId());
		}
	}

}