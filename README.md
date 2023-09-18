# 영화 예매 서비스(MovieTicketing)

## 사용한 기능

### Tool

1. IntelliJ

### database

1. MySql
2. h2database

### plugins

1. Spring
2. Spring quartz
3. jdbc
4. jpa
5. redis
6. security
7. web
8. jwt
9. Lombok
10. elastic search
11. swagger

## 제공하는 기능(API)

### 유저관련 기능

- 회원가입
    - 카카오, 네이버 Open api를 이용, domain상 가입도 가능


- 로그인
    - jwt token을 통해 token을 발급


- 내 정보
    - 내가 예매한 영화를 확인 가능


- 충전 기능
    - 좌석을 예매할 금액 충전, 충전 기록은 별도로 저장


- 영화 예매
    - 좌석은 임의, 임의의 가격

### 관리자관련 기능

- 유저 차단
    - 관리자 계정으로 user의 email을 통해 해당 계정 차단


- 모든 유저 목록 확인

### 영화관련 기능

- 영화 정보
    - 영화진흥위원회 open api를 통해서 영화 정보 가져오기


- 영화 정보를 월마다 quartz-sceduler를 를 통해서 추가, 수정


- 상영 중인 영화는 주마다 quartz-scheduler를 통해서 추가, 삭제
  - 영화 정보상 개봉일 기준 1달이 상영 중인 영화의 기준 


- 영화 검색
    - elastic search를 이용해서 영화 제목을 검색


- 영화 상세정보
    - 검색을 통해서 검색한 영화의 movieCd를 영화진흥위원회의 open api를 통해 정보 가져오기
    - redis 서버에 존재한다면 redis에서 바로 정보를 가져온다 


- 상영중인 영화의 상세정보는 redis서버에 저장
    - 추가로 open api를 통해 데이터를 가져오지 않고 자체 저장

## ERD

![movie_ticketing](https://github.com/SeungHyunLee054/MovieTicketing/assets/103303970/6ab0e30a-b957-48ff-975a-6115fd7e3138)
