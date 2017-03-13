# Report by Yuan 
### Overall

This project applied Spring annotation-based configuration, even there is no web.xml.

Implemented PUT call that is idempotent based on model name. Implemented validation of request body, including cases of "fields required", "Enum type", nested object's "required" (allocation's symbol), "percentage total" and "advisor not found".<br />

Implemented GET call that surports pagination. <br />

Implemented integration test using Spring Test. There are 10 test cases so for and each case was designed to test for expected response body, http status codes and error codes if invalid request. <br />

Persist data in memory temporarily due to time limitation and deployment convenience.<br />


### Environment:
- JDK 1.8
- Spring MVC 4.3
- jackson 2.5
- Hibernate Vlidation 5
- Junit 
- Spring Test(MockMVC)
- Mockito

### Run

Maven 3.3.9 or above is needed. Change directory to pom file, then run below command in order to build war file.

mvn clean install

### Deployment

I have deployed this project to Tomcat 8 in AWS through my personal account. The ending points are like below. Postman is a good app to test this api

**GET**: get models for an advisor<br />
http://sample-env.gfgtgdwbxs.us-west-2.elasticbeanstalk.com/v1/advisor/1/model

**PUT**: create or update a model<br />
With MediaType:"application/json" and request body<br />
http://sample-env.gfgtgdwbxs.us-west-2.elasticbeanstalk.com/v1/advisor/1/model?pageSize=5&pageNumber=0

### Requirements Satisfication

**1. Get models for an advisor**<br />
- retrieving correct elements    <br />
- pagination                    <br />
- default page number, page size        <br />
- optional page number, page size       <br />
- 200 status code                      <br />
- 404 status code                      <br />
- "errorCode":"advisor.not.found"    <br />

**2. Put call**<br />
- create a new model                    <br />
- update an existing model              <br />
- 200 status code                       <br />
- retrieve back mode                    <br />
- 404 status code                       <br />
- "errorCode":"advisor.not.found"       <br />
- 400 status code                       <br />
- "errorCode":"allocation.percentage.total.invalid"   d<br />
- validate request body, including       <br />
 * name:                  required<br />
 * description:           required<br />
 * cashHoldingPercentage: required<br />
 * driftPercentage:       required<br />
 * modelType:             required,  Enum type:{"QUALIFIED","TAXABLE"}<br />
 * rebalanceFrequency:    required,  Enum type:{"MONTHLY","QUARTERLY","SEMI_ANNUAL","ANNUAL"}<br />
 * assetAllocations:	    required<br />
- Validate nested object           <br />
 * symbol:     required<br />
 * percentage: required<br />

**3. Test cases**<br />

implemented 10 test cases, including<br />   

------PUT CALL------ <br />
- case 1: advisor not found<br />
- case 2: allocation percentage total not 100<br />
- case 3: required field missing, e.g.{name:null}<br />
- case 4: invalid enum type, e.g.{modelType:dog}<br />
- case 5: nested object's field missing, e.g.{assetAllocation:symbol}<br />
- case 6: new model created successfully<br />
- case 7: existing model updated successfully<br />

------GET CALL------ <br />
- case 8: advisor not found<br />
- case 9: success in getting models without page number and size<br />
- case 10: success in getting models with page number and size<br />

**4. authentication/authorization**<br />
not attempted

# Create a REST API from a RAML spec

Your task is to create a REST API the implements the end points available in the
provided RAML specification.

Please complete **any** number of the steps below within **5 days** of us
sending you this document. You may put in as many hours as you wish, but we
recommend a maximum of around 5 hours. Use any framework and languages you
would like (Java encouraged) to complete the task.  Update
your forked repository with your solution and a README.md with any steps needed to run it.

We are looking for code quality and innovation/thoughtfulness.

### Part I: Get it

Fork this repository.

Follow the instructions in the `api-docs` directory to view the RAML specification.  The
RAML specification defines an endpoint /v1/advisor/{advisorId}/model and the json schema
for a portfolio model.  A portfolio model is a way for an advisor to specify a group of
asset allocations and rules to manage a portfolio,
see <http://www.investopedia.com/terms/p/portfoliomanagement.asp>.

Create a REST API application that allows a user to add portfolio models for an advisor
and retrieve a list of portfolio models for an advisor.

There should be some persistent store backing the REST API to keep all the models. The
choice is yours in what to use.

The PUT call to add a model should be idempotent based on the model name.  Meaning that
multiple calls with the same model name will not add additional models to the advisor but
overwrite the existing model with that name.
Models with a different name should be added to the list of models for that advisor.

Add validation to make sure the sum of the cash allocation percentage and all of the
individual asset allocations percentage equals 100%.

The GET call should return all models for the specified advisorId as well as support
paging based on query parameters.

Please include unit/integration tests.

**Bonuses/for fun**:
- Validate the request and response from your implementation match the RAML specification.
- Add authentication/authorization so that a logged in advisor has access to only their
own models.
//
