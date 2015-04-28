package zhi;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import zhi.model.MovieRating;

class MovieRatingFieldSetMapper implements FieldSetMapper<MovieRating> {
	public MovieRating mapFieldSet(FieldSet fieldSet) {
		MovieRating movieRating = new MovieRating();

		movieRating.setUserId(fieldSet.readLong(0));
		movieRating.setMovieId(fieldSet.readLong(1));
		movieRating.setRating(fieldSet.readDouble(2));

		return movieRating;
	}
}