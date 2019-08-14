# EPCIS : OPEN DATA PROJECT

> ### This is a project that models data on the production and slaughter of beef by reference to the EPCISDocument.
>
> ### Data from public beef was processed to extract useful event data and stored in xml.
>
>> ### _Data Provision : FoodSafetyKorea_ [Link](http://www.foodsafetykorea.go.kr)
>
>> ### _Project Tool(Language) : java_

# Content
> This data includes the following :
>
>> epc : ENTTY_IDNTFC_NO
>
>> eventTime : SLAU_YMD
>
>> company : SLAU_PLC_NM
>
>> companyAddress : ADDR
>
>> passInspection : SNTT_PRSEC_PASS_ENNC

# Working Process
### First, the elements of the published data are identified.
### Subsequently, elements of useful data are replaced with appropriate business items and stored in xml.
### External method used
#### - Domparser(DocumentBuilder, DocumentBuilderFactory) : Enables you to read and process files from the source
#### - Element : Allows you to handle the elements of each data
#### - Transformer(Transformer, TransformerFactory) : Creates a new document in destination with the value you set

* * *
### The data stored in our HomePage [Link](http://210.93.116.66/epcis/GEODES/)
