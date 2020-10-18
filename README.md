# Client-Server Book Database

This app is a 2 part server + client setup, you can run the server locally or host on another machine. To run simply fork download or fork the repo, extract the Server files (just take everything except Client files) and javac \*.java to compile then java Server \[port #\] to start the server. The client can be compiled into a jar or compiled via terminal and started up (Use all files but Server files).


## Messaging Protocol:
```
REQUEST\r\n
ALL
\\EOF
or 
ISBN \[ISBN\]
TITLE \[TITLE\]
AUTHOR \[AUTHOR\]
PUBLISHER \[PUBLISHER\]
YEAR \[YEAR\]
\\EOF
```
