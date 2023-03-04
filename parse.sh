#! /bin/bash

javac -d bin code/nlp/parser/*.java
java -Xmx1G -cp bin nlp.parser.CKYParser $1 $2