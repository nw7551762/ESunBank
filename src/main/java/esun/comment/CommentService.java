package esun.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esun.post.Post;
import esun.post.PostRepository;
import esun.user.User;
import esun.user.UserRepository;

@Service
@Transactional
public class CommentService {
	@Autowired
    private CommentRepository commentRepository;
	@Autowired
    private PostRepository postRepository;
	@Autowired
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save( String username, int postId, String commentStr) {
    	Optional<Post> post = postRepository.findById(postId);
    	User user = userRepository.findByUsername(username);
    	if(  post.isEmpty() ||  user==null ) {
    		return null;
    	}
    	Comment comment = new Comment( user, post.get(), commentStr, LocalDateTime.now() );
        return commentRepository.save(comment);
    }
    
    public Comment save( Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
    
    public List<Comment> findAllCommentByPostId(int postId) {
        return commentRepository.findAllByPostPostId(postId);
    }
    
}

