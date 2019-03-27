package pattern.decorator;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/1 Created
 */
public abstract class AbstractSounder implements Soundable {
    @Override
    public void sound() {
        String something = fetchSound();
        System.out.println(something);
    }

    protected abstract String fetchSound();
}
