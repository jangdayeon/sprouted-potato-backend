# SPROUTED POTATO
도서 익명 리뷰 플랫폼, SPROUTED POTATO


## [SPROUTED POTATO 바로가기](https://cloudpotato-506250559.ap-northest-2.amazonaws.com)

## 1. 프로젝트 소개 🗒

<img width="637" alt="sp" src="https://github.com/choi-jimin/sprouted-potato-backend/assets/109493189/2781f6c0-be7a-42d2-9236-fe5453c4be3a">

> &nbsp;
>
> 이모지를 통해 사용자가 리뷰를 표정으로 표현할 수 있는 기능을 제공합니다.
> 
> 감정 분석 api를 사용한 도서 리뷰 감정 분석 기능을 제공합니다.
> 
> 그래프를 통해 사용자들의 리뷰 현황을 한 눈에 확인할 수 있는 기능을 제공합니다.
> 
> 사용자들이 어떤 이모지를 통해 어떤 책을 표현했는지 알려주는 기능을 제공합니다.
>
> &nbsp;


## 2. 프로젝트 기간 : _2023-11_

## 3. 팀원 소개  
-   FE & BE - 장다연 :  메인 페이지 / 책 상세 정보 페이지 - 책 상세정보
-   FE & BE - 최지민 :  검색 페이지 / 책 상세 정보 페이지 - 그래프를 통한 통계 표시, 댓글 등록ˑ수정ˑ삭제

## [4. 프로젝트 시연영상 📌](https://www.youtube.com/watch?v=Nw2AZFwl2dA)

## 5. 클라우드 아키텍처 🛠
<img width="494" alt="_1" src="https://github.com/project-fourtato/Backend_v3/assets/84323684/665af649-9ad2-4a50-932a-2d445f30f771">

## 6. 다이어그램 🪛
<img width="550" alt="_2" src="https://github.com/project-fourtato/Backend_v3/assets/84323684/0dd3afa9-27c0-47f1-83c4-ff6531aea189">


## 7. 페이지별 기능
- 메인 화면
  - 사용자가 선택한 이모지가 많
은 순으로 책을 보여줌
  - AI 감정 분석 결과가 긍정이 많
은 순
  - 😄,🥹,😍 이모지가 많은 순
  - 리뷰가 많은 순
  - 베스트셀러
 
- 검색 화면
  - 사용자가 검색한 책 이름에 관한 검색 결과를 보여줌
  
- 리뷰 상세 화면
  - 책 상세 정보
  - 이모지 통계
  - AI 감정 분석 통계
  - 리뷰 작성, 수정, 삭제
  - 다른 사용자들의 리뷰


## [8. API 명세서 📃](https://transparent-sunfish-f71.notion.site/f752c27127f941638e348dbb91ff8c06?v=16064a5f311a4d72b74c36052e0c4724&pvs=4)



## 9. UI

![image](https://github.com/project-fourtato/Backend_v3/assets/84323684/410e3ef3-5463-49eb-87ac-b073d36ccf5e)


## 10. 트러블 슈팅 🎃
### BACK

**리플리케이션 구현 중 MySQL 2003 에러**

- bind-address를 외부에서 접근하지 못하도록 제한하였기에 slave DB가 접근하지 못하여 2003 에러가 발생함
- 이는 master DB의 bind-address를 주석처리를 해서 해결함 
   
**MySQL 리플리케이션 UUID 관련 에러**

- 다중 서버를 사용하기 위해 A 서버의 빌드 내용을 그대로 B 서버에 복제 하였더니 UUID 값이 같아져 UUID 에러가 발생함
- 이는 UUID를 변경을 해줘야하는데, mysql server의 UUID는 auto.cnf에 정의되어 있어 해당 파일을 지운 후, mysql을 재시작하여 해결함

**로드밸런서 접속 오류**

- 동일하게 규칙을 명시한 서로 다른 보안 그룹으로 각각 인스턴스를 추가하였더니 접속이 안되는 오류가 발생함
- 이는 같은 보안 그룹에 두 인스턴스를 추가하여 해결함

### FRONT

**캐러셀 구현의 어려움**
