package siemieniuk.thesis.messageservice.model;

import static org.springframework.data.cassandra.core.cql.Ordering.ASCENDING;
import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("message")
@Data
public class Message {

	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PARTITIONED)
	private long userId;
	@PrimaryKeyColumn(name = "second_user_id", ordinal = 1, type = PARTITIONED)
	private long secondUserId;
	@PrimaryKeyColumn(name = "timestamp", ordinal = 2, type = CLUSTERED, ordering = DESCENDING)
	private UUID timestamp;
	@Column("author_id")
	private long authorId;
	private String content;
}
