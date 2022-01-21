# Software Architecture design patterns

Software Architecture design patterns provide guidelines and solutions to common problems that we face every day when developing software. They can also provide a common language and vocabulary for communicating efficiently within a team. Choosing one early on in the development process is important since the cost for refactoring old code is higher then doing it right in the first place. No pattern is perfect, we must choose the one that fits the nature of our application, the team that is implementing it, and many more other factors. Deciding on the correct factors to take into account and their respective importance is as crucial as implementing the pattern correctly.

Below we will discuss two well-known patterns, Flux (Redux) and MVC. We will also try to provide an implementation plan for Flux

## MVC - Model View Controller

MVC is one of the most prevalent patterns, for client-facing applications and it consists of three interconnected layers, the model, the view, and the controller. The model refers to the backend or business logic. The view refers to the part of our application that is responsible for creating the user interface. The controller is the intermediate layer between the model and the view that is responsible for servicing the requests of the view layer. This is to allow separation of concerns, better testing, and code reusability. For example, the same controller can handle actions from multiple views.

## Flux (Redux)

Flux architecture takes a different approach. It has three parts, the dispatcher, the stores, and the views. The stores contain both the application logic, state, and data. They receive actions and choose how to update their state and they don't have external dependencies. The views than can be bound to any store so that they have access to its state. A view in Flux is responsible only for rendering the UI, they receive updates from the store and choose how to update their internal structure. Finally, the dispatcher is used to coordinate the communication and dependencies between stores. For example, the dispatcher may first wait for an action to be completed by one store before sending a different action to another store. It is not always necessary for example if we have only had simple actions with no dependencies.

## Comparison

The MVC is a more "traditional" design pattern. It's not opinionated, since it only describes the concerns of each layer and leaves the implementation details to the programmer. It has bidirectional communication, the controller renders the view and the view sends actions back to the controller. Even though it is more popular in object-oriented programming languages it can be used with almost any tooling. One concern with MVC is that its not always possible to have views that represent 100% the data provided by the model. This usually leeds to application code inside the view layer.

On the other hand in Flux we have a unidirectional flow. The Store provides the data to the View, then when an interaction happens the View dispatches an action to the Dispatcher. The Dispatcher then sends the action back to the store. The store decides how to update its state and notifies the view that the data has changes. This encourages a more declarative/functional style of programming. It also describes in detail how each component of the architecture should function. Flux also leads to a lot of boilerplate code, which can be prohibiting if the right tooling is not available (automatic binding of views, action creators etc). For this reason in Android application development we must use Kotlin instead of Java.

## Pattern Application

The Flux pattern was chosen to be used with the movie application.

## Structure

To implement the Flux pattern in our application, the following structure is proposed.

- store/models/Movie
- store/actions/Action
- store/actions/StartLoadingAction
- store/actions/SearchMoviesAction
- store/actions/SetMoviesResultAction
- store/actions/SetDetailsAction
- store/actions/FetchCastAction
- store/actions/SetCastAction
- store/actions/ActionType
- store/MoviesState
- store/MoviesStore
- store/MoviesViewModel
- MovieDisplayView (Activity)
- MovieListDisplayView (Activity)

Each part's design and implementation will be explained in a separate section.

## Movie

Movie is a data class. It is constructed from the response of tmdb API and represents a single movie record. It will contain only getter methods and a single build method used to construct an object from a JSON response.

## Actions

Action is a common interface for all the actions. Each action will have a unique action type (enum) that will be used by the store to distinguish them and cast them to the appropriate class. It represents a state transition for our application.

- StartLoadingAction    -> Transition to loading
- SearchMoviesAction    -> Start fetching movies
- SetMoviesResultAction -> Save movies result
- SetDetailsAction      -> Set a movie id as chosen
- FetchCastAction       -> Start fetching cast
- SetCastAction         -> Save cast result

## Movies State

MovieState is the data class used to store the state of our application. It will store the list of movies the user is viewing, the current search term, if we are loading new results and if a movie id is selected.

## MoviesStore (Singleton)

The MoviesStore will be the single source of truth for our application.

It will also have an apply method that takes an action as an argument and applies the appropriate changes to the state, as described above, and returns the new state.

## MoviesViewModel

The MoviesViewsModel will be used to bind the data from the store to the views. Since our application is simple it will also play the role of the dispatcher.

It will have a getState method that returns MutableLiveData of type State.

For each action type, we will define a separate dispatch method. A dispatch method may apply a single action or multiple actions to the store.

After we apply an action to the state we must also update the appropriate LiveData variable so that the UI can get notified about updates.

For example the `dispatchSetMovies(int id)` will simply call `store.apply` with a new SetDetailsACtion. In contrast the `dispatchSearchMoviesAction` will first dispatch a `SearchMoviesAction` and a `StartLoadingAction`, set the value to the liveData variable and then fire a request. When the request finishes it will apply a new action to the store to update the search results list.

It will need to inherit from AndroidViewModel to perform network requests that need access to the context.

## Views

Flux imposes very few constraints on the UI. Since each component can be bound to the store there is no need for a single central controller to coordinate the views. For this reason, we can design smaller more reusable parts of UI. Since our app is simple though we will keep the same structure and have each page as a separate view/activity with its own layout.

## MoviesListView

This is the initial screen of our app. Its purpose is to initialize the view model, bind an event listener to the SearchView to call the `dispatchSearchMoviesAction`, listen to state updates.

If after a state update we have a selected movie for viewing it will start a new intent to navigate to the next activity (MovieView).

If after a state update there is a moviesListResult it will also update the RecyclerView.

## MovieView

Similarly, the MovieView will initialize the view model, call the `dispatchFetchCast` method of the view model and listen to state updates to display the cast.
