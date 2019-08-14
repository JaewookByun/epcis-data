##EPCIS : OPEN DATA PROJECT
***
>####이것은 소고기의 생산 및 도축에 관련한 데이터를 EPCISDocument를 참조하여 모델링한 프로젝트이다.
>####공개적인 소고기의 데이터를 가공하여 쓸모있는 이벤트 데이터를 추출하고, 이를 xml화 하여 저장하였다.
>>####__오픈 데이터 제공 : FoodSafetyKorea (link - www.foodsafetykorea.com)__
>>####__프로젝트 툴(Language) : java__
***
##Content
해당 데이터는 다음과 같은 내용을 포함하고 있다.
장소 : ~
시간 : ~
위치 : ~
etc

##Working Process
먼저, 공개된 데이터의 요소를 파악한다.
이후 쓸모있는 데이터의 요소들을 적정 business 항목들에 대입하여 xml로 저장한다.
외부 메소드
 - Domparser(DocumentBuilder, DocumentBuilderFactory) : source로부터 파일을 읽어와 다룰 수 있게 해줌
 - Element : 각 데이터의 요소들을 다룰 수 있게 해줌
 - Transformer(Transformer, TransformerFactory) : 설정해준 값으로 destination에 새로운 문서를 만들어줌

##제공 데이터의 요소들
ENTTY_IDNTFC_NO --> 
SLAU_PLC_NM -->
SLAU_YMD -->
ADDR -->
SNTT_PRSEC_PASS_ENNC -->

