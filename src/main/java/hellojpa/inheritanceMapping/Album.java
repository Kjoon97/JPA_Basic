package hellojpa.inheritanceMapping;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")   //안쓰면 그냥 기본적으로 DTYPE이 클래스 명("Album")으로 들어감
public class Album extends Item {

    private String artist;
}
