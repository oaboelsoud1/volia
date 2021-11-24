# Volia
## Notification System Task


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

  # Database Design
  - database designed as stated in the requirements , but I added few things to make the design better 
  - UserNotificationLog : used to store the user notificaiton preference . As well , it stores user id and notificaiton code /id at the time of sending it . In order to  help the system recogoize if the user has notified with the same notification before or not . This could be acheived by adding boolean flag in user table which indicates if user has been notified before or not  ; however , I thought that wouldn't make scenes because the notification table has 10 different notification . Boolean flag would make the user receive only one notification . so I made it more generic and depend on if the user notified with specific id before or not . if yes user will not be notified with it again if not , the user will receive a new notification. Although this a generic approach and scalable , but it   effects the peforemance of the application since it Locks the database with every batch inseration. but I think the solution has been used to notify to load and notify the user overcame this issue a little bit . 
  - HibernateSequence : used to generate auto incremented and unique code for database records by storing last id table sequance. This made for replacing records's id . 
  - User : is designed as the requirements with a little change instead of storing customer user preference as integer 0 or 1 . it stored as string to be more expressive and readable . In Entity , it given enum datatype and converter used to convert it to String or Enum .Enum is used instead of String because it is ( cleaner  , extendable and to avoid alot of errors and data corruption . Morever , NotificationLog is virtually referenced in User database to the query reterive user eligable for notification easier .
  - City : designed as mentiond . the city name column has been set to be unique . I think there is no 2 cities would hold the same name . 
  - Notification : I added a code for each notification to replace searching using id . so each notificaiton has a code starts with (NA-{Increamtal unique number})
  - Flyway used in data migration
 
 
 # System  Design 
 - Since the system requires send notification to  massive volume of users . loading one million user and notify them by once would be the worst choice since it would lock the database till it finish and memory expensive . the pagaingtion is used to query on elgiable users, notify , store the user notification preference by sending time . I should use queue with pagination to avoid pagnation issue like skipping elements , but I didn't got enough time .
 
 #  How to use : 
  - Notification could be triggered using endpoint 
```
POST localhost:8080/v1/notifications/notify
RequestBody :
{
"userNameFirstLetter" : (this field can be null) type string
"cityName": (this field can be null) type string
"notificationCode" : (this field can NOT be null) type string - used to identify which one of ten notification will be sent to the user 
}
```
- There are some endpoints added to help in inseration of new user, city and notification records in database . as well they are used in the integration test .
```
POST localhost:8080/v1/cities/
RequestBody :
{
"name" : string - can not be null
}
```
```
POST localhost:8080/v1/users/
RequestBody
{
"name" : String - can not be null (user name)
"cityName" : String - can not be null (the city customer belongs to )
"notificationType" : String - (whether SMS or EMAIL ) . it should be written CAPS as in the example
}
```

Validation has been applied on all the fields needs to .

- RestAdvice has been added to handle the exceptions . 
- ServerError class has all the system errors . the default status error is bad request 


The appliction is dockerized ; however , TestContiners in tests wit mariaDb since SQLDB Container image is not compatibility with Mac M1 
