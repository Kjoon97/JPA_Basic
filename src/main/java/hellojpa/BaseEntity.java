package hellojpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass           //공통적으로 매핑하고 싶은 것들은 이걸 이용하면 됨. 엔티티가 아님, 테이블도 생성 안됨. 조회 검색 불가. 상속관계 매핑 아님.
public abstract class BaseEntity {   //직접 사용할 일이 없으므로 추상 클래스로 만드는 것 권장.

    @Column(name="INSERT_MEMBER")  //칼럼 명 명시 가능
    private String createBy;
    private LocalDateTime createDate;

    @Column(name="UPDATE_MEMBER")
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
