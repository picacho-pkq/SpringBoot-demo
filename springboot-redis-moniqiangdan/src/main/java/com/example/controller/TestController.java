package com.example.controller;


import com.example.redisutil.JedisCom;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


@Controller
public class TestController {

    //总库存
    private long nKuCuen = 0;
    //商品key名字
    private String shangpingKey = "computer_key";
    //获取锁的超时时间 秒
    private int timeout = 60 * 1000;

    @GetMapping ("/qiangdan")
    @ResponseBody
    public List<String> qiangdan() {
        //抢到商品的⽤⼾
        List<String> shopUsers = new ArrayList<>();
        //构造很多⽤⼾
        List<String> users = new ArrayList<>();
        IntStream.range(0, 100000).parallel().forEach(b -> { users.add("pikacho-" + b); });

        //初始化库存
        nKuCuen = 10;

        //模拟开抢
        users.parallelStream().forEach(b -> { String shopUser = qiang(b);
            if(!StringUtils.isEmpty(shopUser)) {
                shopUsers.add(shopUser);
            }
        });
        return shopUsers;
    }

    /**
     * 模拟抢单动作
     *
     * @param b
     * @return
     */
    private String qiang(String b) {
        //⽤⼾开抢时间
        long startTime = System.currentTimeMillis();
        //未抢到的情况下，60秒内继续获取锁
        while((startTime + timeout) >= System.currentTimeMillis()) {
            //商品是否剩余
            if(nKuCuen <= 0) {
                break ;
            }
            if(new JedisCom().setnx(shangpingKey, b)) {
                //⽤⼾b拿到锁
                System.out.println("⽤⼾{}拿到锁..." + b);
                try {
                    //商品是否剩余
                    if(nKuCuen <= 0) {
                        break ;
                    }
                    //模拟⽣成订单耗时操作，⽅便查看：神⽜-50 多次获取锁记录
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }

                    //抢购成功，商品递减，记录⽤⼾
                    nKuCuen -= 1;
                    //抢单成功跳出
                    System.out.println("⽤⼾{}抢单成功跳出...所剩库存：{}" + b + nKuCuen);
                    return b + "抢单成功，所剩库存：" + nKuCuen;
                }finally{
                    System.out.println("⽤⼾{}释放锁..." + b);
                    //释放锁
                    new JedisCom().delnx(shangpingKey, b);
                }
            } else{
                //⽤⼾b没拿到锁，在超时范围内继续请求锁，不需要处理
                // if (b.equals("神⽜-50") || b.equals("神⽜-69")) {
                // logger.info("⽤⼾{}等待获取锁...", b);
                // }
            }
        }
        return "";
    }




    @GetMapping("/setnx/{key}/{val}")
    @ResponseBody
    public boolean setnx(@PathVariable String key, @PathVariable String val) {
        JedisCom jedisCom = new JedisCom();
        return jedisCom.setnx(key, val);
    }

    @GetMapping("/delnx/{key}/{val}")
    @ResponseBody
    public int delnx(@PathVariable String key, @PathVariable String val) {
        JedisCom jedisCom = new JedisCom();
        return jedisCom.delnx(key, val);
    }
}
