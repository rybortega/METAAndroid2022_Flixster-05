Additional Features:

Learned and used Constraint Layout instead of Relative/Linear for all layouts in the app
Stored api keys for Movies API and Youtube API in a secrets.xml file which is ignored by git using .gitignore

Bonus/Stretch Stories
- Added rounded corners to images.
- Improved user interface:
    * Used CardView widgets with elevation and rounded corners to create separation.
    * Added a ripple animation to list items when clicked
    * Detail page uses a ScrollView to scroll when the movie description is too long
    * Play icon is displayed on top of the image of the movie in the details page. Using a SVG with transparency to allow scaling between different device resolutions.
    * Changed app theme to dark pink and purple
- Used View Binding Library to reduce view boilerplate, followed [this](https://stackoverflow.com/questions/60491966/how-to-do-latest-jetpack-view-binding-in-adapter-bind-the-views) SO answer to implement it in the adapter
- Integrated Youtube API to watch trailers from movies
    * Instead of opening a separate activity with a video player, I decided to redirect the user to the youtube app to watch the video. I decided to do it this way since most other apps do it this way, and it allows the user to leverage the full functionality of the youtube app to see comments, likes, etc. When returning to Flixster, picture-in-picture mode is enabled to continue watching the trailer.
    * In order to minimize API calls, I decided to fetch the video id for the movie inside of DetailActivity (after the user clicks on the movie). This means that API calls will only be done for movies that are clicked by the user, instead of all movies. To further optimize this, I am caching the video id's of every movie using SharedPreferences, so API calls are never repeated. Error handling for when the user clicks on the movie trailer in the timeframe between the user accessing DetailActivity and the API call to fetch the video id returns is also included.
