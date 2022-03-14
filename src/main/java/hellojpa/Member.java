package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity     //JPA를 사용하는 애구나 인식하게됨.
public class Member {

    @Id                 //주요 키
    private Long id;
    private String name;

    public Member(){      //JPA의 기본 스펙은 엔티티에 기본 생성자가 필수로 있어야 한다.
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
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
