# Pre-work - *List Keeper*

**List Keeper** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Mark Diez**

Time spent: **2** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Mark items as done
* [ ] Expand to have a list of to-do lists
* [ ] Create a template to-do list e.g. Camping To Do list
* [ ] Add a filter to sort items by priority, by tasks finished, etc.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/kV8kOA3.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

So far, the biggest challenge was setting up Oracle Virtual Box for Genymotion. For some reason, version 5.x disables the host's
internet. I still don't understand how keeping a shortcut and startup icon when installing solved the issue.

Figuring out how to override default behavior was harder than I thought. 
In particular, it took me hours to get the behavior I wanted for single click and long click with each list view item. 
I wanted (and succeded) to have a checkbox to check off an item and when it checks off have a strikethrough on the text.
The first hurdle was retaining the onclick and onlongclick behavior of the list view when another focusable object exists (like a checkbox or another button).
This was solved by simply adding focusable = "false" and focusableOnTouchMode = "false" on the element.
The next big hurdle was keeping the ripple effect only on the view which was solved by specifying drawSelectorOnTop = "true" on the listview and setting the background of the checkbox as transparent. 

Next big hurdle was trying to programatically change the color of a drawable for my priority tags. 
Multiple stack overflow sources have noted to essentially get the drawable, cast it as a GradientDrawable, then set the resource color.
This made my app crash stating that it was pointing to null. 
As a last resort after an hour or two of frustration, I "hard coded" it by just creating each drawable then programatically change the resource for it. 
As soon as I started writing about it here, I realized that I've been getting the background of my imageView, not the source (which is just getDrawable)... 
 

## License

    Copyright 2016 Mark Diez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
