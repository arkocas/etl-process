package com.alirizakocas.etl.process.service;

import com.alirizakocas.etl.process.model.entity.BestsellerEntity;
import com.alirizakocas.etl.process.model.entity.ProductEntity;
import com.alirizakocas.etl.process.repository.source.IBestsellerRepository;
import com.alirizakocas.etl.process.repository.source.IProductRepository;
import com.alirizakocas.etl.process.repository.target.ITargetBestsellerRepository;
import com.alirizakocas.etl.process.repository.target.ITargetProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class EtlProcessService {
    private final IBestsellerRepository bestsellerRepository;
    private final IProductRepository productRepository;
    private final ITargetBestsellerRepository targetBestsellerRepository;
    private final ITargetProductRepository targetProductRepository;

    @Autowired
    public EtlProcessService(IBestsellerRepository bestsellerRepository, ITargetBestsellerRepository targetBestsellerRepository, ITargetProductRepository targetProductRepository, IProductRepository productRepository) {
        this.bestsellerRepository = bestsellerRepository;
        this.targetBestsellerRepository = targetBestsellerRepository;
        this.targetProductRepository = targetProductRepository;
        this.productRepository = productRepository;
    }

    public void publishBestSellers() {
        List<BestsellerEntity> bestsellerList = bestsellerRepository.bestSellers();

        if(!CollectionUtils.isEmpty(bestsellerList)) {
            targetBestsellerRepository.saveAll(bestsellerList);
        }
    }

    public void publishProducts() {
        List<ProductEntity> productList = productRepository.findAll();

        if(!CollectionUtils.isEmpty(productList)) {
            targetProductRepository.saveAll(productList);
        }
    }
}
