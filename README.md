# API 통신 JSON 예시

## 게임 시작
### url: <a href="/chat/startGame">/chat/startGame</a>

```json
{
    "userId": "test",
    "mode": "SETUP",
    "map": [
      {
        "id": "roomLobby",
        "name": "로비",
        "description": "방 내부 설명",
        "connection": [
          { "id": "room_TypeA1" },
          { "id": "room_TypeB3" }
        ]
      }
    ],
    "clues": [
      {
        "id": "clueLog2157",
        "name": "단서명",
        "description": "단서 설명",
        "locationRoomId": "단서 위치 room id값"
      }
    ]
} 
```

## npc 대화
### url: <a href="/chat/talk">/chat/talk</a>

```json
{
    "userId": "test",
    "playerInput": "안녕하세요.",
    "npcId": "npc001",
    "mode": "TALK",
    "knownClues": ["clue1", "clue2"]
} 
```

## 단서 획득
### url: <a herf="/gallery/get-clue">/gallery/get-clue</a>

```json
{
    "userId": "사용자ID",
    "clueImgId": "imgId",
    "clueName": "단서제목"
}
```

## 정답 맞추기
### url: <a href="/chat/checkAnswer">/chat/checkAnswer</a>

```json
{
    "userId": "test",
    "killerId": "npc001",
    "killerReason": "~~이렇게 했기 때문에 너가 범인이야!",
    "mode": "VERDICT"
} 
```