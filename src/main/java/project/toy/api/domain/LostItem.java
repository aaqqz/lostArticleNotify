package project.toy.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.toy.api.status.LostStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class LostItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOSTITEM_ID")
    private Long id;

    private String item;

    private Date lostDate;


    @Enumerated(EnumType.STRING)
    private LostStatus status;
}
