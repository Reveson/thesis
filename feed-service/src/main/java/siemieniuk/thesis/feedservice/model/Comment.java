package siemieniuk.thesis.feedservice.model;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("comment")
@Data
public class Comment {

	@PrimaryKeyColumn(name = "post_id", ordinal = 0, type = PARTITIONED)
	UUID postId;
	@PrimaryKeyColumn(name = "timestamp", ordinal = 1, type = CLUSTERED, ordering = DESCENDING)
	UUID timestamp;
	@Column("author_id")
	long authorId;
	String authorName;
	String content;
}
