package hellojpa.inheritanceMapping;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")   //안쓰면 그냥 기본적으로 DTYPE이 클래스 명("Movie")으로 들어감
public class Movie extends Item {

    private String director;
    private String actor;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
