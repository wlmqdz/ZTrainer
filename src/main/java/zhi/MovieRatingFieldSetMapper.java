package zhi;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import zhi.model.MovieRatingItem;

class MovieRatingFieldSetMapper implements FieldSetMapper<MovieRatingItem> {
	public MovieRatingItem mapFieldSet(FieldSet fieldSet) {
		MovieRatingItem movieRatingItem = new MovieRatingItem();

		movieRatingItem.setUserId(fieldSet.readLong(0));
		movieRatingItem.setMovieId(fieldSet.readLong(1));
		movieRatingItem.setRating(fieldSet.readDouble(2));

		return movieRatingItem;
	}
}