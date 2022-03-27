package hellojpa.cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent" ,cascade = CascadeType.ALL, orphanRemoval = true)  // parent만 persist하면 child도 다 persist됨.
    private List<Child> childList = new ArrayList<>();                                 //orphanRemoval = true - 고아 객체 삭제

    //연관관계 매핑
    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    public void setName(String name) {
        this.name = name;
    }
}
