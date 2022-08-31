package com.alirizakocas.etl.process.repository.source;

import com.alirizakocas.etl.process.model.entity.BestsellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBestsellerRepository extends JpaRepository<BestsellerEntity, String> {
    @Query(nativeQuery = true,
            value = "select product_id, count(distinct user_id) as quantity from order_items join orders on order_items.order_id = orders.order_id group by order_items.product_id order by count(distinct orders.user_id) desc")
    List<BestsellerEntity> bestSellers();
}
