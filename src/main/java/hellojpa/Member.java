package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

//    @Column(name="TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")    //관계와 조인하는 컬럼 명시
    private Team team;
    // 1대N 관계에서 N인 곳( = ManyToOne 을 쓰는 곳 = 외래키가 있는 곳)을 연관관계 주인으로 해야한다.!!

    @OneToOne
    @JoinColumn(name="LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    //연관관계 편의 매소드 ( setTeam 함수로 해도 괜찮)
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); //Team에서 member 추가를 Member가 team set할 때 같이하자! this는 member이다.
                                    // (연관관계 주인에서도 세팅하는건 당연하지만 )아닌 곳에서도 세팅을해주는 것이 좋다.
                                    //어차피 연관관계 주인이 아닌 team에서 add해봤자 무반응이서 연관관계만 생각하면 추가를 안해도된다.
        // 그런데 만약에 JPA를 사용하지 않고, 순수한 자바 코드로 테스트를 한다거나 그러면 add를 하지 않으면 양방향 연관관계가 유지되지 않습니다.
        //단위테스트를 작성하면 순수한 객체를 바로 테스트 할 수도 있으니까요^^!
        //JPA의 기술적인 관점도 중요하지만, 또 한편으로 객체 관점도 중요하니까요^
    }

//    public void setTeam(Team team) {
//        this.team = team;
//    }
}
