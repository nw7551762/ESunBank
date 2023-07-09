package esun.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import esun.post.Post;
import esun.post.PostService;
import esun.user.User;
import esun.user.UserService;

@RestController
@RequestMapping("/api/demo")
public class PostController {
	
	@Autowired
    private PostService postService;
	
	@Autowired
    private UserService userService;

    @PostMapping("/auth/post")
    @Transactional
    public ResponseEntity<String> createPost(@RequestHeader("X-Username") String username, @RequestParam("content") String content,
    		@RequestParam(value = "image", required = false) MultipartFile image) {
    	
    	Post post = new Post();
    	User user = userService.findByUsername( username ).get() ;
    	post.setUser(user);
    	
    	
        post.setContent( content );
        if (image != null && !image.isEmpty()) {
            try {
                byte[] imageBytes = image.getBytes();
                post.setImage(new SerialBlob(imageBytes));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        post.setCreatedDate(LocalDateTime.now());
        postService.createPost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }
    
    @GetMapping("/allPost")
    public List<Post> findAllPost( )  {
    	List<Post> allPosts = postService.getAllPosts();
    	return allPosts;
    }
    
    @GetMapping("/image/{postId}")
    public void getPostImage(@PathVariable("postId") int postId, HttpServletResponse response) {
        try {
            // 根據 postId 找到 post
            Post post = postService.getPostById(postId);
            
            if (post != null && post.getImage() != null) {
                // 獲取圖片字節數據
                byte[] imageData = post.getImage().getBytes(1, (int) post.getImage().length());

                // 設置 response 的 content-type
                response.setContentType("image/jpeg");

                // 將圖片字節數據寫入 response 的 OutputStream
                response.getOutputStream().write(imageData);
                response.getOutputStream().flush();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    
    @PutMapping("/post1/{postId}")
    @Transactional
    public ResponseEntity<String> updatePost(
            @PathVariable("postId") int postId,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Post post = postService.getPostById(postId);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        post.setContent(content);

        if (image != null && !image.isEmpty()) {
            try {
                byte[] imageBytes = image.getBytes();
                post.setImage(new SerialBlob(imageBytes));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update post");
            }
        }

        postService.updatePost(post);

        return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully");
    }
    
    @GetMapping("/post1/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") int postId) {
        Post post = postService.getPostById(postId);
        if (post != null) {
            return ResponseEntity.ok().body(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/auth/post/{postId}")
    @Transactional
    public ResponseEntity<?> deletePost(@PathVariable int postId) {
        // Check if the post exists
        if (!postService.existsById(postId)) {
            return ResponseEntity.notFound().build();
        }
        
        User user = postService.getPostById(postId) .getUser();

        // Delete the post
        postService.deletePost(postId, user);
        return ResponseEntity.ok().build();
    }
    
    
    
    

}
