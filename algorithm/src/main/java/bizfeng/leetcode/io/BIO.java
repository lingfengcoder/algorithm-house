package bizfeng.leetcode.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wz
 * @Date: 2022/8/19 16:06
 * @Description:
 */
@Slf4j
public class BIO {

    public static void main(String[] args) {
        Thread serverThread = new Thread(BIO::server);
        serverThread.start();

        Thread client1 = new Thread(BIO::client, "客户端-1");
        client1.start();

        Thread client2 = new Thread(BIO::client, "客户端-2");
        client2.start();
    }


    private static void server() {
        ServerSocket server;
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress("127.0.0.1", 9999));
            log.info(" 服务端启动 ");
            while (true) {
                //获取新的 连接上的客户端
                Socket socket = server.accept();

                if (!socket.isClosed()) {
                    SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
                    log.info("server 获取到 client :{} 连接", remoteSocketAddress);
                    InputStream inputStream = socket.getInputStream();
                    byte[] buf = new byte[1024];
                    int len;

                    //读取客户端传输的内容
                    while ((len = inputStream.read(buf)) >0) {
                        log.info(" server get {}", new String(buf, 0, len, StandardCharsets.UTF_8));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void client() {
        try {
            Socket client = new Socket();
            Thread thread = Thread.currentThread();
            client.connect(new InetSocketAddress("127.0.0.1", 9999));
            log.info(thread.getName()+": 开始连接 ");
            OutputStream outputStream = client.getOutputStream();
            while (true) {
                TimeUnit.SECONDS.sleep(RandomUtil.randomLong(5,10)/2);

                String data = thread.getName() + ": " + RandomUtil.randomString(5);
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
