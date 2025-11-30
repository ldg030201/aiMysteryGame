package sch.ldg.aimysterygame.phone.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.phone.entity.Memo;
import sch.ldg.aimysterygame.phone.repository.MemoRepository;

import java.util.List;

@Service
public class MemoService {
    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public List<Memo> findMemoByUserId(String userId) {
        return memoRepository.findByUserId(userId);
    }
}
