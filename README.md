# My Personal Project

## A Supermarket Management System

### Project Description
This project is a supermarket management system which consists of a Stocking system, a Cashier System and a Supervisor
system which looks over the whole management. Each one of these systems have some specific tasks whick they can 
perform. This management system also records all the transactions made by the Cashier.

#### Who will use it?
A supermarket has to do a lot of things at once (like managing billing, keeping track of stock present). This makes it
 harder for the person managing their supermarket. This system would maintain these. Any supermarket which needs a
  management system for its store can use this system.

#### Why is this project of Interest to you?
I like to keep track of things and maintain an order. This project is similar to this, i.e. it manages things and 
maintains the power hierarchy between different levels of employees. Organizing things and keeping them updated at 
all times is a good practice when you need to manage some store.

##### For Phase 2
The persistence package in main and test folders, and some methods in model classes were modeled with the help of
- Name: JsonSerilizationDemo
- Author: Paul Carter
- Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
- Repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

##### For Phase 3
The soundfile was obtained from 
- Title: Cha Ching Register
- name in the file: soundEffect.wav
- path: ./data/soundEffect.wav
- Link: http://soundbible.com/1997-Cha-Ching-Register.html

### User Stories
- As a user, I want to be able to login to admin initially
- As a user, I want to be able to make new different levels of employees
- As a user, when I login to *admin* or a *supervisor*, I want to be able to view transactions and make changes to 
the employee list and inventories
- As a user, when I login to *Cashier*, I want to be able to process billing for a customer
- As a user, when I login to *Stocking Staff*, I want to be able to add, remove and look at the items in the inventory.
- As a user, I will be able to *logout* of any employee account
- As a user, I want to be able to *save all the data* when I quit the program
- As a user, I want to be able to *use the information saved during the previous session*
- As a user, I want to be able to *save all the inventories, transaction, employee records*
- As a user, I want the program to save the essential data *before ending the program*
 and load the essential data *after starting the program*
 
 #### Phase 4: Task 2
- In this project I implemented the first option given, that is "Test and design a class in your model package that
is robust".
- I made the **Item** class in the **model** package to be robust. The methods which were made robust were the 
**addStock** method and **removeStock** method. Both these methods take an integer as the parameter. Both these 
methods throw a **StockCannotBeEditedException**. This exception is present under the exceptions package under main.
  
 #### Phase 4: Task 3 
- The diagram shows that there is good amount cohesion, but the coupling can be improved upon. There are 
 ###### What I would have improved if I had more time
- I would have tried to reduce the duplicate code in the level two and level three class. Specifically, I would
make a billing page class under the commonpages Package.
- I would try to rearrange the code in each class in the ui package. Make it easier to understand.
- I would try to add an animated background, so that the program would be more interesting to use.
- I would try to improve the alignment buttons, labels and textFields so that  they look a little more spaced out.
- I would go trough the code and try to resolve any bugs if present.