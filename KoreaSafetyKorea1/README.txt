식품안전나라(FoodsafetyKorea)에서 제공하는 오픈데이터를 이용하여 EPCISDocument로 모델링 하였다.

오픈데이터는 '바코드연계제품정보'이며 해당 데이터에서 제공하는 정보는 아래에 명시되어있다.

=================================================

PRDLST_REPORT_NO	품목보고(신고)번호

PRMS_DT			보고(신고일)

END_DT			생산중단일

PRDLST_NM		제품명

POG_DAYCNT		유통기한

PRDLST_DCNM		식품 유형

BSSH_NM			제조사명

INDUTY_NM		업종

SITE_ADDR		주소

CLSBIZ_DT		폐업일자

BAR_CD			유통바코드

=================================================

여러 정보중에서 유용하다고 생각하는 정보들을 분류하여 변환기를 이용하여 저장하였다.

분류한 정보는 [식별코드, 이름, 날짜, 위치정보]에 상응하는 [유통바코드, 제품명, 보고(신고일), 주소]이다.


----------------- 작업 프로세스 -----------------

DocumentBuilderFactory 클래스로 문서를 읽고 DocumentBuilder 클래스로 빌더를 생성하여

Document 클래스로 문서를 객체로 파싱하였다. 그리고 새로만들 문서를 Document 클래스로 객체화 하고

필요한 노드와 속성을 설정한 후 분류한 정보들을 각 유통바코드의 속성으로 생성하였다.

문서파싱을 완료하였다면 TransformerFactory 클래스의 매개변수인 DOMSource와 FileWriter의 객체를 생성한 후

.transform 메소드를 통하여 완전한 XML문서로 변환 및 생성한다.

-------------------------------------------------


그렇게 해서 최종적으로 데이터가 변환되었다면, 사용자는 변환된 데이터를 서버에 저장한 후

식별코드(유통바코드)를 입력하여 해당하는 정보를 볼 수있다.


EPCISDocumentGenerator.java  --> 변환기 클래스

Product.java  --> 제품 추상화 클래스

FoodSafetyKorea1.xml  --> 변환된 문서


'바코드연계제품정보' 확인방법

1. https://www.foodsafetykorea.go.kr/api/datasetList.do  접속

2. '서비스' 카테고리에서 '바코드연계제품정보' 입력 후 검색.
