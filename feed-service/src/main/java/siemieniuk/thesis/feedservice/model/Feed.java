package siemieniuk.thesis.feedservice.model;

import java.util.UUID;

public interface Feed {
	long getAuthorId();
	UUID getTimestamp();
	String getContent();
	String getPhoto();
}
