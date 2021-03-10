package com.lcc.jvm;

import java.io.IOException;

/**
 * // 打开jmx,必填
 * -Dcom.sun.management.jmxremote
 *
 * // 绑定host,必填 如果在想在公网使用，需要填公网ip
 * -Djava.rmi.server.hostname=127.0.0.1
 *
 * // 绑定port,必填
 * -Dcom.sun.management.jmxremote.port=16013
 *
 * // 是否开启ssl,必填  如果不填，又没有用ssl就会出问题
 * -Dcom.sun.management.jmxremote.ssl=false
 *
 * // 是否开启鉴权,必填 如果不填，报错 sun.management.AgentConfigurationError
 * -Dcom.sun.management.jmxremote.authenticate=false
 *
 * // 使用指定的JMX帐号授权文件
 * -Dcom.sun.management.jmxremote.access.file=/conf/jmxremote.access
 *
 * // 使用指定的JMX帐号文件
 * -Dcom.sun.management.jmxremote.password.file=/conf/jmxremote.password
 *
 * // 报错 sun.management.AgentConfigurationError
 * -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.port=16013
 *
 * // 使用prometheus jmx_exporter 链接不上
 * // ssl: true 报错java.io.EOFException: SSL peer shut down incorrectly
 * -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.port=16013 -Dcom.sun.management.jmxremote.authenticate=false
 * // ssl: true 报错java.io.EOFException: SSL peer shut down incorrectly
 * -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.port=16013 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=true
 *
 * -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.port=16013 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
 *
 */
public class JmxDemo {

    public static void main(String[] args) throws IOException {
        try {
            while (true) {
                System.out.println("开始");
                System.in.read();
                System.out.println("结束");
            }
        } finally {
            System.out.println("结束前回调");
        }
    }

}
