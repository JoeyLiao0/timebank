package tb.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/test")
public class MyWebSocketServer {

    // 使用ConcurrentHashMap来存储会话和会话ID的映射  
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        // 当新的WebSocket连接建立时，将会话ID和会话对象存入映射  
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("New connection established: " + sessionId);
    }

    @OnClose
    public void onClose(Session session) {
        // 当WebSocket连接关闭时，从映射中移除会话  
        String sessionId = session.getId();
        sessions.remove(sessionId);
        System.out.println("Connection closed: " + sessionId);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // 处理接收到的消息  
        System.out.println("Received message: " + message);
        session.getBasicRemote().sendText("I hear you");
        // 可以根据需要向其他会话发送消息  
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // 处理错误  
        System.err.println("Error occurred: " + throwable.getMessage());
    }

    // 向指定会话发送消息的方法  
    public static void sendMessageToSession(String sessionId, String message) throws IOException {
        Session session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        } else {
            System.err.println("Session not found or not open: " + sessionId);
        }

    }


}