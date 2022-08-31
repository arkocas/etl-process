package com.alirizakocas.etl.process.repository.target;

import com.alirizakocas.etl.process.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITargetProductRepository extends JpaRepository<ProductEntity, String> {
}
