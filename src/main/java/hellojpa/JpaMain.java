package hellojpa;

import hellojpa.cascade.Child;
import hellojpa.cascade.Parent;
import hellojpa.inheritanceMapping.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class JpaMain {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();   //트랜잭션 시작

        //스프링이 자동으로 다 해주므로 스프링이랑 같이 쓸때는 em.persist(member)만해주면 자동으로 다 됨.
        try{
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent1 = new Parent();
            parent1.addChild(child1);
            parent1.addChild(child2);

            em.persist(parent1);    //Parent에서 cascade 설정한 덕분에 parent1만 persist()해도 child1, child2 모두 persist()됨.

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent1.getId());
            findParent.getChildList().remove(0);      //부모 컬렉션에서 삭제 -> 0번은 고아 객체됨.

            tx.commit();   // 트랜잭션 커밋( 커밋을 꼭 해야 반영이 된다. ->영속성 컨텍스트에 저장된 객체들이 커밋 이 시점에 디비로 쿼리 날라가는 것이다.)

        }catch (Exception e){   //예외 발생 시 롤백
            tx.rollback();
        }finally {              //작업이 완료되면 엔티티 매니저 닫기.(엔티티매니저가 내부적으로 데이터베이스 커낵션을 물고 동작하므로 항상 닫아줘야한다.)
            em.close();
        }

        emf.close();  //was(웹어플리케이션)가 내려갈때 엔티티매니저 팩토리를 닫아야한다. -> 그래야 리소스 릴리즈됨.
    }
}
// EntityManagerFactory는 애플리케이션 로딩 시점에 딱 하나만 만든다.
// 고객이 어떤 상품을 장바구니에 담거나, 할 때마다 디비 커넥션을 날려서 쿼리를 날리고 종료되는 이런 일괄적인 단위를 할 때마다 EntityManager를 만들어야한다.
// 고객 요청시마다 엔티티 매니저를 호출한다.
// JPA는 트랜잭션이 중요하다! 데이터를 변경하는 모든 작업은 트랜잭션 안에서 작업해야한다.
// JPA를 통해서 엔티티를 가져오면 JPA가 계속 관리를 한다. 이 엔티티가 변경이 되었는지 트랜잭션 커밋 시점에 체크를 한다. -> 변경됐다면 Update쿼리를 날리고 트랜잭션 커밋한다.