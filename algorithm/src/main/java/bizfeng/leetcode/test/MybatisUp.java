package bizfeng.leetcode.test;

import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: wz
 * @Date: 2022/6/17 20:06
 * @Description:
 */
@Slf4j
public class MybatisUp {

    public static void main(String[] args) throws FileNotFoundException {
        File dir = new File("D:\\company\\code\\sansu\\c1outlocal\\src\\com\\iptvInterface\\model");

        boolean directory = dir.isDirectory();
        if (directory) {
            File[] files = dir.listFiles();
            for (File file : files) {
                FileReader fileReader = FileReader.create(file, StandardCharsets.UTF_8);
                fileReader.readLines((LineHandler) s -> {
                    if(s.contains("id=\"")){
                       // s=s.substring();
                    }
                });
            }
        } else {
            log.error("{} not directory", dir.getAbsolutePath());
        }
    }
}
