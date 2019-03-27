package reflect;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/8 Created
 */
public class SimpleInfo {
    private static String DEMO = "I am a static member of SimpleInfo.";

    static {
        System.out.println("Loading class SimpleInfo.");
    }

    private String id;
    private String info;

    public SimpleInfo() {

    }

    public SimpleInfo(String id, String info) {
        this.id = id;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    String getInfo() {
        return info;
    }

    protected void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SimpleInfo:{id:").append(id)
                .append(", info:").append(info).append("}");
        return builder.toString();
    }
}
