# 영화 예매 서비스(MovieTicketing)

## 사용한 기능
### Tool
1. IntelliJ
### database
1. MySql
2. h2database
### plugins
1. Spring boot
2. Spring batch
3. Spring quartz
4. jdbc
5. jpa
6. redis
7. Lombok
8. elastic search
9. swagger

## 제공하는 기능(API)
### 유저관련 기능
1. 회원가입 기능(카카오 Open api를 이용)
2. 충전 기능
3. 영화 상세정보 및 상영 영화 목록
4. 영화 검색
5. 영화 예매(좌석은 임의)

### 영화관련 기능
1. 영화진흥위원회 open api를 이용해서 영화 정보를 가져오기
2. 영화 정보를 월마다 batch를 이용해서 자동으로 가져오기
3. 영화의 상세정보는 영화진흥위원회의 open api를 통해서 movieCd를 통해 가져오기
4. 상영중인 영화의 상세정보는 redis서버에 저장
