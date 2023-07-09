package esun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import esun.post.Post;
import esun.post.PostService;
import esun.user.User;

@Controller
@RequestMapping("/view")
public class ViewController {
	@Autowired
	PostService postService;
	
	@GetMapping("/")
    public String index() {
        return "index"; 
    }
	
	
	@GetMapping("/registerPage")
    public String registerPage() {
        return "registerPage";
    }
	
	@GetMapping("/postList")
    public String postList() {
        return "postList"; // be same as the template file name (without suffix)
    }
	
	@GetMapping("/allPost")
    public String allPost() {
        return "allPost"; // be same as the template file name (without suffix)
    }
	
	@GetMapping("/loginPage")
    public String login() {
        return "loginPage"; // be same as the template file name (without suffix)
    }
	
	@GetMapping("/createPost")
    public String createPost() {
        return "createPost"; // be same as the template file name (without suffix)
    }
	
	@GetMapping("/redirectToEdit/{postId}")
	public String editPost(@PathVariable("postId") int postId, Model model) {
	    System.out.println("redirectToEdit");
	    Post post = postService.getPostById(postId);
	    model.addAttribute("post", post);
	    return "editPost";
	}
	
	@GetMapping("/redirectToPost/{postId}")
	public String redirectToPost(@PathVariable("postId") int postId, Model model) {
	    System.out.println("redirectToPost");
	    Post post = postService.getPostById(postId);
	    String username = (String) model.getAttribute("username");
	    System.out.println( username);
	    
	    model.addAttribute("post", post);
	    return "postPage";
	}


	
}
