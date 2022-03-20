package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)   //allocationSize: 디비에 미리 올려 놓고 그 개수 만큼 사용하는 방식
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name")   //객체는 username, DB 테이블의 컬럼 명은 name이라고 하고싶을 때.
        private String username;

    public Member(){      //JPA의 기본 스펙은 엔티티에 기본 생성자가 필수로 있어야 한다.
    }

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
}
