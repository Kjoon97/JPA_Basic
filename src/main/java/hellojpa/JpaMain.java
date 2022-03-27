package hellojpa;

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
            Team teamA = new Team();
            teamA.setName("teamA");
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamA);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("user1");
            Member member2 = new Member();
            member2.setUsername("user2");
            member1.setTeam(teamA);
            member2.setTeam(teamB);
            em.persist(member1);
            em.persist(member2);

            em.flush();
            em.clear();

            //em.find()는 PK로 가져오는 것이기 때문에 JPA가 내부적으로 추적 가능하다.
            //그러나 JPQL은 그대로 sql문으로 번역이 된다 -> 멤버만 가져오려는데 팀이 즉시로딩으로 되어있네? -> 즉시로딩하려면 무조건 Team 값이 다 들어가있어야함 (지연 로딩이면 그냥 Team 프록시를 넣으면 됨.)
            //->그러면 Member쿼리 날라가고, Member의 개수가 10개이면 10개만큼 즉시로딩인 team을 가져오기 위해 별도의 쿼리문이 날라간다.

            //정리 :JPQL-> SQL 번역 : select * from Member
            //그리고 Team을 즉시 로딩하기 위해서 Team값을 다 가져와야하므로 select * from Team where TEAM_ID = member...까지 하게된다.
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            //초반에 날리는 쿼리 한번 + N개의 객체이면 N번 실행 됨. = N+1문제라고 한다.
            //멤버만 조회를 하고 싶어서 날린 쿼리인데 멤버랑 엮인 팀 객체가 N개이면  -> 멤버 조회 쿼리 1번 + 팀 객체 조회 쿼리(N번)
            //결국 멤버 하나 조회하려고 쓸데없이 N번을 더 쿼리가 날라가는 것이다.
            //지연 로딩하면 Team 객체가 프록시로 대체 되므로 (Team)N번의 쿼리가 날라가지 않고 Member 1번만 조회된다.

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