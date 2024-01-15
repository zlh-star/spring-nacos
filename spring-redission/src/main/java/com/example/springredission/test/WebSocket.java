//package com.example.springredission.test;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//
//@ServerEndpoint(value = "/websocket/{userName}")
//@Component
//public class WebSocket {
//    //ws://localhost:8200/websocket/{username}
//    private final static Logger logger = LogManager.getLogger(WebSocket.class);
//
//    /**
//     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
//     */
//
//    private static int onlineCount = 0;
//
//    /**
//     * concurrent包的线程安全Map，存放每个客户端对应的MyWebSocket对象
//     */
//    private static final ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();
//
//    /**
//     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
//     */
//
//    private Session session;
//    private String userName;
//
//
//    /**
//     * 连接时调用的方法(success)
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("userName") String userName) {
//        this.session = session;
//        this.userName = userName;
//        //加入map
//        webSocketMap.put(userName, this);
//        addOnlineCount();           //在线数加1
//        logger.info("用户{}连接成功,当前在线人数为{}", userName, getOnlineCount());
//        try {
//            sendMessage(String.valueOf(this.session.getQueryString()));
//        } catch (IOException e) {
//            logger.error("IO异常");
//        }
//    }
//
//
//    /**
//     * 关闭连接调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        //从map中删除
//        webSocketMap.remove(userName);
//        subOnlineCount();           //在线数减1
//        logger.info("用户{}关闭连接！当前在线人数为{}", userName, getOnlineCount());
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        logger.info("来自客户端用户：{} 消息:{}",userName, message);
//
//        //群发消息
//        for (String item : webSocketMap.keySet()) {
//            try {
//                webSocketMap.get(item).sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 发生错误时调用
//     *
//     * @OnError
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        logger.error("用户错误:" + userName + ",原因:" + error.getMessage());
//        error.printStackTrace();
//    }
//
//    /**
//     * 向客户端发送消息
//     */
//    public void sendMessage(String message) throws IOException {
//        session.getBasicRemote().sendText(message);
//    }
//
//    /**
//     * 通过userId向客户端发送消息
//     */
//    public void sendMessageByUserName(String userName, String message) throws IOException {
//        logger.info("服务端发送消息到{},消息：{}",userName,message);
//        if(StringUtils.isNotBlank(userName)&&webSocketMap.containsKey(userName)){
//            webSocketMap.get(userName).sendMessage(message);
//        }else{
//            logger.error("用户{}不在线",userName);
//        }
//
//    }
//
//    /**
//     * 群发自定义消息
//     */
//    public void sendInfo(String message) throws IOException {
//        for (String item : webSocketMap.keySet()) {
//            try {
//                webSocketMap.get(item).sendMessage(message);
//            } catch (IOException e) {
//                continue;
//            }
//        }
//    }
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        WebSocket.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebSocket.onlineCount--;
//    }
//
//}
//
//
