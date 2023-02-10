package project.toy.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name="postId")
    private Post post;

    @Builder
    public Comment(Long id, String comment, Member member, Post post){
        this.id = id;
        this.comment = comment;
        this.member = member;
        this.post = post;

        if(member != null && !member.getComments().contains(this)){
            member.getComments().add(this);
        }

        if(post != null && !post.getComments().contains(this)){
            post.getComments().add(this);
        }
    }
}
