package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.db.CachePerformance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CachePerformanceRepository extends JpaRepository<CachePerformance, Long> {
}

