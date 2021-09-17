package siemieniuk.thesis.userservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("feed-service")
public interface FeedFeignClient {
	@RequestMapping(
			method= RequestMethod.POST,
			value="/feed/subscriber/{subscriberId}/denormalizeFeeds",
			consumes="application/json")
	ResponseEntity<?> denormalizeFeeds(@PathVariable("subscriberId") long subscriberId);
}
