package zhi.model;

public class MovieRatingItem {
	private Long userId;
	private Long movieId;
	private Double rating;

	public MovieRatingItem() {

	}

	public MovieRatingItem(String line) {
		String[] items = line.split(",");
		userId = Long.parseLong(items[0]);
		movieId = Long.parseLong(items[1]);
		rating = Double.parseDouble(items[2]);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
}
