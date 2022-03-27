package hellojpa;

import hellojpa.embededtype.Address;

public class ValueMain {

    public static void main(String[] args) {
        int a =10;
        int b = 10;

        System.out.println("a == b = " + (a==b));

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("address1 == address2:" + (address1 == address2));
        System.out.println("address1 equals address2:" + (address1.equals(address2)));   //값 비교.(예) 회사 주소랑 자택 주소랑 같은지 확인해서 어떤 비지니스 로직을 사용할 경우.

    }
}
