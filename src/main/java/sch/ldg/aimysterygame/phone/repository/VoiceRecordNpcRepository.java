package sch.ldg.aimysterygame.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordNpc;

public interface VoiceRecordNpcRepository extends JpaRepository<VoiceRecordNpc,Integer> {
    VoiceRecordNpc findByNpcIdAndUserId(String npcId, String userId);
}
