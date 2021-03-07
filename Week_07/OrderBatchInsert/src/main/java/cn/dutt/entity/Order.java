package cn.dutt.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Order {

    private Integer id;

    private LocalDate transDate;

    private BigDecimal transAmount;
}
