package siemieniuk.thesis.feedservice.model;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("reaction")
@Data
public class Reaction {

	@PrimaryKeyColumn(name = "post_id", ordinal = 0, type = PARTITIONED)
	private UUID postId;
	@PrimaryKeyColumn(name = "author_id", ordinal = 1, type = PARTITIONED)
	private long authorId;
	@PrimaryKeyColumn(name = "timestamp", ordinal = 2, type = CLUSTERED, ordering = DESCENDING)
	private UUID timestamp;
}
