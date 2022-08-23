package bizfeng.leetcode.io;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wz
 * @Date: 2022/8/19 16:07
 * @Description:
 */
@Slf4j
public class NIO_Selector_Epoll {

    public static void main(String[] args) {
        Thread serverThread = new Thread(NIO_Selector_Epoll::server);
        serverThread.start();

        Thread client1 = new Thread(() -> client(true), "客户端-1");
        client1.start();

        Thread client2 = new Thread(() -> client(true), "客户端-2");
        client2.start();

        Thread client3 = new Thread(() -> client(false), "客户端-3");
        client3.start();
    }

    private static void server() {
        ServerSocketChannel server = null;
        try {
            server = ServerSocketChannel.open();

            server.configureBlocking(false);

            server.bind(new InetSocketAddress("127.0.0.1", 9999));

            //4.获取选择器
            Selector selector = Selector.open();
            //5.将通道注册到选择器上，并且开始指定监听的接收事件
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                log.info("selector size = {}", selector.selectedKeys().size());
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    //有新的连接进入
                    if (selectionKey.isAcceptable()) {
                        SocketChannel accept = server.accept();
                        //设置NIO模式
                        accept.configureBlocking(false);
                        //注册新的socket
                        accept.register(selector, SelectionKey.OP_READ);
                        log.info(" client {} register", accept.getRemoteAddress());
                    } else if (selectionKey.isReadable()) {
                        log.info("socket :{} is readable ", selectionKey.channel());
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len;
                        while ((len = socketChannel.read(buffer)) > 0) {
                            //切换读写模式
                            buffer.flip();

                            log.info(" server get msg :{}", new String(buffer.array(), 0, len, StandardCharsets.UTF_8));

                            buffer.clear();
                        }
                    }
                    //把当前 已经处理的事件删除
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void client(boolean active) {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
            socketChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Thread thread = Thread.currentThread();

            buffer.put((thread.getName() + ": connect ").getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();

            while (active) {
                TimeUnit.SECONDS.sleep(RandomUtil.randomLong(1, 6) / 3);
                buffer.put((thread.getName() + ": " + RandomUtil.randomString(4)).getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
