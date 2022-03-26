package hellojpa.inheritanceMapping;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn        //DTYPE - 기본으로 하위 엔티티들의 클래스명이 들어간다. 바꾸고 싶으면 하위 클래스에 @DiscriminatorValue("") 쓰면 됨.
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
