# API 통신 JSON 예시

## 게임 시작
### url: /chat/startGame

```json
{
    "userId": "test",
    "mode": "SETUP"
} 
```

## npc 대화
### url: /chat/talk

```json
{
    "userId": "test",
    "playerInput": "안녕하세요.",
    "npcId": "npc001",
    "mode": "TALK",
    "knownClues": ["clue1", "clue2"]
} 
```

## 정답 맞추기
### url: /chat/checkAnswer

```json
{
    "userId": "test",
    "killerId": "npc001",
    "killerReason": "~~이렇게 했기 때문에 너가 범인이야!",
    "mode": "VERDICT"
} 
```