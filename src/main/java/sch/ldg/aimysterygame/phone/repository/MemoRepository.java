package sch.ldg.aimysterygame.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sch.ldg.aimysterygame.phone.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Integer> {
    List<Memo> findByUserId(String userId);
}
