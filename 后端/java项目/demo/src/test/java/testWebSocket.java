import org.junit.Test;
import tb.websocket.MyWebSocketServer;

import java.io.IOException;

public class testWebSocket {
    @Test
    public void test(){
        // 假设你有一个会话ID "someSessionId" 和一个消息 "Hello, client!"
        String someSessionId = "someSessionId"; // 替换为实际的会话ID
        String messageToSend = "Hello, client!";

        try {
            MyWebSocketServer.sendMessageToSession(someSessionId, messageToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


