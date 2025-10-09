package sch.ldg.aimasterygame.restTest.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimasterygame.restTest.entity.TestEntity;
import sch.ldg.aimasterygame.restTest.repository.TestRepository;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<TestEntity> getAllTests() {
        return testRepository.findAll();
    }
}
