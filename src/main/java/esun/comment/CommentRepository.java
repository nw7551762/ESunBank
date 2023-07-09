package esun.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esun.post.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findAllByPostPostId( Integer postId);
}

