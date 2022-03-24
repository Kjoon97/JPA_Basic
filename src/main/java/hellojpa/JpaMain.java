package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();   //트랜잭션 시작

        //스프링이 자동으로 다 해주므로 스프링이랑 같이 쓸때는 em.persist(member)만해주면 자동으로 다 됨.
        try{
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);  //em.persist()되면 항상 id값 들어감. 영속 상태 들어가기전 무조건 pk값 세팅된다.

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);       //연관관계 주인인 곳에서 세팅한경우.
          //  team.addMember(member);      //연관관계 주인이 아닌 곳에서 세팅한 경우.

            em.persist(member);
//            em.flush();
//            em.clear();
            //flush와 clear를 하면 1차 캐시에 아무 것도 없기때문에 DB에서 다시 조회한다. 그렇기 때문에  JPA가 외래키도 인식해서 members도 다시 조회해야겠고 동작한다.
            //하지만 flush와 clear를 안할 때는 1차 캐시가 존재하므로 연관관계 주인에서만 세팅을 한 상태이면 findTeam에서 member가 빈 상태이다.
            //그러므로 연관관계 주인이 아닌 곳에서도 세팅을 해줘야한다.(team.getMembers().add(member))
            //일반적인 상황에서는 flush와 clear를 하지 않는다.학습을 위해서 쿼리문을 보기 위해서 flush와 clear를 한것이다.

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            System.out.println("====================");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("====================");

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