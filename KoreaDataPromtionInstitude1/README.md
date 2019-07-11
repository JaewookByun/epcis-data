# Outline

It was modelled as EPCISDocuments using open data provided by the Korea Data Promotion Institute.

The data is the history of the airplane.

# Information provided & explantion

eventTime : Time the event occurred

epcList

> epc : Flight number

bizStep : the state of an aircraft, it is as follows.

>  urn:epcglobal:cbv:bizstep:processing : processing
>
> urn:epcglobal:cbv:bizstep:none : undetermined
>
> urn:epcglobal:cbv:bizstep:departing : departed
>
> urn:epcglobal:cbv:bizstep:gate_change : gate change
>
> urn:epcglobal:cbv:bizstep:diverted : diverted
>
> urn:epcglobal:cbv:bizstep:boarding : boarding
>
> urn:epcglobal:cbv:bizstep:preparing board : preparing board
>
> urn:epcglobal:cbv:bizstep:check_in_open : check_in_open
>
> urn:epcglobal:cbv:bizstep:due_to_close : due_to_close 
>
> urn:epcglobal:cbv:bizstep:check_in_close : check_in_close
>
> urn:epcglobal:cbv:bizstep:arriving : arrived
>
> urn:epcglobal:cbv:bizstep:stand_by : stand by
>
> urn:epcglobal:cbv:bizstep:go_to_gate : enter to gate
>
> urn:epcglobal:cbv:bizstep:canceled : canceled
>
> urn:epcglobal:cbv:bizstep:delay : delay
>
> urn:epcglobal:cbv:bizstep:gate_close : gate close
>
> urn:epcglobal:cbv:bizstep:hold : check-in interruption

bizLocation : Departure Airport

extension

> sourceList
>
> > source : departure airport
>
> destinationList
>
> > destination : destination

gate : gate number

isInternational : international or not

isFerry : ferry or not

company : airline name

# Working process

Read the CSV file and save the required information.
Use the JDOM library to create information in an XML file.

# Code & File

EPCISConverter.java : convert CSV to EPCISDocument

Values.java : Object that stores information in a CSV file