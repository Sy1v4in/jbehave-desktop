!- Calculator Example 

Story: Ensure that calculator is working with all operations

Narrative:
In order to ensure my employees are calculating the customer changes properly
As a restaurant manager
I want a simple calculator for them

Scenario: User should calculate operations between two numbers properly

Given that the calculator is active
When user do the operation <operation> between <value1> and <value2>
Then the result should be <result> 

Examples:
|value1|operation|value2|result|
|10|sum|5|15|
|10|subtraction|2|8|
|10|division|2|5|
|10|multiplication|16|160|


Scenario: User erase last calculation