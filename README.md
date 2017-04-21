#Overview

The implementation is based on Java, Maven & Spring Boot. Spring Boot is used only for UI & REST.

#Steps to run

1) mvn clean install

2) mvn spring-boot:run

3) Navigate to http://localhost:8080/

4) Enter the term to search


#Points to note

1) Redis source code is already extracted inside the "source-code" directory

	$ ls

	CodeIndexer.iml README.md       pom.xml         source-code     src             target


2) The path of the source code can be configured in src/main/resources/application.properties.
   To configure a different directory, set the property "sourcecode.location" to relative path.

3) Application startup indexes the content of the directory specified in (2) and holds it in memory.
   It is not persisted & will be rebuild for every restart.

4) Partial searches are supported. Searching for "ell" will return -> hell, hello...

5) File name specific searches are supported.

6) Non-alphanumeric characters in search will be ignored (i.e. * ? + . etc.)

7) Multi word searches are supported.

# Explanation of the json output 

	{
	
	  "lineNumber" : 1046,
	
	  "isFunction" : false,
	
	  "isFileName" : false,
	
	  "file" : "./source-code/redis-unstable/deps/hiredis/sds.c",
	
	  "snippet" : " * will have the effect of turning the string \"hello\" into \"0ell1\"."
	
	}	
	

lineNumber -> the line number in the file where the matched term is present (will be 0 if its a file name)

isFunction -> true if the search matched a "function" (function name indexing has not been implemented,
                                                        so this will always be true for now)

isFilename -> true if the search matched a "file name"

file 	   -> the location of the file 

snippet    -> snippet of the line which matched the search

