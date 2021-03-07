package cn.dutt;

import cn.dutt.entity.Order;
import cn.dutt.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.IntStream;

@Slf4j
@MapperScan("com.dutt.mapper")
@ComponentScan(basePackages = {"com.dutt"})
@SpringBootApplication
public class OrderBatchInsertApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderBatchInsertApplication.class, args);

        OrderService service = context.getBean(OrderService.class);

        if(Objects.nonNull(args) && args.length>0 && StringUtils.equals("1",args[0])){
            long start = System.currentTimeMillis();

            IntStream.rangeClosed(1,1000000).forEach(i->{
                service.saveOrder(LocalDate.now(),new BigDecimal(i));
            });

            Long end = System.currentTimeMillis();

            log.info("save oder {}",end-start);

        }

        if(Objects.nonNull(args) && args.length>0 && StringUtils.equals("2",args[0])){
            long start = System.currentTimeMillis();

            IntStream.rangeClosed(1,1000000).forEach(i->{
                LinkedList<Order> orders = new LinkedList<>();
                Order order = new Order();
                order.setTransDate(LocalDate.now());
                order.setTransAmount(new BigDecimal(i));
                orders.add(order);
                if(orders.size()>10000){
                    service.batchSaveOrder(orders);
                    orders.clear();

                }
            });

            Long end = System.currentTimeMillis();

            log.info("save oder {}",end-start);

        }
    }

}
