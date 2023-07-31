# H8-YoungCha

소프티어부트캠프 2기 프로젝트 팀 **영차 🚙** 입니다.

## 팀원 소개 👨‍💻

|                                          FE                                          |                                         FE                                          |                                          FE                                          |                                           BE                                           |                                            BE                                             |                                             AOS                                              |                                          AOS                                           |
| :----------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/1lsang" width="400px" alt="박일상"/> | <img src="https://avatars.githubusercontent.com/jhyep" width="400px" alt="박지혜"/> | <img src="https://avatars.githubusercontent.com/bae-sh" width="400px" alt="배성현"/> | <img src="https://avatars.githubusercontent.com/csct3434" width="400px" alt="김동철"/> | <img src="https://avatars.githubusercontent.com/dohyeon-han" width="400px" alt="한도현"/> | <img src="https://avatars.githubusercontent.com/hyeonseongkang" width="400px" alt="강현성"/> | <img src="https://avatars.githubusercontent.com/DEVxMOON" width="400px" alt="정혜린"/> |
|                    [박일상](https://github.com/tributetothemoon)                     |                         [박지혜](https://github.com/jhyep)                          |                         [배성현](https://github.com/bae-sh)                          |                         [김동철](https://github.com/csct3434)                          |                         [한도현](https://github.com/dohyeon-han)                          |                         [강현성](https://github.com/hyeonseongkang)                          |                         [정혜린](https://github.com/DEVxMOON)                          |

## 그라운드 룰 📜

### 시간 ⏰

- 수업시간
  - FE : 화 10:30 ~ 12:00 & 수 11:00 ~ 12:00
  - BE : 월 10:30 ~ 12:00 & 수 14:00 ~ 16:00
  - Android : 화 & 목 14:00 ~ 16:00
- 체크인 : 월-금 10:00
- 회의 : 17:00 ~ 18:00
- 스크럼 : 월-금 10:00 ~ 10:30

### 의사결정 방식 ✅

금요일 데모 배포 이전, 목요일 회의 시간을 활용하여 개발 현황 체크 후 데모 QA를 진행합니다. 그 후, 피드백을 반영하여 데모를 배포합니다.

### 프로젝트 관리 방식 📊

Github Project와 Wiki를 활용하고, 노션을 사용하여 간단하게 정리 후 Github Wiki에 업로드합니다.

### 팀 분위기 🌈

- 자연스럽고 편안한 분위기를 조성하며 함께 개발합니다.
- 상호 존중과 배려를 중요시합니다.

### 키워드 🔑

행복하게 열심히 잘 하자, 아프지 말자, 주인의식

### 역할과 책임 👥

#### 전체 팀원

- **서기**

  Notion 템플릿을 사용하여 작성한 내용을 Markdown 형식으로 추출하고 Wiki에 반영합니다. 일정 조율을 위해 돌아가며 회의록을 작성합니다.

  순서 :일상 → 성현 → 현성 → 혜린 → 지혜 → 도현 → 동철

#### 프론트엔드 🖥️

소통 및 코드 리뷰를 서로의 성장을 위해 지속적으로 진행합니다.

#### 백엔드 ⚙️

(팀원의 역할과 책임 내용 추가 필요)

#### 안드로이드 📱

클린코드 작성에 집중합니다.
코드 작성 전 충분히 생각하고 작성합니다.

#### 행동 규범 🤝

우리는 하나입니다. 영차!

## 협업 전략

### Commit Rule 📝

```shell
$ git commit -m "feat: 새로운 기능 구현"
```

|    제목    |                                                설명                                                 |
| :--------: | :-------------------------------------------------------------------------------------------------: |
|   `feat`   |                                          새로운 기능 구현                                           |
|   `fix`    |                                              버그 수정                                              |
|   `docs`   |                                          문서 변경 및 추가                                          |
| `refactor` |                코드 리팩토링 / 파일•폴더이름 변경 / 파일 폴더 제거 / 파일 경로 변경                 |
|   `test`   |                               테스트 코드, 리펙토링 테스트 코드 추가                                |
|  `design`  |                                           UI 디자인 변경                                            |
|  `chore`   | 라이브러리 설치, 환경설정 파일 변경, 빌드 업무 수정, 패키지(package.json...) 수정, 코드 스타일 변경 |

### 브랜치 전략 🌿

이슈를 생성한 후, 템플릿에 맞게 제목 및 내용을 작성하고 해당 이슈에 대한 브랜치를 생성합니다.

#### 브랜치 네이밍 룰

```plain
{브랜치 속성}/{이슈 번호}-{해당 브랜치 명}
```

- **예시**
  - feat/#1-button-component
  - refactor/#2-button-component
  - fix/#3-error

#### 브랜치 속성

|    속성    |                           설명                           |
| :--------: | :------------------------------------------------------: |
|   `feat`   |            새로운 기능을 구현할 때 사용해요.             |
| `refactor` | 기능은 똑같이 작동하지만, 코드를 리팩토링할 때 사용해요. |
|   `fix`    |                에러를 수정할 때 사용해요.                |

- main(배포)
- dev
  - dev/fe
    - feat/#1
      - feat/#1-1-button-component
    - refactor/#12-code-module
  - dev/anrdoid
  - dev/be

### 브랜치 보호 전략 🔒

- main 브랜치는 각 파트(FE, BE, AOS)에서 1명 이상 approve 눌러야 merge를 진행할 수 있습니다.
- dev 브랜치는 해당 파트 팀원이 전부 코드 리뷰 및 approve가 있어야 merge를 진행할 수 있습니다.

### 머지 전략 🔄

- Commit merge

### 회고방식 🚀

주간 회고에서는 진행 상황과 그에 대한 피드백, 협업에 있어서 바라는 점이나 아쉬웠던 점 등을 함께 이야기해봅시다!

## 기획 디자인 링크

[📑 기획서 보러가기](https://www.figma.com/file/1NtR0XBBHbNXK8cE0asX4S/%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%83%81%EC%84%B8%EC%84%A4%EA%B3%84%EC%84%9C?type=design&node-id=172%3A7015&mode=design&t=kr2SIoPz644V6vWb-1)

[🎨 디자인 보러가기](https://www.figma.com/file/aTK27d8JGjSAp8qttQSwgy/Oh,-my-car-set_Handoff?type=design&node-id=8:17111&mode=design&t=Eig1TvDmCVJ4eT5D-1)
