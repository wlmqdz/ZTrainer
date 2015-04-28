package zhi;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import zhi.model.Movie;

class MovieFieldSetMapper implements FieldSetMapper<Movie> {
	public Movie mapFieldSet(FieldSet fieldSet) {
		Movie Movie = new Movie();

		Movie.setId(fieldSet.readLong(0));
		Movie.setTitle(fieldSet.readString(1));
		Movie.setGenres(fieldSet.readString(2));

		return Movie;
	}
}