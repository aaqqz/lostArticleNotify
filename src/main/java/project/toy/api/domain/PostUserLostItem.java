package project.toy.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostUserLostItem extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "LOSTITEM_ID")
    private Long id;

    private String item;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    @Builder
    public PostUserLostItem(Long id, String item, Member member){
        this.id = id;
        this.item = item;
        this.member = member;
        if(!member.getPostUserLostItems().contains(this)){
            member.getPostUserLostItems().add(this);
        }
    }
}
