package sch.ldg.aimysterygame.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sch.ldg.aimysterygame.phone.entity.Clue;

import java.util.List;

public interface ClueRepository extends JpaRepository<Clue, Integer> {
    List<Clue> findByUserId(String userId);
}
