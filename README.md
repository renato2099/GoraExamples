GoraExamples
============
@author rmarroquin

Description
===========

This project presents examples of how to use Apache Gora with different data stores.
Currently (out of the box) examples cater for the following Gora stores.

* gora-cassandra (CassandraStore)

Instructions
============
The examples include the following scenario's

* Alien - Alien describes an entity who has a first and last name, a password, telephone number 
  and an index associated with the telephone number. To display how schema mapping is handled 
  in Gora, the telephone number is stored as a Union.
* Simpson - Simpson describes one of the fictional characters from the well known 
  adults programme (ha ha). Each member of the family may have a first and last name, a password, 
  telephone number and an index associated with the telephone number.
  To display how schema mapping is handled in Gora, the telephone number is stored as a Union.
* User - User describes one of the most common Database entries. Each User may have only a 
  first and last name and a password.
