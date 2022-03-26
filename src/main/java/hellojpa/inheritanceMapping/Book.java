package hellojpa.inheritanceMapping;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")   //안쓰면 그냥 기본적으로 DTYPE이 클래스 명("Book")으로 들어감
public class Book extends Item {

    private String author;
    private String isbn;
}
