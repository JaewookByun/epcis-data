## EPCIS : OPEN DATA PROJECT

> #### This is a project that models data on the production and slaughter of beef by reference to the EPCISDocument.
>
> #### Data from public beef was processed to extract useful event data and stored in xml.
>
>> #### __Data Provision : FoodSafetyKorea (link - www.foodsafetykorea.com)__
>
>> #### __Project Tool(Language) : java__

## Content
> This data includes the following :
>
>> epc : ENTTY_IDNTFC_NO
>
>> company : SLAU_PLC_NM
>
>> eventTime : SLAU_YMD
>
>> companyAddress : ADDR
>
>> passInspection : SNTT_PRSEC_PASS_ENNC

## Working Process
먼저, 공개된 데이터의 요소를 파악한다.
이후 쓸모있는 데이터의 요소들을 적정 business 항목들에 대입하여 xml로 저장한다.
외부 메소드
 - Domparser(DocumentBuilder, DocumentBuilderFactory) : source로부터 파일을 읽어와 다룰 수 있게 해줌
 - Element : 각 데이터의 요소들을 다룰 수 있게 해줌
 - Transformer(Transformer, TransformerFactory) : 설정해준 값으로 destination에 새로운 문서를 만들어줌
