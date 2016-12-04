#Project-1 Story Finder
#### Team Number
Team #26
#### Team Members
Jesse Christiansen, Ryan Jung, William Fujimoto
#### Youtube video Link
TODO - Add your team's unlisted youtube video link here.

## Problem statement:
You will be building an app that displays a list of Cards based on a Search Query.

Here is what the screens will look like:

Screen1(With Search bar and the RecyclerView): 
![Screen1](/imgs/screen1.png?raw=true "")

Screen2(Details of the Story): 
![Screen2](/imgs/screen2.png?raw=true "")

The user will have the chance of entering in a search query into the search bar. Your app should populate the RecyclerView(which is right below it) with cards. Clicking on a Card will take the user into another activity that will have details for that story.


###Models & Controllers
**A class called Story**, that holds the data members for a single Card. The Story class should have the following members:

* UUID mUuid
* String mName
* String mDescription
* A Picture field (an appropriate image type)

**A Singleton for Stories**, Look at the class samples to look at how you should use the Singleton. You can implement the search mechanism here.

**A Recycler View** that is right below the search query(Look at the mockup pictures)

**Other necessary classes as seen in the class samples**

##Constraints
* For now you must support a Potrait phone layout only.
* You are strongly recommended to follow best practicies for android development. Look at the samples for best practices. If you use something better, document it in your submission video.
* Use an appropriate search widget for the search bar.
* Use appropriate widgets to represent Cards in android(Instead of designing from scratch).
* You are free to choose implementation details that are not mentioned in this requirements Readme.


##Requirements:
These requirements are divided into tiers, completing more will grant you a higher grade for the assignment.

**Tier 1:**

* Layouts are built correctly, with a search widget and a recycler view
* Singlton class and model implemented.
* The recycler view populates cards.

**Tier 2:**

* Your app supports Material design and the Cards/view is aesthetically pleasing.
* Search should work(search by Name of story). The user enters a query, then hits "Search" to search for Items.

**Tier 3:**

* Add a splash screen
* Real-time search, starts searching and populating the recycler view as soon as the user enters in characters into the query.
* App is throughly tested to remove any bugs. Document any additional work into the video.


##Submission
* You must push your code onto GitHub before the deadline.
* You must create a Video (minimum 30 seconds, upto 10 mins max) that demonstrates the app working. This video is your chance to explain to us how you implemented the requirements and any other project details. Upload the video as an Unlisted video on Youtube, and add its link onto this README.
* Document any and all **extra work**(outside of the requirements) into the video.


## Note
* There are no extensions
* Make sure your last push is before the deadline. Your last push will be considered as your final submission.
* You will be building on this assignment in Project 2. So you have to make sure that you finish this assignment (atleast till tier1 for a minimum grade)
* Post questions on Piazza if you have any questions.
* Please contact the course staff if you run into issues. We are here to help you!
