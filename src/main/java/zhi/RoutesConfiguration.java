package zhi;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {

	@Bean
	RoutesBuilder importMovieRouteBuilder() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				org.apache.camel.dataformat.csv.CsvDataFormat csvDataFormat = new org.apache.camel.dataformat.csv.CsvDataFormat();
				csvDataFormat.setSkipHeaderRecord(true);
				from("file:c:/temp/ml-latest-small?fileName=movies.csv&noop=true").autoStartup(false).log("Start").routeId("importMovie").onCompletion()
						.log("Finished").end().unmarshal(csvDataFormat).split(body()).streaming().parallelProcessing().to("bean:movieService?method=saveMovie")
						.end();

				from("file:c:/temp/ml-latest-small?fileName=ratings.csv&noop=true").autoStartup(false).log("Start").routeId("importRating").onCompletion()
						.log("Finished").end().unmarshal(csvDataFormat).split(body()).streaming().parallelProcessing()
						.to("bean:movieService?method=addMovieRating").end().to("bean:movieService?method=calculateSimilarity")
						.to("bean:movieService?method=saveSimilarity").end();

			}
		};
	}
}
