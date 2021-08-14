package siemieniuk.thesis.feedservice.model;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("feed_author")
@Data
public class FeedAuthor {

	@PrimaryKeyColumn(name = "author_id", ordinal = 0, type = PARTITIONED)
	long authorId;
	@PrimaryKeyColumn(name = "timestamp", ordinal = 1, type = CLUSTERED, ordering = DESCENDING)
	UUID timestamp;
	String content;
	String photo;
}
