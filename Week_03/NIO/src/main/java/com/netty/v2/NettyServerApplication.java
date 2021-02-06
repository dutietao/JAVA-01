package com.netty.v2;


import com.netty.v2.inbound.HttpInboundServer;

public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "3.0.0";

    //处理步骤
    //1请求被接收
    //2请求数据过滤
    //3请求路由
    //4请求对应服务器
    //5服务器返回响应数据
    //6返回数据过滤

    /**
     *  inbound 指的是 数据从socket读取后的处理过程 由下向上
     *  outbound 指的是 数据从 socket写的过程 ，由上向下
     */


    public static void main(String[] args) {

        //netty 服务器的绑定端口
        String proxyPort = System.getProperty("proxyPort","8888");

        //后端服务器的列表，现在为1个服务器提供服务
        String proxyServer = System.getProperty("proxyServer","http://localhost:8081");

        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(port, proxyServer);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
