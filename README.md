# 로아랑 (Loarang)

**개발 기간**: 2022년 3월 7일 ~
<br>
**사용 언어 및 라이브러리**: Kotlin, Compose, Room, Firebase Realtime Database, Hilt

![icon-playstore_Large](https://github.com/user-attachments/assets/a4a62b7c-a5a6-490d-bfeb-2821716133d7)

[https://github.com/Sangyoon98/Loarang](https://github.com/Sangyoon98/Loarang)
<br>
[https://play.google.com/store/apps/details?id=com.cookandroid.loarang](https://play.google.com/store/apps/details?id=com.cookandroid.loarang)

# 💡Topic

- **RPG 게임 ‘로스트아크’의 콘텐츠 관리 어플리케이션**
- 강남대학교 공과대학 소프트웨어응용학부 졸업작품전시회에서 **최수우상 수상**

# 📝Summary

RPG 게임 ‘로스트아크’를 즐겨하면서 콘텐츠 진행 정보를 확인하기 불편하다는 것이 확인되었습니다. 게임 커뮤니티에서도 콘텐츠 진행 정보를 엑셀 파일로 저장해서 관리한다는 정보를 듣고 어플리케이션을 제작하게 됐습니다. 본인의 캐릭터와 캐릭터별 콘텐츠 진행 정보를 관리할 수 있고 게임 내 필요한 기능들이 더 제공되는 어플리케이션입니다.

# ⭐️Key Function

- 캐릭터 추가 삭제 기능으로 캐릭터 정보 리스트 제공
- 추가한 캐릭터가 자동으로 연계되어 ‘주간숙제’탭에 캐릭터 콘텐츠 진행상황 표시
- 게임 내 고정 컨텐츠 정보를 ‘일정표’탭에 제공
- ‘정보실’탭에서 게임에서 가장 많이 계산되는 경매 손익 계산, 거래 수수료 계산을 자동으로 계산해주는 계산기 기능 제공
- 공지 사항과 패치 내역을 확인하고 개발자에게 메일로 문의할 수 있는 기능 제공

# 🛠️Tech Stack

**v1.1 ~ v1.2**
`Java`, `Jsoup`, `Firebase`, `Glide`, `RXJava`, `SQLite`

**v1.3**
`Kotlin`, `Firebase Realtime Database`, `Coroutine`, `Room`

**v1.4**
`Kotlin`, `Compose`, `Firebase Realtime Database`, `Coroutine`, `Room`, `Hilt`

# ⚙️Architecture

**v1.1 ~ v1.2**
`MVC`

**v1.3**
`MVVM`

**v1.4**
`MVVM`

# ✋🏻Part

- 안드로이드 앱 전체 개발 및 총괄

# 🤔Learned

**v1.1 ~ v1.2**
- `Recycler View`를 사용해보면서 재사용성이 좋은 Recycler View의 장점을 알게 됐고 `Adapter`의 데이터가 변경될 경우 구현해야 하는 코드에 대해 알게 됐음.
- `Jsoup`을 사용해 **크롤링**을 하고 `RXJava`를 사용한 **비동기 처리**를 해보면서 **비동기 프로그래밍의 사용법**에 대해 알게 됐음.
- `SQLite`를 사용해 캐릭터 정보를 저장하면서 DB관리의 방법을 알게 됐음.
- Firebase Realtime Database를 사용해 Firebase 연동법과 Recycler View의 리스트들을 실시간으로 관리하는 방법을 알게 됐음.

**v1.3**
- `Java` 코드를 `Kotlin`으로 마이그레이션 하며 두 언어의 장단점과 차이점을 알게 됐음.
- `Room`을 사용해 캐릭터 정보를 저장하면서 DB관리를 기존보다 더욱 직관적이고 수월하게 하는 방법을 알게 됐음.
- 비동기 처리를 `RXJava`에서 `Coroutine`으로 변경하면서 `Coroutine`의 러닝커브가 낮고 더 낮은 메모리를 사용하는 것을 알게 됐음.
- `MVC`에서 `MVVM`으로 아키텍처 구조를 변경하면서 `ViewModel`의 역할과 구성을 알게 됐음.

**v1.4**
- `XML`을 `Compose`로 변경하면서 `Compose`의 사용법과 구성 방법을 배우게 됐음.
- `Hilt`를 적용해보며 의존성 주입의 필요성을 알게 됐음.

# 📸ScreenShot

로스트아크 게임 정보 제공 어플리케이션

------

# Loarang v1.4
- XML -> Compose 변환
- version catalog 적용

![Image](https://github.com/user-attachments/assets/7110eabb-db08-48b2-b2c5-891af9573a44) |![Image](https://github.com/user-attachments/assets/148fbf35-bf98-4a37-97f3-4f068d5c06fa)
--- | --- | 
![Image](https://github.com/user-attachments/assets/4c2543b7-d099-4825-81fa-579772820a93) |![Image](https://github.com/user-attachments/assets/13c28f25-ee67-426b-9c06-b95c0f016d2f)
![Image](https://github.com/user-attachments/assets/a86c53ba-2542-4f71-9875-c1adaaecd01c) |![Image](https://github.com/user-attachments/assets/fb2f5228-1c07-4cb3-9520-dc279c89e675)
![Image](https://github.com/user-attachments/assets/188dad45-9ae2-40b5-9095-18ac3bc034ea) |![Image](https://github.com/user-attachments/assets/6428e3e4-f5e6-4da6-b411-5a37fae065bd)
![Image](https://github.com/user-attachments/assets/dd57c9b4-c4c1-4feb-97e4-8b4b71a2efaa) |![Image](https://github.com/user-attachments/assets/c0f213bd-4f60-47f4-80ff-c416868ecd64)
![Image](https://github.com/user-attachments/assets/474e3899-e895-444a-9e2e-51b2c31d2855) |![Image](https://github.com/user-attachments/assets/cb90abe0-f4e4-4179-b0f1-e9184f886940)

------

# Loarang v1.3
- 다크 모드 지원
- java -> kotlin 100% 변환
- Legacy 코드 제거 및 개선
- Proguard 적용
- Android 15 지원

![Screenshot_20241226_030041](https://github.com/user-attachments/assets/9f08a5c0-95bf-4fb7-9fee-f475685c1a78) |![Screenshot_20241226_030059](https://github.com/user-attachments/assets/5a1654d8-22af-4b13-a027-30d56c2b3b10)
--- | --- | 
![Screenshot_20241226_030105](https://github.com/user-attachments/assets/6a647b62-06f1-4613-83ac-824e73ef9a82) |![Screenshot_20241226_030140](https://github.com/user-attachments/assets/fa6778ec-bf71-4870-b979-f52c3372c715)
![Screenshot_20241226_030149](https://github.com/user-attachments/assets/46705446-079d-4217-92a2-923573b36786) |![Screenshot_20241226_030315](https://github.com/user-attachments/assets/3858d157-e6c8-4df2-927a-f3e39c0cd9bd)
![Screenshot_20241226_030323](https://github.com/user-attachments/assets/fd33c868-bfac-480f-b59b-64b6fab09967) |![Screenshot_20241226_030333](https://github.com/user-attachments/assets/514dcc49-6f0e-44aa-8434-92e69696d0f2)
![Screenshot_20241226_030337](https://github.com/user-attachments/assets/bb26daf6-7234-4c78-a8a6-50eac9a9290a) |![Screenshot_20241226_030346](https://github.com/user-attachments/assets/743ed9da-be00-4e14-aeae-9ce8c5da68f8)
![Screenshot_20241226_030352](https://github.com/user-attachments/assets/7b58050c-c6f4-46fd-a9a9-69472f010dbb) |![Screenshot_20241226_030411](https://github.com/user-attachments/assets/bb56c4e5-182c-4315-91b5-71a41d6e2743)

------

# Loarang v1.1 ~ v1.2

![슬라이드1](https://user-images.githubusercontent.com/28301831/224558462-fe2e32a8-2b56-488b-a885-253cb3714000.PNG)
![슬라이드2](https://user-images.githubusercontent.com/28301831/224558515-25fa119d-b513-4fab-b430-070c38d78b24.PNG)
![슬라이드3](https://user-images.githubusercontent.com/28301831/224558519-811399d3-798f-46e2-97fe-e38fee58a892.PNG)
![슬라이드4](https://user-images.githubusercontent.com/28301831/224558521-dc1542a1-eed1-4854-a49d-16697a66f537.PNG)
![슬라이드5](https://user-images.githubusercontent.com/28301831/224558524-e9943312-e19c-452e-ba50-70e9facc60cb.PNG)
![슬라이드6](https://user-images.githubusercontent.com/28301831/224558527-a7ba7f27-afaa-4389-bfff-92e2ae38cd30.PNG)
![슬라이드7](https://user-images.githubusercontent.com/28301831/224558528-bb201151-ee69-4527-8740-dcc26973b5ff.PNG)
![슬라이드8](https://user-images.githubusercontent.com/28301831/224558531-8906f443-f0c8-464f-99dd-c847fb052ad2.PNG)














