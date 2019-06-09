# This was modelled as EPCISDocuments using open data provided by FoodSafetyKorea. Open data is 'Barcode-Linked Product Information', and the information provided by this data is described below.
 
> ### PRDLST_REPORT_NO 	  Item Reporting (Report) Number
>
> ### PRMS_DT Reporting 	(Report Date)
>
> ### END_DT			        End Date
>
> ### PRDLST_NM 		      Product Name
>
> ### POG_DAYCNT 		      Expiration date
>
> ### PRDLST_DCNM 		    Food Type
>
> ### BSSH_NM 		        Manufacturer Name
>
> ### INDUTY_NM 		      Industry Name
>
> ### SITE_ADDR 		      Address
>
> ### CLSBIZZ_DT 		      Closing Date
>
> ### BAR_CD 			        Distribution Barcode


### Among the various information, information that they thought would be useful was classified and stored using a transducer.

### The required information [identification code, name, date and location information] corresponds to [distribution bar code, product name, reporting (report date) and address].

## The Working Process...
> 
> ### After reading a document using the DocumentBuilderFactory class, you created the author with the DocumentBuilder class, which was created as an object using the document class. 
>
> ### After creating a new document with the document class and setting up the required nodes and properties, [distribution bar code, product name, reporting (report date), and address] were written as properties for each distribution bar code.
>
> ### Once the document classification is complete, create the DOMSource and FileWriter objects, the parameters of the TransformerFactory class, and convert and create a complete XML document using the .transform method.
>
<hr/>

## Finally, 
### when the data is converted, the user can enter an identification code (Distribution Barcode) to store the transformed data on the server to view the information.

## Codes and other documents
> ### EPCISDocumentGenerator.java --> Converter Class
>
> ### Product.java --> Product abstraction class
>
> ### FoodSafetyKorea1.xml --> [Translated Data](http://210.93.116.66/epcis/GEODES/FoodSafetyKorea1.xml)
>

## How to check 'Barcode-Linked-Product-Information'
>
> ### 1. [Go to Home Page](https://www.foodsafetykorea.go.kr/api/datasetList.do)
>
> ### 2. In the 'Service' category, type 'Barcode linked product information' and search.
