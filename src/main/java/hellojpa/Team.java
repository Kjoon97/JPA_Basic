package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")   // "team"은 Member 클래스의 변수 명. members 변수로는 조회만 가능, 수정,등록은 연관관계 주인인 Member 클래스의 team 변수로 해야 가능.
    private List<Member> members = new ArrayList<>();  //ArrayList<>()로 초기화하면 add할때 null포인트가 안 뜸.
    //members 변수에 값을 넣어도 DB에 들어 가지 않는다(연관관계 주인이 아니기 때문!!)

    //연관관계 편의 매소드 여기서 구현해도 되고, Member 에서 구현해도된다.
//    public void addMember(Member member) {
//        member.setTeam(this);
//        members.add(member);
//    }

    public List<Member> getMembers() {
        return members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
