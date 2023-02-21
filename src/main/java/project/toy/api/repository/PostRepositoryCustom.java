package project.toy.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.toy.api.request.PostSearch;
import project.toy.api.response.PostResponse;

public interface PostRepositoryCustom {

    Page<PostResponse> search(PostSearch postSearch, Pageable pageable);
}
