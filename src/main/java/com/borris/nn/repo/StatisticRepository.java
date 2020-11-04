package com.borris.nn.repo;

import com.borris.nn.entity.Statistic;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "statistic", path = "statistics")
public interface StatisticRepository extends PagingAndSortingRepository<Statistic, Long> {
}
