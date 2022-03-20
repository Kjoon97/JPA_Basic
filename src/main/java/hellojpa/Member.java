package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity     //JPA를 사용하는 애구나 인식하게됨.
@TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)  //identity전락은 값을 넣으면 안되고 디비에 insert해줘야함
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
