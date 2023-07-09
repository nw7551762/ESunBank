package esun.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esun.user.User;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class PostService {
	@Autowired
    private PostRepository postRepository;
	
	public PostService() {}

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + postId));
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }
    
    public void updatePost(Post post) {
        postRepository.save(post);
    }

	public boolean existsById(Integer postId) {
		return postRepository.existsById(postId);
	}
	
	public void deletePost(int postId, User user) {
	    // Get the post
	    Post post = postRepository.findById(postId).orElseThrow();
	    
	    if (!post.getUser().equals(user)) {
	        return;
	    }
	    
	    user.getPosts().remove(post);
	    postRepository.delete(post);
	}

	
	
}

