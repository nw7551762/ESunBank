package esun.post;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import esun.comment.Comment;
import esun.user.User;

@Entity
@Component
@Table(name = "post")
public class Post {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postid")
    private int postId;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    
    @Column(name = "content")
    private String content;
    
    @Column(name = "img")
    private Blob image;
    
    @Column(name = "createddate")
    private LocalDateTime createdDate;
    
    public Post() {}
    


    public List<Comment> getComments() {
		return comments;
	}


	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	public int getPostId() {
        return postId;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Blob getImage() {
        return image;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    // 可以根據需要添加其他setter和getter方法
    

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", user=" + user.getUsername() +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
}
