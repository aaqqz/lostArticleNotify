# 분신물 알림 서비스

* problem : 대중교통 이용 중 분실한 제품을 찾기 위해 경찰청, 관공서 사이트를 오랜 시간 검색해야 한다.

* idea : 사용자가 분실물을 등록 -> 분실물정보 API의 데이터와 유사성이 있을 시 알림 메일 발송

---

서울시 대중교통 분실물 습득물 정보 API 를 이용하여,

사용자가 등록한 분실물 정보와 분실물정보API의 Data 유사성이 있다면,
 
사용자의 메일로 분실물의 정보(분실물명, 상태, 습득장소 등)를 발송한다.

계층형 게시판 커뮤니티를 이용하여 사용자 간 분실물정보에 대한 정보교환을 활성화하였다

---
# 기술 스택

* Java -version 11
* Gradle
* Spring Boot (API Server)
* Spring Security
* Spring Scheduler
* H2 Database
* JPA & QueryDSL
* JWT
* JUnit
* Google SMTP

---
* 모든 기능에 대한 테스트코드 작성
---
