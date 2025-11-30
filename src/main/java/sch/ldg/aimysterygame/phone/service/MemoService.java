package sch.ldg.aimysterygame.phone.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.ai.service.GameStateStoreService;
import sch.ldg.aimysterygame.phone.entity.Memo;
import sch.ldg.aimysterygame.phone.repository.MemoRepository;
import sch.ldg.aimysterygame.unityAPI.dto.gameData.RelationshipDTO;
import sch.ldg.aimysterygame.unityAPI.dto.gameData.SuspectDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemoService {
    private final MemoRepository memoRepository;
    private final GameStateStoreService gameStateStoreService;

    public MemoService(MemoRepository memoRepository, GameStateStoreService gameStateStoreService) {
        this.memoRepository = memoRepository;
        this.gameStateStoreService = gameStateStoreService;
    }

    public List<Memo> findMemoByUserId(String userId) {
        return memoRepository.findByUserId(userId);
    }

    public void saveNpcMemo(String userId, List<SuspectDTO> suspects) {
        for (SuspectDTO suspect : suspects) {
            StringBuilder sb = new StringBuilder();
            //정보
            sb.append("※이름: ").append(suspect.getName()).append("\n");
            sb.append("※나이: ").append(suspect.getAge()).append("\n");
            sb.append("※직업: ").append(suspect.getJob()).append("\n");

            //관계
            sb.append("※관계:\n");
            for (RelationshipDTO relationship : suspect.getRelationships()) {
                String npcName = gameStateStoreService.getNpcNameByNpcId(userId, relationship.getWith());
                String memo = """
%s(%s):
%s

                """.formatted(npcName, relationship.getType(), relationship.getNote());
                sb.append(memo);
            }

            //알리바이
            sb.append("※알리바이:\n");
            sb.append(suspect.getAlibi().getSummary()).append("\n");
            sb.append(suspect.getAlibi().getDetail());

            Memo memo = Memo.builder()
                    .userId(userId)
                    .title(suspect.getName() + "의 정보")
                    .memo(sb.toString())
                    .updateDateTime(LocalDateTime.now())
                    .build();

            memoRepository.save(memo);
        }
    }
}
