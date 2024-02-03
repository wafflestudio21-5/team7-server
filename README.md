<img src= "https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=1,2,3&animation=fadeIn&height=300&section=header&text=WAFFLE%20CAFE&fontSize=90&fontColor=ffffff"/>

## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [배포 링크](#배포-링크)
3. [기술 스택](#기술-스택)
4. [프로젝트 구조(ERD)](#ERD)
5. [기능 및 화면 소개](#기능-및-화면-소개)
6. [개발 과정](#개발-과정)
7. [팀원 정보](#팀원-정보)

## 프로젝트 소개
NAVER 카페 서비스의 각 카페 내에서 제공되는 기능들을 클론하였습니다.
세미나 시간에 배우는 개념을 포함한 풍부한 구현 요소들로 인해 선정하였으며, 이의 세부적 기능까지 구현하는 것을 목표로 하였습니다.

**✅ 필수 스펙 구현**

- [x] 회원가입, 로그인 및 소셜 로그인 기능
- [x] 유저 계정 페이지
- [x] 글 작성 / 댓글 작성
- [x] 페이지 네이션 : 게시물, 댓글 창 등
- [x] AWS 배포

**☑️ 추가 스펙 구현**

- [x] 즐겨찾기 수를 기준으로 인기 게시판 기능
- [x] 비밀 댓글 기능

## 배포 링크

- 배포 URL : https://cafewaffle.shop/
- 일반 계정
  - 테스트 ID : waffleuser
  - 테스트 PW : password123!
- 관리자 계정
  - 테스트 ID : admin
  - 테스트 PW : adminpassword

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

## ERD

<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/5ff1a26a-940d-4690-8e88-944c5b2c243a" />

## 기능 및 화면 소개
### ⭐ 실제 화면 소개


#### 1.  회원가입 화면

<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/b0b1b2df-d172-4098-878e-65c39bcdbf4c">

#### 2.  로그인 화면

<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/60fcbdd1-c891-45d9-a5d1-150bb7e47c63">

#### 3. 소셜로그인 화면
<img src= "https://github.com/wafflestudio21-5/team7-server/assets/55388254/120506a6-0ec7-45f0-a7fc-a236b3f7f5c8">
<img src= "https://github.com/wafflestudio21-5/team7-server/assets/55388254/e4d1a76c-f740-45fe-8a80-4411aadee7e5">

#### 4.  유저 계정 페이지 화면 
<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/8450c54a-7356-4a8a-87a3-3d319121f635">

#### 5.  게시글 작성 화면
<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/17aa415e-c5eb-423c-9d90-05da083fd1c1">

#### 6. 게시글 리스트 화면
**[기본 보기 방식]**

<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/c47fc1f6-a401-4fee-b70e-a683620f9e4d">

**[보기 방식 변경]**

<img src="https://github.com/wafflestudio21-5/team7-server/assets/55388254/c39f1c7e-e904-43e0-b498-9530be436319">

#### 7. 게시글 수정 화면
<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/c136f7d3-cfa0-48da-8e39-bfd56aa32f6c">

#### 8. 댓글 작성 화면
<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/40836683-c937-4f8c-8085-855504730b44">

#### 9. 비밀 댓글 작성 화면

<img src="https://github.com/wafflestudio21-5/team7-server/assets/55388254/4f0c2d69-79d3-45f8-b8c8-5a1d7bff62ae">
<img src="https://github.com/wafflestudio21-5/team7-server/assets/55388254/3e3dfb85-0b4a-4a2e-953e-f44efb704b56">

#### 10. 대댓글 작성화면

<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/b0c27a13-70c0-400e-95fd-875fc911698d">

#### 11. 검색 화면

**[전체 게시판, 일반 게시판 하단]**

<img src=  "https://github.com/wafflestudio21-5/team7-server/assets/55388254/8b8a0a9a-1164-4ac0-81fb-d65eff42c9c6">

**[카페 상단]**

<img src="https://github.com/wafflestudio21-5/team7-server/assets/55388254/f983042c-4a4f-4708-bef5-5ab953da2499">

**[검색 페이지]**

<img src= "https://github.com/wafflestudio21-5/team7-server/assets/55388254/827c226d-bccb-4977-8d27-1ecc2f28505a">

#### 12. 정렬 화면

**[전체 게시판, 일반 게시판 페이지네이션 정렬]**
<img src="https://github.com/wafflestudio21-5/team7-server/assets/55388254/a397f006-8e8c-46ae-afeb-39a5a73d4230">

**[카테고리별 정렬]**
<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/67800213-b3bf-48b0-a8f3-6c1e03357bcf">
<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/2dd58a71-fa43-442f-9e08-da70ed583bad">
<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/a5187b4f-ae9c-487d-a8c8-83e08629f8eb">

#### 13.인기 게시판 화면

<img src = "https://github.com/wafflestudio21-5/team7-server/assets/55388254/3afb45ed-3382-40ad-bb01-8b082d935c8a">
<details>
<summary> <strong>⭐페이지 별 세부 기능 </strong></summary> 

1. 회원가입, 로그인, 소셜 로그인 페이지
   <br><br />
2. 메인 페이지
- 전체글 보기
3. 사이드바
- 카페 정보
- 나의활동
- 즐겨찾는 게시판
- 즐겨찾기 된 수를 기준으로 인기있는 게시판을 표시 (추가 구현)
- 최근 댓글, 답글
- 카페 탈퇴하기

4. 카페 인기글 게시판
- 정해진 기간 내에서 조회/댓글/좋아요 수를 기준으로 인기있는 게시글 표시
5. 게시판 페이지
- 게시판에 해당하는 게시글 목록 표시
6. 게시글 작성/수정 페이지
- 게시글 작성 및 수정
7. 게시글 페이지
- 게시글 조회
- 댓글, 대댓글, 비밀댓글(추가 구현) 작성 및 삭제
- 게시글 좋아요 및 취소
- 게시글 수정 페이지로 이동
- 게시글 삭제
8. 유저 정보 페이지
- 마이 페이지 : 작성글, 댓글단 글, 좋아요한 글
- 타 유저 페이지 : 작성글, 댓글단 글
9. 검색
- 키워드에 따른 게시글 검색
- 검색 기간 설정, 검색 게시판 설정, 검색 대상 설정, 상세 검색

</details>



## 개발 과정


### 개발 방식

#### **⭐ 커밋 컨벤션**

| 이름        | 목적       |
|-----------|----------|
| feat      | 새로운 기능 추가 |
| fix       | 버그 수정    |
| docs      | 문서 업데이트  |
| style     | 스타일 변경   |
| refactor  | 리팩토링     |
| test      | 테스트 추가   |
| chore     | 유지보수     |

#### **⭐ Git 브랜치 : GitFlow 방식**

| 이름        | 목적        |
|-----------|-----------|
| main      | 최종 배포 브랜치 |
| dev       | 통합 브랜치    |
| feature   | 기능 개발 브랜치 |
| setEntity | 엔티티 통합 브랜치 |
| docs      | 리드미 수정    |

#### **⭐회의 방식(Agile)**

주 1회 금요일 오후 6시 스프린트

| 1주차 (12/26) | 자기소개, 서비스 정하기, 규칙 정하기 |
| --- | --- |
| 2주차 (1/5) | 기본 설정, 도메인 모델, ER 다이어그램 |
| 3주차 (1/12) | 필수 기능 정리 및 분담 |
| 4주차 (1/19) | 엔티티 정리, Api 정리 |
| 5주차 (1/26) | 필수 기능 구현 |
| 6주차 (2/2) | 추가 기능 구현, 프론트 구현 마무리, 베포 |

#### **⭐PR 방식**

- 백엔드: 자신을 제외한 팀원들을 리뷰어로 설정, 4명의 리뷰어가 있어야 merge 가능
- 프론트: 2인이므로 서로 검토 후 PR


## 팀원 정보

<table>  
<tr>  
  <td>Team-07</td> 
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
  <td></td>
</tr>
<tr>
  <td>GitHub</td>
  <td><a href="https://github.com/vitacpark"><img src = "https://avatars.githubusercontent.com/u/55388254?v=4" Width = "100"></a></td>
  <td><a href="https://github.com/hwoo-cho04"><img src = "https://avatars.githubusercontent.com/u/90887713?v=4" width = "100"></a></td>
  <td><a href="https://github.com/Yumin22224"><img src = "https://avatars.githubusercontent.com/u/128684390?v=4" width = "100"></a></td>
  <td></td>
</tr>
</table>
