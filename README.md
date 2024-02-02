<img src= "https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=1,2,3&animation=fadeIn&height=300&section=header&text=WAFFLE%20CAFE&fontSize=90&fontColor=ffffff"/>

## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [배포 링크](#배포-링크)
3. [기술 스택](#기술-스택)
4. [프로젝트 구조(ERD)](#프로젝트-구조(ERD))
5. [기능 및 화면 소개](#기능-및-화면-소개)
6. [개발 과정](#개발-과정)
7. [팀원 정보](#팀원-정보)

# 프로젝트 소개
NAVER 카페 서비스의 각 카페 내에서 제공되는 기능들을 클론하였습니다.
세미나 시간에 배우는 개념을 포함한 풍부한 구현 요소들로 인해 선정하였으며, 이의 세부적 기능까지 구현하는 것을 목표로 하였습니다.

**✅ 필수 스펙 구현**

- [x] 회원가입, 로그인 및 소셜 로그인 기능
- [x] ...

**☑️ 추가 스펙 구현**

- [x] 즐겨찾기 수를 기준으로 인기있는 게시판을 표시하는 기능
- [x] 비밀 댓글 기능

## 배포 링크

- 배포 URL : https://cafewaffle.shop/
- 테스트 ID : hong
- 테스트 PW : password123

## 기술 스택

### 🖥️ FrontEnd
<div>
 <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> 
 <img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white"> 
 <img src="https://img.shields.io/badge/styled components-DB7093?style=for-the-badge&logo=styledcomponents&logoColor=white"> 
 <img src="https://img.shields.io/badge/context api-61DAFB?style=for-the-badge&logo=react&logoColor=black"> 
 <img src="https://img.shields.io/badge/react router-CA4245?style=for-the-badge&logo=reactrouter&logoColor=white">
<br>
</div>

### ⚙️ BackEnd
<div>
  <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
  <img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"> 
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
<br>
</div>

### 📤 CI/CD
<div>
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> 
  <img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"> 
  <img src="https://img.shields.io/badge/aws ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> 
<br>
</div>

## 프로젝트 구조(ERD)

<img src = "" />

## 기능 및 화면 소개


- 회원가입 페이지
- 로그인 페이지
- 메인 페이지
    - 전체글 보기
- 사이드바
    - 카페 정보
    - 나의활동
    - 즐겨찾는 게시판
    - 즐겨찾기 된 수를 기준으로 인기있는 게시판을 표시 (추가 구현)
    - 최근 댓글, 답글
    - 카페 탈퇴하기
- 카페 인기글 게시판
    - 정해진 기간 내에서 조회/댓글/좋아요 수를 기준으로 인기있는 게시글 표시
- 게시판 페이지
    - 게시판에 해당하는 게시글 목록 표시
- 게시글 작성/수정 페이지
    - 게시글 작성 및 수정
- 게시글 페이지
    - 게시글 조회
    - 댓글, 대댓글, 비밀댓글(추가 구현) 작성 및 삭제
    - 게시글 좋아요 및 취소
    - 게시글 수정 페이지로 이동
    - 게시글 삭제
- 마이페이지
    - 작성글, 작성댓글, 댓글단 글, 좋아요한 글
- 타 유저 페이지
    - 작성글, 댓글단 글
- 검색
    - 키워드에 따른 게시글 검색
    - 검색 기간 설정, 검색 게시판 설정, 검색 대상 설정, 상세 검색

필수기능

회원가입 / 로그인 / 소셜로그인 / 유저 계정 페이지 / 글 작성 / 댓글 작성 / 페이지네이션

추가기능

인기게시판 / 비밀 댓글

- 캡쳐 넣을 화면

1 회원가입 화면

2 로그인 화면

3 소셜로그인 화면

4 유저 계정 페이지 화면 

5 게시글 작성 화면

6 게시글 리스트 화면

7 게시글 수정 화면

7 댓글 작성 화면

8 비밀 댓글 작성 화면

9 대댓글 작성화면

10 검색 화면

11 정렬 화면

12 인기 게시판 화면

## 개발 과정


### 개발 방식(GitFlow)

- 커밋 컨벤션

| feat | 새로운 기능 추가 |
| --- | --- |
| fix | 버그 수정 |
| docs | 문서 업데이트 |
| style | 스타일 변경 |
| refactor | 리팩토링 |
| test | 테스트 추가 |
| chore | 유지보수 |
- Git 브랜치
    - main
    - dev : 통합 브랜치
    - feature: 기능 개발 브렌치
    - setEntity

### 회의 방식(Agile)

|  |  |
| --- | --- |
|  |  |
|  |  |

주 1회 금요일 오후 6시

| 1주차 (12/26) | 자기소개, 서비스 정하기, 규칙 정하기 |
| --- | --- |
| 2주차 (1/5) | 기본 설정, 도메인 모델, ER 다이어그램 |
| 3주차 (1/12) | 필수 기능 정리 및 분담 |
| 4주차 (1/19) | 엔티티 정리, Api 정리 |
| 5주차 (1/26) | 필수 기능 구현 |
| 6주차 (2/2) | 추가 기능 구현, 프론트 구현 마무리, 베포 |

### PR 방식

- 백엔드: 자신을 제외한 팀원들을 리뷰어로 설정, 4명의 리뷰어가 있어야 merge 가능
- 프론트:

## 문제 발생과 극복 과정

## 팀원 정보

Team 07
<table>  
<tr>  
  <td></td> 
  <td>황두현(Leader)</td> 
  <td>최영주</td> <td>전수빈</td> 
  <td>정윤재</td>
</tr> 
<tr>
  <td>GitHub</td>
  <td><a href="https://github.com/DoohyunHwang97"><img src="https://avatars.githubusercontent.com/u/76721027?v=4" width="100"></a></td> 
  <td><a href="https://github.com/YJ0513"><img src="https://avatars.githubusercontent.com/u/135787320?v=4" width="100"></a></td> 
  <td><a href="https://github.com/soobin-jeon"><img src="https://avatars.githubusercontent.com/u/142797788?v=4" width="100"></a></td> 
  <td><a href="https://github.com/darwinj07"><img src="https://avatars.githubusercontent.com/u/54761229?v=4" width="100"></a></td> 
</tr> 
<tr>
  <td></td>
  <td>박찬영</td>
  <td>조현우</td>
  <td>허유민</td>
</tr>
<tr>
  <td>GitHub</td>
  <td><a href="https://github.com/vitacpark"><img src = "https://avatars.githubusercontent.com/u/55388254?v=4" Width = "100"></a></td>
  <td><a href="https://github.com/hwoo-cho04"><img src = "https://avatars.githubusercontent.com/u/90887713?v=4" width = "100"></a></td>
  <td><a href="https://github.com/Yumin22224"><img src = "https://avatars.githubusercontent.com/u/128684390?v=4" width = "100"></a></td>
</tr>
</table>



황두현

---

최영주(YJ0513)

---

![KakaoTalk_20240113_001559970.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/f54ad8d9-5e85-4a03-b287-fc7503d9267a/987f7f4e-0375-4582-9ea0-f440ec338684/KakaoTalk_20240113_001559970.jpg)

전수빈(soobin-jeon)

---

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f54ad8d9-5e85-4a03-b287-fc7503d9267a/89d5fdb4-16e4-4340-a8de-ed2baaba475a/Untitled.jpeg)

정윤재

---

박찬영(vitacpark)

---

![박찬영_0003_브라운_반명함.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/f54ad8d9-5e85-4a03-b287-fc7503d9267a/4544a9aa-c151-4c90-8fb1-b9c9216f033c/%EB%B0%95%EC%B0%AC%EC%98%81_0003_%EB%B8%8C%EB%9D%BC%EC%9A%B4_%EB%B0%98%EB%AA%85%ED%95%A8.jpg)

조현우

---

허유민

---
