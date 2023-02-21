package project.toy.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;

    @Builder
    public Comment(String comment, Member member, Post post){
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
