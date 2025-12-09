package sch.ldg.aimysterygame.phone.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.phone.entity.Clue;
import sch.ldg.aimysterygame.phone.repository.ClueRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClueService {
    private final ClueRepository clueRepository;

    public ClueService(ClueRepository clueRepository) {
        this.clueRepository = clueRepository;
    }

    public void saveClue(Clue clue) {
        clue.setCreateDateTime(LocalDateTime.now());
        clueRepository.save(clue);
    }

    public List<Clue> findClueByUserId(String userId) {
        return clueRepository.findByUserId(userId);
    }
}
