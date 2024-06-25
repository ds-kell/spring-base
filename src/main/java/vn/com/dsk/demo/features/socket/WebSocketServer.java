//package vn.com.dsk.demo.features;
//
//
//import jakarta.websocket.OnMessage;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.server.ServerEndpoint;
//import org.springframework.stereotype.Component;
//
//@ServerEndpoint("/api/public/websocket")
//@Component
//public class WebSocketServer {
//
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("Client connected");
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        System.out.println("Received message: " + message);
//        session.getAsyncRemote().sendText("Server received your message: " + message);
//    }
//}
