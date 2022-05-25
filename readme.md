# Project 2 - *Flixster*

**Flixster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.
    * [x] In order to minimize API calls, I decided to fetch the video id for the movie inside of DetailActivity (after the user clicks on the movie from the main screen and accesses the detail view). This means that API calls will only be done for movies that are clicked by the user, instead of all movies. To further optimize this, I am caching the video id's of every movie using SharedPreferences, so API calls are never repeated. Error handling for when the user clicks on the movie trailer before the API call returns is also included.
    * [x] Instead of opening a separate activity with a video player, I redirected the user to the youtube app to watch the video. I decided to do it this way since most other apps do it this way, and it allows the user to leverage the full functionality of the youtube app to see comments, likes, etc. When returning to Flixster, picture-in-picture mode is enabled to continue watching the trailer.
              
The following **additional** features are implemented:

* [x] Users can favorite a movie by clicking on the heart located in the Toolbar of the detail view. 
    * [x] Favorite movies are stored in a Room database for persistence.
    * [x] Users can view all their favorite movies by clicking on the bookmark button located in the Toolbar of the main activity.
    * [x] When there are no favorites, an image and some text prompting the user to add some favorite movies fades in using animations.
    * [x] Since multiple activities must access the Room database (DetailActivity to add a favorite, and FavoriteActivity to access favorites), the database is shared across activities using the Singleton design pattern, which is the recommended approach according to Google's documentation.
* [x] UI improvements
    * [x] Learned and used Constraint Layout instead of Relative/Linear for all layouts in the app
    * [x] Used CardView widgets with elevation and rounded corners to create separation.
    * [x] RatingBar for detail view has an animation whenever the detail view is opened.
    * [x] Added a ripple animation to list items when clicked
    * [x] Detail page uses a ScrollView to scroll when the movie description is too long
    * [x] Play icon is displayed on top of the image of the movie in the details page. 
    * [x] Changed app theme to dark pink and purple
    * [x] All icons use SVG files to allow scaling between different device resolutions.
* [x] Stored api keys for Movies API and Youtube API in a secrets.xml file which is ignored by git using .gitignore
* [x] Numerical rating and number of votes for a movie is shown in the detail view
              

## Video Walkthrough

<img src='walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

## Notes

Deciding when and where to access the API/Room Database, how to minimize those accesses, where to store data to avoid duplication, and when to cache data. Explanations of the design decisions I made are included above.
## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [2021] [Tobias Pristupin]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
