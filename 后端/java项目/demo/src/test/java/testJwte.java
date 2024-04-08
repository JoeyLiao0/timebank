import org.junit.Test;
import tb.util.myJwt;

public class testJwte {
    @Test
    public void testJwt(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRlc3QiLCJyb2xlIjoiQ1UiLCJpZCI6IjEiLCJzZXNzaW9uSWQiOiIxIiwiZXhwIjoxNzEyMzk2OTI1LCJpc3MiOiJMaWFvIiwiaWF0IjoxNzEyMzkzMzI1fQ.XCQ2S2tiBYinhfdivTOhJ67tNQFWc6GlCVdxnJFriBM";
        myJwt mj = new myJwt(token);
        String id = (String) mj.getValue("id");
        String role = (String) mj.getValue("role");
        String sessionId = (String) mj.getValue("sessionId");
        String name = (String) mj.getValue("name");
        System.out.println(id+role+sessionId+name);
    }
}
