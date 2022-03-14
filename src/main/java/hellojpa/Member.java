package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity     //JPA를 사용하는 애구나 인식하게됨.
public class Member {

    @Id                 //주요 키
    private Long id;
    private String name;

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
