# Nursery Notes

Introduction 
---
Nursery Notes is an app to assist families and caregivers to record care activities for children or patients. Activities can record the name, activity name, time, date, and any additional notes the user would like to include. Users can view documented activities by most recent, list of activities, or list of names. 

When my son was born, he was extremely sick and the doctor needed us to keep a detailed record of his feedings. It was quite difficult writing in a journal and trying to find a certain date or activity. The app is a simpler, user friendly way to keep records of care. 

Intended users
---
* Parents
* Nanny/Babysitter 
* Aunts, Uncles, Grandparents
* Any other care givers

User Stories
---
[User Stories](user-stories.md)

Current state of the app
---
The Nursery Notes app runs and has most of it's functionality. The app shows the add activity pop up but does not add activities correctly and show said activity. I was only able to add the two activities shown but no more. The list of children and list of activities functions are not able to sort by child or activity at the moment.

I would like to implement the color scheme I had chosen for the project and more of a light, pleasant design to appeal to the user. The colors have been saved in the color xml but had not been put into the app.

I would like to have quite a few things added to the functionality in the future. The edit activity pop up should have the latest info in the fields as to change the info, not start with a blank add activity screen. The list of children and list of activities be able to search and display the selected name by most recently added. 

Design Documentation
---
[Wireframe Diagram](wireframe.md)

[Entity-Relationship Diagram](erd.md)
 
External Services
---
[External Services](external-services.md)

 Entity Classes
---
[Activity Entity](https://github.com/mgraham21/nursery-notes/blob/master/app/src/main/java/net/nurserynotes/model/entity/Activity.java)

[Child Entity](https://github.com/mgraham21/nursery-notes/blob/master/app/src/main/java/net/nurserynotes/model/entity/Child.java)

[Record Entity](https://github.com/mgraham21/nursery-notes/blob/master/app/src/main/java/net/nurserynotes/model/entity/Record.java)

Dao
---
[Activity Dao](https://github.com/mgraham21/nursery-notes/blob/master/app/src/main/java/net/nurserynotes/model/dao/ActivityDao.java)

[Child Dao](https://github.com/mgraham21/nursery-notes/blob/master/app/src/main/java/net/nurserynotes/model/dao/ChildDao.java)

[Record Dao](https://github.com/mgraham21/nursery-notes/blob/master/app/src/main/java/net/nurserynotes/model/dao/RecordDao.java)

Database
--- 
[Database]()

DDL
---
[DDL](ddl.md)
