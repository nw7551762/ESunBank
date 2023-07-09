package esun.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import esun.post.Post;
import esun.user.User;

@Entity
@Component
@Table(name = "comment")
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentid")
    private int commentId;
	
	@ManyToOne
	@JoinColumn(name = "userid")
    private User user;
    
	@ManyToOne
	@JoinColumn(name = "postid")
    private Post post;
    
	@Column(name = "content")
    private String content;
    
	@Column(name = "createdat")
    private LocalDateTime createdAt;
	
	public Comment() {
	}

    public Comment(User user, Post post, String content, LocalDateTime createdAt) {
		this.user = user;
		this.post = post;
		this.content = content;
		this.createdAt = createdAt;
	}

	public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

