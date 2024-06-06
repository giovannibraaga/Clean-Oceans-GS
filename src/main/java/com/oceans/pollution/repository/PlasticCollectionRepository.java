package com.oceans.pollution.repository;

import com.oceans.pollution.model.PlasticCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlasticCollectionRepository extends JpaRepository<PlasticCollection, Long> {
}
