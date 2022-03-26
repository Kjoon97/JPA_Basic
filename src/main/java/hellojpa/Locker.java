package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {

    @Id @GeneratedValue
    private Long id;

    private String name;

    //Locker에도 Member랑 양방향 관계 설정하고 싶으면 다음 추가
    @OneToOne(mappedBy = "locker")
    private Member member;
}
