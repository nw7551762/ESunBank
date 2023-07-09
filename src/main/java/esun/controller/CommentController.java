package esun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esun.comment.Comment;
import esun.comment.CommentService;
import esun.post.PostService;
import esun.user.UserService;

@RestController
@RequestMapping("/api/demo")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@Transactional
    @PostMapping("/auth/comment/{username}/{postId}")
    public ResponseEntity<String> addComment(@PathVariable("postId") int postId,
            		@RequestParam("comment") String comment,
            		@RequestHeader("X-Username") String username) {
    	System.out.println(" addComment ");
    	commentService.save(username, postId, comment);
        return ResponseEntity.ok("留言提交成功");
    }

	@GetMapping("/allComment/{postId}")
	public ResponseEntity<List<Comment>> findPostComments(@PathVariable("postId") int postId) {
		List<Comment> allComment = commentService.findAllCommentByPostId(postId);
		return ResponseEntity.ok(allComment);
	}

}
