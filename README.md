# springBootSample
Notes for Implementation Exercise

	⁃	Exercise implemented by Spring Boot and Java 8.
	⁃	As database Spring Boot’s embedded H2 database is used.
	⁃	H2 engine accepts min and max values 2147483648 to 2147483647 as integers. Because of this reason ip_blacklist table’s type defined as BigINT for ip column.
	⁃	KEY keyword does not mean anything to H2 database engine, this statement has been changes as follow.   KEY `customer_idx` (`customer_id`) —>   CREATE INDEX customer_idx ON hourly_stats(customer_id);
	⁃	Id column is added to ip_blacklist and ua_blacklist tables.
	⁃	Ip type is changed as varchar, since sample request in the exercise’s explanation has it as string.

Endpoints:
Headers:
Content-Type= application/json

http://localhost:8080/clients/1/request (POST)
Body:
{"customerID":1,"tagID":2,"userID":"AA","remoteIP":"03"
  ,"timestamp":"1538559510000"}

http://localhost:8080/clients/1/statistics (POST)
Body:
{"customerID":1, "date":"2018-10-03"}

To compile project:
mvn clean install
To run project:
AdClearApplication class’ main method.

 
Connecting to database through browser while application running:
link: 
http://localhost:8080/h2-console/login.jsp?jsessionid=48f51454107ff6bfc30085d039945408
JDBC URL: 
jdbc:h2:mem:testdb
User Name:
sa
