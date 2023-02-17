package project.toy.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEMBER_ID")
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PostUserLostItem> postUserLostItems = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password, List<Post> posts, List<PostUserLostItem> postUserLostItems, List<Comment> comments) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.posts = posts;
        this.postUserLostItems = postUserLostItems;
        this.comments = comments;
    }
}
