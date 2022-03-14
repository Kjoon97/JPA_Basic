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
            //멤버 추가.
//            Member member = new Member();
//            member.setId(2L);            //long 타입이므로 L을 붙임.
//            member.setName("HelloB");
//            em.persist(member);

            //멤버 조회
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id=" +findMember.getId());
//            System.out.println("findMember.name="+ findMember.getName());

            //JPQL을 이용해 전체 멤버 조회
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)  //페이지 기능 5번째부터 8번째까지 조회
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());;
            }

            //멤버 수정.
//            findMember.setName("HelloJPA");

            //멤버 삭제
            //em.remove(findMember);

            tx.commit();   // 트랜잭션 커밋( 커밋을 꼭 해야 반영이 된다.)

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