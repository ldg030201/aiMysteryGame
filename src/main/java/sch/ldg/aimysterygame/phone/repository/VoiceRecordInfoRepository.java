package sch.ldg.aimysterygame.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordInfo;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordNpc;

import java.util.List;

public interface VoiceRecordInfoRepository extends JpaRepository<VoiceRecordInfo, Integer> {
    List<VoiceRecordInfo> findByVrnInfo(VoiceRecordNpc npc);
}
