****************************************
* Welcome to CHALEY CHAT APPLICATION   *
****************************************

###########
# AUTHORS #
###########

SOSNIN ANDREY
DJAN DENNIS MINTAH

###########
# COMPILE #
###########
To compile simply run
$ javac *.java

This compliles all the classes

#######
# RUN #
#######

*RMI registry
$ rmiregistry

*The server
$ java ChatServer

*The client

$ java ChatClient


* All in seperate terminals

######################
## FUNCTIONALITIES ##
#####################

++++++++++++
+ JOIN CHAT+
++++++++++++
To start chatting a user must join the chat by using the following command

$ JOIN <name>

eg

$ JOIN dennis

response:

"Successful Registration! start chatting
Your unique identifier is dennis_4. Please use this for Private messaging"

<name> refers to the client name and the < > brackets should be ignored

+++++++++++++++++++
+ PRIVATE MESSAGE +
+++++++++++++++++++

This feature allows to send a message to a particular connected client. It requires the 
unique identifier of the receipient.

Command

$ @ <unique_id> <message>

eg:

$ @ dennis_1 helloworld

<unique_id> refers to the unique identifier of the client
<message> refers to the message to be sent

+++++++++++++++++
+ GROUP MESSAGE +
+++++++++++++++++
This is the default for all messaging. When messages are sent in this mode 
all connected clients receive. 

Note: You have to join before sending messages to the group

$ <message>


eg:

$ helloworld


+++++++++
+ LEAVE +
+++++++++

This feature allows a client to leave the chatroom

Command 

$ LEAVE



+++++++++++
+ HISTORY +
+++++++++++

This feature allows one to view history of previous messages in the group chat

*NOTE* This feature is only available for group chats. Private chat history are not 
available.


COMMAND:

$ HISTORY TODAY 

$ HISTORY H <number_of_hours>

$ HISTORY DATE YYYY-MM-DD

eg:

$ HISTORY H 2

response:
history for last 2 hours

eg 

$ HISTORY DATE 2018-02-16








