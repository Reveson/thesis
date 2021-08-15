package siemieniuk.thesis.notificationservice.model;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("notification")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PARTITIONED)
	private long userId;
	@PrimaryKeyColumn(name = "timestamp", ordinal = 2, type = CLUSTERED, ordering = DESCENDING)
	private UUID timestamp;
	private String content;
	@Column("is_read")
	private boolean isRead;
}
