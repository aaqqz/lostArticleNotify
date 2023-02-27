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
public class Member extends BaseTimeEntity{

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
    private List<MemberLostItem> memberLostItems = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    private String roleType;

    @Builder
    public Member(String name, String email, String password, List<Post> posts, List<MemberLostItem> memberLostItems, List<Comment> comments, String roleType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.posts = posts;
        this.memberLostItems = memberLostItems;
        this.comments = comments;
        this.roleType = roleType;
    }
}
