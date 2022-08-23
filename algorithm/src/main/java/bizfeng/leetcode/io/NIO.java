package bizfeng.leetcode.io;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wz
 * @Date: 2022/8/19 16:07
 * @Description:
 */
@Slf4j
public class NIO {

    public static void main(String[] args) {
        Thread serverThread = new Thread(NIO::server);
        serverThread.start();

        Thread client1 = new Thread(NIO::client, "客户端-1");
        client1.start();

        Thread client2 = new Thread(NIO::client, "客户端-2");
        client2.start();
    }

    private static void server() {
        ServerSocketChannel server = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        List<SocketChannel> socketChannelList = new ArrayList<>();
        try {
            server = ServerSocketChannel.open();

            server.configureBlocking(false);

            server.bind(new InetSocketAddress("127.0.0.1", 9999));

            while (true) {
                for (SocketChannel socketChannel : socketChannelList) {
                    int len = socketChannel.read(buffer);
                    if (len > 0) {
                        buffer.flip();
                        log.info("server get msg: {}", new String(buffer.array(), 0, len, StandardCharsets.UTF_8));
                        buffer.clear();
                    }
                }
                SocketChannel accept = server.accept();
                if (accept != null) {
                    accept.configureBlocking(false);
                    socketChannelList.add(accept);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void client() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
            socketChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Thread thread = Thread.currentThread();

            while (true) {
                TimeUnit.SECONDS.sleep(RandomUtil.randomLong(1, 6) / 3);
                buffer.put((thread.getName() + ": " + RandomUtil.randomString(4)).getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
