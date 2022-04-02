package hellojpa;

import hellojpa.cascade.Child;
import hellojpa.cascade.Parent;
import hellojpa.embededtype.Address;
import hellojpa.embededtype.Member2;
import hellojpa.embededtype.Period;
import hellojpa.inheritanceMapping.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();   //트랜잭션 시작

        //스프링이 자동으로 다 해주므로 스프링이랑 같이 쓸때는 em.persist(member)만해주면 자동으로 다 됨.
        try{
            Member2 member = new Member2();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity","street","10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

//            member.getAddressHistory().add(new Address("old1","street","10000"));
//            member.getAddressHistory().add(new Address("old2","street","10000"));

            member.getAddressHistory().add(new AddressEntity("old1","street","10000"));
            member.getAddressHistory().add(new AddressEntity("old2","street","10000"));

            em.persist(member); //member 하나만 persist해도 addresshistory, favoritefood 모두 persist됨.이런 값타입 컬렉션의 생명 주기는 member에 의존함.
            em.flush();
            em.clear();

//            //값 타입 addressHistory,favoriteFoods 조회- 지연 로딩으로 이때 select 쿼리 날라감
//            Member2 foundMember = em.find(Member2.class, member.getId());
//            List<Address> addressHistory = foundMember.getAddressHistory();
//            for (Address address : addressHistory) {
//                System.out.println("address = " + address.getCity());
//            }
//            Set<String> favoriteFoods = foundMember.getFavoriteFoods();
//            for (String favoriteFood : favoriteFoods) {
//                System.out.println("favoriteFood = " + favoriteFood);
//            }
//
//            //값 타입은 수정하려면 완전히 새로 만들고 넣어줘야한다. (foundMember.getHomeAddress().setCity("newCity")이런 식으로하면 안됨)
//            Address a = foundMember.getHomeAddress();
//            foundMember.setHomeAddress(new Address("newCity",a.getStreet(),a.getZipcode()));
//
//            //컬렉션 안의 치킨-> 한식
//            foundMember.getFavoriteFoods().remove("치킨");
//            foundMember.getFavoriteFoods().add("한식");
//
//            //주소 바꾸기
//            foundMember.getAddressHistory().remove(new Address("old1","street","10000"));  //똑같은 대상을 찾아서 지워줌.(equals매소드 작동)
//            foundMember.getAddressHistory().add(new Address("newCity1","street","10000"));


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