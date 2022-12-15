package com.example.springwebsocket.controller;

import com.example.springwebsocket.test.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

@RestController
@RequestMapping("/webSocket")
public class TestController {

    @Autowired
    private WebSocket webSocket;
    @PostMapping("/sentMessage")
    public void sentMessage(String userId,String message){
        try {
            webSocket.sendMessageByUserId(userId,message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/IP",method = RequestMethod.POST)
    public void IP(){
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            ArrayList<String > list = new ArrayList<>();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                //isUp判断是否是启动状态
                //isVirtual 判断是否是虚拟Ip
                //isLoopback 判断是否是子网络接口
                if (networkInterface.isUp()&&!networkInterface.isVirtual()&&!networkInterface.isLoopback()) {

                    //内部网络地址
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (inetAddress instanceof Inet4Address) {
                            String hostAddress = inetAddress.getHostAddress();
                            String hostName = inetAddress.getCanonicalHostName();
                            list.add(hostAddress);
                        }
                    }
                }
            }
            System.out.println("新方式--------"+list);
            String IPS=String.join(",",list);
            String[] arr = IPS.split(",");
            String str=null;
            int var4 = arr.length;
            byte var5 = 4;
            if (var5 < var4) {
                str = arr[var5];
            }
            System.out.println("str:"+str);

        } catch (
                SocketException e) {
            e.printStackTrace();
        }
    }


}

