package com.alirizakocas.etl.process.repository.target;

import com.alirizakocas.etl.process.model.entity.BestsellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITargetBestsellerRepository extends JpaRepository<BestsellerEntity, String> {
}
