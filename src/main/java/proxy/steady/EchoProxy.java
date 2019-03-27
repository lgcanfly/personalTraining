package proxy.steady;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/18 Created
 */
public class EchoProxy implements Echo {
    private Echo echo;

    public EchoProxy(Echo echo) {
        this.echo = echo;
    }

    @Override
    public void echo() {
        System.out.println("Begin echo proxy.");
        echo.echo();
        System.out.println("End echo proxy.");
    }

    public static void main(String[] args) {
        EchoProxy proxy = new EchoProxy(new EchoImpl());
        proxy.echo();
    }
}
