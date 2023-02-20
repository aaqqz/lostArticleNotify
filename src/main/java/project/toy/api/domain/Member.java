package project.toy.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private List<MemberLostItem> memberLostItems = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    private String roleType;
//    @JsonIgnore
//    private boolean activated;
//
//    @ManyToMany
//    @JoinTable(
//            name = "MEMBER_AUTHORITY",
//            joinColumns = {@JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
//    )
//    private Set<Authority> authorities;

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
