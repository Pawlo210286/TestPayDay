# Architecture Overview
The architecture solution for project is MVVM and Clean architecture. Widely used Android Architecture Components.
The graph below shows approach:
<img src="/architecture.png" alt="architecture" title="architecture" width="560" height="370" />

MVVM architecture is the most suitable solution for And:roid app development, and has advantages:
 - Maintainability: working with smaller and focused parts of the codes and make changes easily due to the separation of different kinds of code
 - Modularity: provides clearer separation of the UI and application logic
 - Extensibility: easier development in a huge team, ability for developers to split the app into different independent modules, which can be developed in the same time
 - Testability: unit testing even easier, as there is no dependency on the View

# Presentation scheme
<img src="/presentation_layer.png" alt="presentation layer" title="presentation layer" width="763" height="450" />

## A list of technologies I chose for this project
Shown in the current demo project:
- Kotlin
- Coroutines - used for threading
- LiveData & ViewModels
- Kodein - dependency injection
- Retrofit/OkHttp - networking
- Gson for JSON parsing
- Timber for logging and debugging

## Flow Diagram implemented in this project
# Sign in diagram:
<img src="/user_flows/sign_in_flow.png" alt="Sign in flow" title="Sign in flow" width="516" height="769" />

# Sign up diagram:
<img src="/user_flows/sign_up_flow.png" alt="Sign in flow" title="Sign up flow" width="484" height="780" />

# Dashboard diagram:
<img src="/user_flows/dashboard_flow.png" alt="Dashboard flow" title="Dashboard flow" width="410" height="743" />

# Transactions list diagram:
<img src="/user_flows/transaction_list_flow.png" alt="Transactions list flow" title="Transactions list flow" width="427" height="743" />

## Technical questions

##### **Q**: What would you add to your solution if you had more time?

-14 h, unit tests, instrumental tests

##### **Q**: What was the most useful feature that was added to the latest version of your chosen language?

The Kotlin/JVM compiler now generates type annotations in the bytecode for Java 8 and later targets.
but in this project, none of the innovations of the latest version of the language were used.

##### **Q**: How would you track down a performance issue in production?

- Firebase Performance Monitoring.

##### **Q**: How would you debug issues related to API usage?

 - I used charles, postman

##### **Q**: How would you improve the Node server’s API that you just used?

- add the ability to sort the results
- use HTTPS instead of HTTP for security
- use single format for DateTime representation;

>Please describe yourself using JSON.
```json
{
  first_name: "Pavlo",
  last_name: "Nikitin",
  skils: [
    "android",
    "kolin",
    "java"
  ],
  languages: [
    "Russian",
    "Ukranian",
    "English"
  ]
}
```