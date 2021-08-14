package siemieniuk.thesis.feedservice.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubscriptionService {

	public List<Long> getActiveSubscribersList(long userId) {
		//TODO integrate with Redis
		return Collections.emptyList();
	}
}
