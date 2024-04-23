package com.chuanchuan.seata.order.conf;

import com.alibaba.nacos.common.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
public class H5Conf {
    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    private String sessionClass;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan("com.zzyy");

        Properties prop = this.getJpaProperties();
        prop.setProperty("hibernate.current_session_context_class", sessionClass);

        sfb.setHibernateProperties(prop);

        return sfb;
    }

    private Properties getJpaProperties() {
        Properties prop = new Properties();
        Map<String, String> map = jpaProperties.getProperties();
        if (MapUtils.isNotEmpty(map)) {
            for (String key : map.keySet()) {
                prop.setProperty(key, map.get(key));
            }
        }
        return prop;
    }
}
