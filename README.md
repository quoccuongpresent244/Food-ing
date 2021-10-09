# Fooding
If you are a new tourist to a new city and you have no idea, or you and your lover are planning a date but don't know what to eat and where to eat? Then our application will be the best choice. The app will give you the most intuitive list of eateries around you, from which you can make your own decisions  

## Key Technologies
`Java`, `SQL`, `Android Studio`, `Computer Vision`, `Machine Learning`

# App Structure:
MapsActivity: A map show nearby restaurants
MainAcitivity:
ListFragment: Recycle view includes a list of restaurants
GuideFragment: User manual

# Major Function:
Maps activity: 
Using Google Maps API (Places API) and applying Async Task to download data of places (address, price level, image,...). 
Then we take their coordinates and use Marker to display them on the screen.
Users can interact with the restaurant through the map or the list of them. 

ListFragment: 
We create PlaceInfo class to store the information of a place like name, open hours, image,...
Then we make a list of Places Info and apply a Singleton pattern to ensure the uniqueness of the list. 
We also use Recyclerview to display these infos so users can see and make decisions. 
If they don’t know the way to go there, they can tap on the [Go Here] button. This will call a Navigation Intent of Google Maps and show them the way to go to the Restaurant that they choose.

Main Activity: 
Using ViewAnimator to smoothly switch between tabs.
Using ViewPager and NavigationBar to hold and change among our fragments.

# Team Members: 
Mai Quốc Khánh 

Phạm Thiên Long

Nguyễn Duy Anh Quốc
