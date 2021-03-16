# API

## Objective 

This REST API aims to:
* **broadcast coupons**, in particular in a communication campaign by couponing,
* enable the organisation (company, etc.) to **generate data** about this campaign, 
so that she can **measure** the efficiency of its campaign

## Pre-requisites

The webservice is not standalone. It plugs in the information system (database) of the company, 
so the following has to be adapted :
* the database to connect with : file `application.properties`,
* the entities in the database : folder `entities` and its related controller, 
service, and if necessary, dao

*Nota: be sure to use a relational database.*

## Installation

This is a spring boot based API. 
It can run with any IDEs with spring boot support. 

## Description

Before beginning it will be refered to a *client system* to distinguish the computing system from the person (a client of the company).
this system could be a *web* or *mobile*.

### Predicates

The client system and the webservice communication language is JSON.

#### What does the webservice do ?

The webservice:
1. Takes the URL parameter and/or JSON body when expected (refer below: *endpoints*)
2. Executes the client request by questioning the database
3. Checks the response when necessary
4. Returns the status code and
    * Either the answer in the response body if status code equals 200,
    * Or, a *''custom response body''* with detail when response status code is not 200
    
#### What does the webservice doesn't do ?

1. Check method inputs (URL parameters, request body). 
The webservice assumes that the quantity and data types received are what it expects them to be.
That means that **the client system is responsible for sending to the webservice**:
    * the **expected quantity** of parameters,
    * the **expected data types** (integer, string, etc.)

2. Hash passwords

### Key functionalities

There are two main functionalities : coupons lifecycle management and client space management.

#### Display Coupons

Coupons returned are of type `CouponReturned` (see below). There are 2 cases :
* All price reductions available (without the codePromo)
* A coupon when requested (with the codePromo)

*Nota : the codePromo is the code that gives access to the price reductions.*

#### Coupons lifecycle management

The *life* of a coupon is the following :

* **Storage** : it is the state of a `Coupon` type stored in the database, 
with only a reference to the `Product` it refers to (via its id).
The creation and destruction af a `Coupon` is not managed by the webservice.
Each organisation has to do it manually directly in the database.

* **Consultation** : when a user consults the coupon, 
the coupon sent to the client system (in the response body)
 is actually a `CouponReturned`. The difference with a `Coupon` is that it returns 
 information that originate both with `Product` (not only the product id) and with `Coupon`.
    * Underlying operation : create an object of type `Coupon_is_consulted`
 
* **Registration** : when the user, after having consulted the coupon, 
registers it in his client space.
    * Underlying operation : create an object of type `Coupon_is_registered`.

* **Removal** : when someone removes a coupon, previously registered, in his/her client space.


####  Client space management

The webservice lies in the following predicate: 
* the registration is made possible by a login mail. That is why a client space is required to register the coupon

As this is a required step for coupon *registration* and *removal* states, 
the API provides methods to process the following steps:
1. Checks whether the login (considered as unique) exists in the database
2. Then,  
    1. In the case of log in, it checks if the password matches the login and return the corresponding response
    2. In case of client space creation, add a client space in `client_space` if status code is 200
        * Underlying operation : connect to `Client` entity, search for the login, 
          and if the login already exists in `Client`, the client_id is referenced in `Client_space`

####  Data generation for the organisation

The structure and content of the API is also dedicated to help organisations to measure the efficiency 
of its campaign.

##### Principle
Maintain, in the database, the relation between the user and the coupon

##### Overview

The following schema indicates, for each entity, the attributes that relate tables to each other.

| `Product` |     | `Coupon`    |     | `Coupon_is_consulted` |     | `Coupon_is_registered` |     | `Client_space` |
| --------- | --- | ----------  | --- | --------------------- | --- | ---------------------- | --- | -------------- | 
| id        | <=> | product_id  |     | stringDateRef         | <=> | stringDateRef          |     | ...            |
| ...       |     | id          | <=> | coupon_id             |     | login_mail             | <=> | login_mail     | 

##### Explanation

As represented in the overview, tables are linked 2 by 2 (`Product` with `Coupon`, `Coupon`  with `Coupon_is_consulted`, etc.).
The two **key attributes** that really ensure the relation between the user and the coupon are `login_mail` and `stringDateRef`.

`login_mail` enables to categorize the user.
During the coupon liflecycle, from the organisation point of view :
1. In *consultation* state : the user is "**anonymous**"
2. In *registration* state : the user can be either a "**prospect**", or a "**client**" if his/her login is found in the database  

`stringDateRef` has no other use that "tracking" the coupon until the registration state
 (make the link between `Coupon_is_consulted` and `Coupon_is_registered`).
Indeed, it can be useful to identify which coupons are registered. Coupled with the `login_mail` in  `Coupon_is_registered`,
it enables to identify, who consulted/registered which coupons.

*Nota: 
* `stringDateRef` is a timestamp, so considered as unique (high probability), and easily handleable in a string format.*
* `Coupon` are linked to a city or all cities where the organisation is present. The goal is to enable local communication policies if useful for the organisation.

### Endpoints and more ...


See full documentation [here](https://documenter.getpostman.com/view/13289894/TWDTMKEz).

It also shows minimum body requests content, and some use cases with the expected result.
This API has been developed with the objective of sending as minimum parameters as possible in the URL.  

# Tests

## Scope

In this API **integration tests** are made.

They are runned with real data in a database.

Be careful : some tests manipulate data in the database (INSERT or DELETE SQL operations).
Check each test and its comments when added. Some of them require manual action in the database after running the test.

To quickly make the link between the data stored in database and those required to run the tests, `TestService` class
holds all the constants used to run the test (except URL paths) 

# Author

###### Pomme 