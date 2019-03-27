package pattern.decorator;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/1 Created
 */
public class Singer extends AbstractSounder {
    @Override
    protected String fetchSound() {
        return "I am a singer.";
    }
}
