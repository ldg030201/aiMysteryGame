package sch.ldg.aimasterygame.restTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sch.ldg.aimasterygame.restTest.entity.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Integer> {

}
