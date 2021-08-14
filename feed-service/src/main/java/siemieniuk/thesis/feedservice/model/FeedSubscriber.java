package siemieniuk.thesis.feedservice.model;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("feed_subscriber")
@Data
public class FeedSubscriber {

	@PrimaryKeyColumn(name = "subscriber_id", ordinal = 0, type = PARTITIONED)
	long subscriberId;
	@PrimaryKeyColumn(name = "timestamp", ordinal = 1, type = CLUSTERED, ordering = DESCENDING)
	UUID timestamp;
	@Column("author_id")
	long authorId;
	@Column("author_name")
	String authorName;
	String content;
	String photo;
}
