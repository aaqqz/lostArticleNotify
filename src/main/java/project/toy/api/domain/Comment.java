package project.toy.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;

    private int depthNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @OneToMany(mappedBy = "id")
    private List<Comment> childComment;

    @Builder
    public Comment(String comment, Member member, Post post, int depthNumber, Comment parentComment, List<Comment> childComment){
        this.comment = comment;
        this.member = member;
        this.post = post;
        this.parentComment = parentComment;
        this.childComment = childComment;

        if(member != null && member.getComments() != null && !member.getComments().contains(this)){
            member.getComments().add(this);
        }

        if(post != null && post.getComments() != null && !post.getComments().contains(this)){
            post.getComments().add(this);
        }
    }
}
