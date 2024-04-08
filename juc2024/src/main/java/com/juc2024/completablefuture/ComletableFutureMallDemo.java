package com.juc2024.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 跨境平台 产品比价
 * 每个平台的价格存储在不同的表里，比如 product_amazon product_shein product_shopee
 * <p>
 * 输入：产品名称、平台
 * 输出：价格
 */
public class ComletableFutureMallDemo {


    public static void main(String[] args) {
        List<String> platforms = Arrays.asList("shein", "amazon", "shopee");

        // 1、顺序获取耗时
        long start = System.currentTimeMillis();
        for (String platform : platforms) {
            PlatformPrice platformPrice = getPrice(platform, "高效能人士的七个习惯");
        }
        System.out.println("1、顺序获取耗时：" + (System.currentTimeMillis() - start));
        System.out.println();

        // 2、并行执行耗时
        start = System.currentTimeMillis();
        List<PlatformPrice> platformPriceList = platforms.stream()
                .map(e -> CompletableFuture.supplyAsync(() -> getPrice(e, "高效能人士的七个习惯")))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("2、并行获取耗时：" + (System.currentTimeMillis() - start));
    }

    private static PlatformPrice getPrice(String platform, String bookName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double price = ThreadLocalRandom.current().nextDouble(32, 86);
        PlatformPrice platformPrice = new PlatformPrice(platform, bookName, price);
        System.out.println(Thread.currentThread().getName() + platformPrice);

        return platformPrice;
    }

    static class PlatformPrice {
        String plarformName;
        String bookName;
        Double price;

        public PlatformPrice(String plarformName, String bookName, Double price) {
            this.plarformName = plarformName;
            this.bookName = bookName;
            this.price = price;
        }

        @Override
        public String toString() {
            return "PlatformPrice{" +
                    "plarformName='" + plarformName + '\'' +
                    ", bookName='" + bookName + '\'' +
                    ", price=" + price +
                    '}';
        }
    }


}
