# My Personal Project: 
## **Book List App**

> I have over 30 pdfs of various books stored on my laptop
> in different folders, and even more books cluttered around my
> room. I'm simultaneously reading about a fifth of them, while 
> being too lazy to use bookmarks for about half. It also often 
> happens that I'm very excited to read a book, get about halfway
> through, and then am bombarded with college work and forget to 
> get back to it and instead pick up a different book in front of me
> 
> Clearly, the optimal way to address my issues regarding closure and 
> finishing tasks before starting new ones was to build an app to help me 
> keep track of everything I'm reading: **how far along I am, where the book is stored, 
> sort the list by progress** (so I know which books I'm almost done with),
> **sort the list by how excited I am to read the book, and also as a bonus,
> add notes** to the book, so I can keep track of important data and graphs, 
> cool quotes, the main points of the book, or just my remarks about the book.
>
>
> The application is for anyone in my position who likes to read multiple books 
> at the same time, and sometimes forgets where a book is stored/kept, or that 
> they were reading the book in the first place

## User Stories

> **A user must be able to:**
> - *add to or remove a book* from the list
> - *add to or remove notes* from a book in the list
> - view the book list and *sort it by excitement or progress*
> - *view the location* of the book in the list, along with other information
> - *save* the book list when they want
> - *open* the saved book list


## Phase 4: Task 2

> **Sample Event Log**
>
>``` 
>EVENT LOG
>
>Thu Mar 31 09:51:44 PDT 2022
>Added book: Superforecasting
>Thu Mar 31 09:51:44 PDT 2022
>Added book: Kafka on the shore
>Thu Mar 31 09:51:44 PDT 2022
>Added book: The Time Machine - HG Wells
>Thu Mar 31 09:52:24 PDT 2022
>Removed book: The Time Machine - HG Wells
>Thu Mar 31 09:52:29 PDT 2022
>Removed book: Kafka on the shore
>Thu Mar 31 09:52:31 PDT 2022
>Added book: The Art of Investing ```


## Phase 4: Task 3
![UML Class Diagram](https://github.students.cs.ubc.ca/CPSC210-2021W-T2/project_q2s8b/blob/master/UML_Design_Diagram.jpeg)

> Potential refactoring
> - Create an abstract ui class of which ConsoleBookListApp 
> and MainAppWindow are extensions, as both contain fields for 
> a BookList, JsonReader and JsonWriter. Any potential ui class
> that allows the user to interact with the book list would
> require those fields as well as certain general abstract functions
> like adding and removing books or saving the list.
> - Other than that, I feel like the code has minimal dependencies. 
> There are no cycles of arrows, or heavy interlinking. Most of the 
> connections are very linear. BookList has the most number of 
> dependencies but none of them are redundant or can be clubbed
> together.