# JPA쇼핑몰사이트
자바 ORM 표준 프로그래밍 - 기본편/실전편 실습

## 프로젝트 개요
회원, 상품, 주문 도메인 간의 프로세스를 고려해 Spring MVC패턴 기반 쇼핑몰 웹 서비스를 개발하며 Spring boot+JPA를 학습하였습니다.


## 설계
### 도메인 설계

<img width="1000" alt="도메인설계" src="https://user-images.githubusercontent.com/76679463/136525868-a06d344a-54b9-4c18-afaa-c99ca1170c55.PNG">


## Preview
### Main Page
<img width="1000" alt="mainUIv3" src="https://user-images.githubusercontent.com/76679463/136526066-e0d0d39b-75cb-47eb-983d-521c9d62f7d1.PNG">

#### (ADMIN/ MEMBER) 권한별로 보이는 기능을 구별

* ADMIN (@jpashop.co.kr 도메인은 관리자로 구분)
<img width="1000" alt="admin page" src="https://user-images.githubusercontent.com/76679463/136529102-f40f52a4-22d0-4bd5-aab0-187bdb65bb39.PNG">

* MEMBER
<img width="1000" alt="member page" src="https://user-images.githubusercontent.com/76679463/136528523-bf61b2a2-1d14-470f-b529-4d9e2c8b6216.PNG">


## 개발 기능
* 회원 기능
  * 회원 목록
* 상품 기능
  * 상품 등록
  * 상품 목록
* 주문 기능
  * 상품 주문
  * 주문 내역
* 로그인 기능
  * 로그인 / 로그아웃
  * 회원가입

## 개발 환경
* Gradle
* Spring Boot
* jAVA jdk 11

## 의존 라이브러리
* data-jpa
* thymeleaf
* spring-web
* lombok
* junit
* devtools
* h2 database
* mysql
