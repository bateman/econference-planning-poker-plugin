

# Configuring the room #

In order to start a meeting you should setup a Multi-user room on one of the conference services offered by XMPP server.

You can find lists of free XMPP service over the web. Examples are:
  * http://xmpp.org/services/
  * http://www.jabberes.org/servers/
If you are going to use these public services, you will have a room set up on <room name>@conference.<service name>

For instance if I would create a room named test on the service jabber.org.by, I have my room at test@conference.jabber.by

You have to write the room address in the 

&lt;target:room&gt;

 tag as shown in the following example.

```
<target:platform type="jabber">
     <target:jabber>
        <target:backendId>it.uniba.di.cdg.jabber.jabberBackend</target:backendId> 
        <target:room>test@conference.jabber.org.by</target:room>			
    </target:jabber>
</target:platform>
```

# Setting meeting properties #

In the case of Planning Poker be sure that the content of 

&lt;conference:service&gt;

 tag is **planningpoker**.

You could a name to the meeting in the 

&lt;conference:name&gt;

 tag, the topic you are going to discuss in the 

&lt;conference:topic&gt;

 tag and the time which the meeting is scheduled at.

```
<conference:service>planningpoker</conference:service>
	
<conference:name>Project X</conference:name>

<conference:topic>Testing Planning Poker</conference:topic>

<conference:schedule>2009-10-12T12:30:00+00:00</conference:schedule>
```

# Configuring the team #

## Adding the moderator ##

You can set the moderator of the meeting in the 

&lt;role:moderator&gt;

 tag adding a number of data in the appropiate tag:

  * the name of moderator in the 

&lt;role:fullname&gt;

 tag
  * the email of moderator in the 

&lt;role:email&gt;

 tag
  * the organization of moderator in the 

&lt;role:organization&gt;


  * the id of moderator in the 

&lt;role:id&gt;



The id is the full email that identify the account of the user.
For example if you have an account on Jabber, the name of which is **foo**, you have to write **foo@jabber.org**.
If you have a gmail account like **foo@gmail.com**, you have to write **foo@gmail.com**

```
    <role:supportTeam>
        <role:moderator>
            <role:fullname>Alessandro</role:fullname>
            <role:email>alessandrob@jabber.org</role:email>
            <role:organization>Jabber</role:organization>
            <role:id>alessandrob@jabber.org</role:id>    	      
        </role:moderator>
    </role:supportTeam>
```

## Adding the other participants ##

You have to add the other participants in the 

&lt;role:participants&gt;

 tag.

As for the moderator you should add a 

&lt;role:expert&gt;

 tag for each participant with the proper information.

For example if you have 3 accounts:

  1. foo@gmail.com
  1. bar@gmail.com
  1. bill@jabber.org

the configuration is the following one:

```
<role:participants>
        <role:expert>
            <role:fullname>Foo</role:fullname>
            <role:email>foo@gmail.com</role:email>
            <role:organization>Pinco</role:organization>
            <role:id>foo@gmail.com</role:id>     
        </role:expert>   
		<role:expert>
		 <role:fullname>Bar</role:fullname>
            <role:email>bar@gmail.com</role:email>
            <role:organization>Pallino</role:organization>
            <role:id>bar@gmail.com</role:id>
        </role:expert>  
		<role:expert>
	<role:fullname>Bill</role:fullname>
            <role:email>bill@jabber.org </role:email>
            <role:organization>Microsoft</role:organization>
            <role:id>bill@jabber.org</role:id>
        </role:expert>	
    </role:participants>	
```
