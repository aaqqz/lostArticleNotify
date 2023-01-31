package project.toy.api.repository;

import project.toy.api.domain.Post;
import project.toy.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
