package sch.ldg.aimysterygame.restTest.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.restTest.entity.TestEntity;
import sch.ldg.aimysterygame.restTest.repository.TestRepository;

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
