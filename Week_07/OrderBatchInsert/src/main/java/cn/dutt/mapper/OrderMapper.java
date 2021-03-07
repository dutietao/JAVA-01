package cn.dutt.mapper;

import cn.dutt.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderMapper {

    @Insert("INSERT INTO ORDER(trans_date, trans_amount) VALUES(#{transDate}, #{transAmount})")
    public void saveOrder(@Param("transDate") LocalDate transDate, @Param("transAmount") BigDecimal transAmount);

    @Insert({
           "<script>",
            "INSERT INTO ORDER(trans_date, trans_amount)",
            "VALUES ",
            "<foreach collection='items' item='item' index='index' separator=','>",
            "(#{item.transDate}, #{item.transAmount})",
            "</foreach>",
            "<script>"
    })
    public void batchSaveOrder(List<Order> items);

}
