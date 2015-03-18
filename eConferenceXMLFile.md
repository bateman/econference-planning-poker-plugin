
```
<?xml version="1.0" encoding="UTF-8"?>
<m:meeting
    xmlns:m="http://conferencing.di.uniba.it/meeting"
    xmlns:conference="http://conferencing.di.uniba.it/base"
    xmlns:role="http://conferencing.di.uniba.it/role"
    xmlns:target="http://conferencing.di.uniba.it/platform"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://conferencing.di.uniba.it/meeting meeting.xsd">

	<!--meeting configuration file - do not edit by hand-->
    <target:platform type="jabber">
        <target:jabber>
            <target:backendId>it.uniba.di.cdg.jabber.jabberBackend</target:backendId> 
            <target:room>test@conference.ugres.di.uniba.it</target:room>
         </target:jabber>
    </target:platform>

    <conference:name>Test</conference:name>

    <conference:topic>Testing eConference</conference:topic>

    <conference:schedule>2009-10-12T12:30:00+00:00</conference:schedule>

    <conference:items>
        <conference:item>agenda-item-1</conference:item>
	<conference:item>agenda-item-2</conference:item>
    </conference:items>

    <conference:trainingSessions/>

	<conference:directorCanDiscuss>false</conference:directorCanDiscuss>
	
    <role:supportTeam>
         <role:director>
            <role:fullname>Filippo Lanubile</role:fullname>
            <role:email>lanubile@di.uniba.it</role:email>
            <role:organization>UBari</role:organization>
            <role:id>lanubile@ugres.di.uniba.it</role:id>
        </role:director>

        <role:moderator>
            <role:fullname>Alessandro Brucoli</role:fullname>
            <role:email>moderator@di.uniba.com</role:email>
            <role:organization>Uniba</role:organization>
            <role:id>alessandro.brucoli@ugres.di.uniba.it</role:id>
        </role:moderator>

        <role:scribe>
            <role:fullname>Fabio Calefato</role:fullname>
            <role:email>scribe@di.uniba.it</role:email>
            <role:organization>Uniba</role:organization>
            <role:id>fcalefato@ugres.di.uniba.it</role:id>
        </role:scribe>
		
    </role:supportTeam>

    <role:participants>
        <role:expert>
            <role:fullname>Pinco Pallino</role:fullname>
            <role:email>pincopalla@ugres.di.uniba.it</role:email>
            <role:organization>Uniba</role:organization>
            <role:id>pincopalla@ugres.di.uniba.it</role:id>
        </role:expert>   
	    <role:expert>
	    <role:fullname>Ciccio Cappuccio</role:fullname>
            <role:email>cicciocapp@ugres.di.uniba.it</role:email>
            <role:organization>Uniba</role:organization>
            <role:id>cicciocapp@ugres.di.uniba.it</role:id>
        </role:expert>                                        
    </role:participants>
</m:meeting>
```