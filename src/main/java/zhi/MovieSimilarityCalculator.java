package zhi;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import zhi.model.Movie;

@Component("movieSimilarityCalculator")
public class MovieSimilarityCalculator {

	public Double calculateSimilarity(Movie movie1, Movie movie2) {
		// calculate Pearson correlation score
		List<Long> commonUserIds = new ArrayList<Long>();
		for (Long userId : movie1.getRatings().keySet()) {
			if (movie2.getRatings().containsKey(userId)) {
				commonUserIds.add(userId);
			}
		}

		if (commonUserIds.size() == 0) {
			return 0d;
		}

		Double sum1 = 0d;
		Double sum2 = 0d;
		Double sum1Sq = 0d;
		Double sum2Sq = 0d;
		Double pSum = 0d;

		for (Long userId : commonUserIds) {
			Double pref1 = movie1.getRatings().get(userId);
			Double pref2 = movie2.getRatings().get(userId);
			sum1 += pref1;
			sum2 += pref2;
			sum1Sq += Math.pow(pref1, 2);
			sum2Sq += Math.pow(pref2, 2);
			pSum += (pref1 * pref2);
		}

		Double num = pSum - (sum1 * sum2 / commonUserIds.size());
		Integer n = commonUserIds.size();
		Double den = Math.sqrt((sum1Sq - Math.pow(sum1, 2) / n) * (sum2Sq - Math.pow(sum2, 2) / n));
		if (den == 0d)
			return 0d;

		Double r = num / den;

		return r;
	}
}
