package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity     //JPA를 사용하는 애구나 인식하게됨.
public class Member {

    @Id
    private Long id;

    @Column(name = "name")   //객체는 username, DB 테이블의 컬럼 명은 name이라고 하고싶을 때.
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)   //객체에는 enum 타입을 쓰는데 DB는 그런 타입이 없어서 @Enumerated 추가 해주면된다.
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;   //데이터 베이스에 varchar를 넘어서는 큰 내용을 넣고 싶을 떼 Lob 사용하면 됨.

    @Transient   //DB 테이블에 매핑 안함. 그냥 단지 메모리 상에서만 활용하고 싶을 때 사용.
    private int temp;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
