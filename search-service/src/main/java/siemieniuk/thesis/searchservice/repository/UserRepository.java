package siemieniuk.thesis.searchservice.repository;

import siemieniuk.thesis.searchservice.data.User;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User, String> {

  @Query("{\"multi_match\" : {\"query\": \"?0\", \"fields\": [ \"name\", \"surname\", \"login\" ] }}")
  List<User> findAllByQuery(String name);
}
