package proxy.cglib;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/1 Created
 */
public class PersonService {
    public PersonService() {
        System.out.println("PersonService constructor.");
    }

    public final void getPerson(String code) {
        System.out.println("PersonService:getPerson>>" + code);
    }

    public void setPerson() {
        System.out.println("PersonService:setPerson>>");
    }
}
