package cn.dutt.service;

import cn.dutt.entity.Order;
import cn.dutt.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderMapper mapper;

    public void saveOrder(LocalDate transDate, BigDecimal transAmount){
        try{
            mapper.saveOrder(transDate,transAmount);
        }catch (Exception e){
            log.error("saveOrder error");
        }
    }

    public void batchSaveOrder(List<Order> list){
        try{
            mapper.batchSaveOrder(list);
        }catch (Exception e){
            log.error("saveOrder error");
        }
    }
}
