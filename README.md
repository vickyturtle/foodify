# Foodify
This app searches for food on lifesum server and allows you to view their nutrition details and save them for later.

- Search cards are colored according to their Calorie content.
- Cards animate when you scroll for first time.
- Favorite button animates only when you make an item favorite.

## Project Structure
- Project is structured to follow MVP pattern with the Presenter and Scene API's defined inside `core` package. All activities are implementation of `Scene` while presenter implementations are found in `logic` package.
- All server entities are found in `entity` package and are transported to UI models using `Trasnsporter` helper class.
- There is generic RecyclerView Card Adapter which handles all type of cards and gives a heterogeneous card list and follows MVP pattern. All cards have a Presenter and Data associated with them.
- Realm is used for database, as it is lot easier and efficient to implement than sqlite ORM's and provides better performances than them.
- Retrofit/OkHttp caching is used for 5 min for offline data fetch.
- Dagger2 has been used for dependency injection with a `PerActivity`, default and `Singleton` scopes.
- RxJava and RxAndroid has been used for Publish/Subscribe pattern.
- Mockito has been used to provide mocks in Unit tests.

## Note
- There are some structural classes(like Logger, etc) which I have copied from my existing projects. Also, I have tried to use different libraries to provide and idea of their implemntation. All the libraries used are declared in `libraries.gradle` file.
- I have tried to showcase the MVP pattern implementation which can be scaled much easier to bigger projects, though it may be a bit of overkill for this small sized project.