package siemieniuk.thesis.feedservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import siemieniuk.thesis.feedservice.model.UserCache;

@Repository
public interface UserCacheRepository extends CrudRepository<UserCache, String> {

}