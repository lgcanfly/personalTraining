package pattern.decorator;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/1 Created
 */
public class PopularSinger implements Soundable {
    private Soundable soundable;

    @Override
    public void sound() {
        System.out.println("Obvious, I am popular.");
        soundable.sound();
    }
}
