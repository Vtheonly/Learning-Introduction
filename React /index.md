To enhance your React tutorial by adding more labs to each chapter (assuming "chatpetr" was a typo for "chapter"), I’ve expanded the original lab ideas with additional hands-on exercises. Below is a detailed index for each section of your React tutorial, including the original labs plus new ones to ensure learners get comprehensive practice with the concepts covered. Each chapter includes at least five lab ideas to provide ample opportunity for hands-on learning.
📘 React Tutorial - Enhanced Index with Additional Labs
00:00:00 - React Tutorial for Beginners ⚛️
Concepts Covered:  
What is React and why use it?  
Setting up a React environment (Node.js, npm, Vite/Create React App)  
Understanding JSX (JavaScript XML)  
Rendering elements in React
Lab Ideas:  
Set up a new React project using Vite or Create React App to practice initializing a development environment.  
Create a "Hello, World!" component and render it to the DOM to get familiar with component creation and rendering.  
Modify JSX with dynamic data, such as displaying a greeting based on the current time of day (e.g., "Good Morning" or "Good Evening").  
Build a DateTime component that displays the current date and time, updating it with a static value initially.  
Create a Simple Calculator component that uses JSX to display and perform basic arithmetic operations (e.g., addition, subtraction) based on hardcoded values.
00:20:26 - Card Components 🃏
Concepts Covered:  
Understanding components in React  
Creating functional components  
Reusing components  
Organizing components in separate files
Lab Ideas:  
Create a reusable Card component with a title, image, and text to practice component structure.  
Render multiple Card components with different content to explore reusability.  
Pass data via props to the Card component (e.g., different titles, images, and descriptions for products or profiles).  
Build a CardList component that renders an array of Card components from sample data like a list of movies or books.  
Add a Modal component that opens when a Card is clicked, displaying additional details about the card’s content.
00:32:24 - Adding CSS Styles 🎨
Concepts Covered:  
Styling options: inline styles, external CSS, CSS Modules, Styled Components  
Using className in JSX  
Dynamic styling with state and props
Lab Ideas:  
Style the Card component using an external CSS file to apply consistent styling.  
Use CSS Modules to scope styles specifically to the Card component and avoid conflicts.  
Apply dynamic styling, such as changing the Card’s background color based on a prop (e.g., "light" or "dark" theme).  
Create a Button component with styles that vary based on props (e.g., "primary", "secondary", "danger").  
Implement a Theme Switcher that toggles between two color schemes (e.g., light and dark) using CSS variables and state.
00:40:40 - Props 📧
Concepts Covered:  
Passing data with props  
Setting default props and using PropTypes  
Destructuring props  
Props as read-only
Lab Ideas:  
Create a Profile component that accepts name, age, and avatar as props and displays them.  
Render multiple Profile components with different user data to practice prop usage.  
Add default props to the Profile component (e.g., default age of 25 if not provided).  
Build a Product component that displays details like name, price, and an optional discount percentage via props.  
Create a Comment component with author and text props, using PropTypes to enforce data types.
00:52:49 - Conditional Rendering ❓
Concepts Covered:  
Conditional logic in JSX (if, ternary, &&)  
Conditional className for styling
Lab Ideas:  
Create a Login Button that displays "Logout" when a user is logged in, based on a boolean condition.  
Show/hide a section of the UI based on a state value (e.g., a "More Info" toggle).  
Apply conditional styling, such as enabling a dark mode class when a condition is true.  
Build a Notification component that displays different messages and styles based on type (e.g., success, error).  
Create a Weather component that renders different icons and text based on a weather prop (e.g., "sunny", "rainy").
01:03:04 - Rendering Lists 📃
Concepts Covered:  
Mapping arrays in JSX  
Using the key prop  
Dynamic list rendering
Lab Ideas:  
Create a To-Do List that renders tasks from an array as list items.  
Display a list of users from a dataset with properties like name and email.  
Add dynamic list updates with buttons to add or remove items from the list.  
Build a Gallery component that renders a list of images with captions from an array.  
Create a Table component that displays tabular data (e.g., name, age) with unique keys for each row.
01:29:43 - Handling Click Events 👆
Concepts Covered:  
Event handlers in React  
Using arrow functions  
Passing arguments to handlers
Lab Ideas:  
Create a button that logs a message to the console when clicked.  
Build a counter that increments a number with each button click.  
Pass event data to a handler, such as logging the button’s text or target details.  
Implement a Like Button that toggles between "Like" and "Unlike" on click.  
Create a Quiz component where clicking buttons selects answers and updates a score.
01:42:04 - useState() Hook 🎣
Concepts Covered:  
State in React  
useState() syntax and updates  
Triggering re-renders with state
Lab Ideas:  
Create a counter app that increments or decrements using useState().  
Build a toggle switch to show or hide a block of text.  
Make a dynamic form with inputs (e.g., name, email) controlled by state.  
Implement a Rating component where users select a star rating that updates state.  
Create a Shopping List where users can add items to a state-managed list.
01:58:36 - onChange Event Handler 🚦
Concepts Covered:  
Handling form input changes  
Controlled vs. uncontrolled components  
Managing form state
Lab Ideas:  
Create an input field that updates state as the user types and displays the value below.  
Display a live preview of input text in real-time (e.g., a username preview).  
Build a form with multiple fields (e.g., name, email) stored in state.  
Implement a Search Bar that filters a static list of items based on user input.  
Create a Registration Form with fields like name and password, adding basic validation on submit.
02:13:16 - Color Picker App 🖌
Concepts Covered:  
Using input[type="color"]  
Dynamic style updates with state
Lab Ideas:  
Build a color picker that changes a div’s background color based on user selection.  
Save the selected color in state and display its hex code.  
Add a reset button to revert the color to a default value (e.g., white).  
Extend the picker to change text color in addition to background color.  
Create a Palette Generator that suggests complementary colors based on the chosen color.
02:23:31 - Updater Functions 🔄
Concepts Covered:  
Functional updates with useState()  
Using prevState for reliable updates
Lab Ideas:  
Create a counter that uses prevState to increment or decrement correctly.  
Build a voting system where multiple buttons increment a shared count.  
Implement a Progress Bar that increases by a percentage with each click, using functional updates.  
Create a Multi-Step Form where each step updates state based on previous inputs.  
Build a Timer that increments every second using functional updates for accuracy.
02:30:45 - Updating Objects in State 🚗
Concepts Covered:  
Immutable state updates  
Using the spread operator
Lab Ideas:  
Create a User Profile component that edits name and email in a state object.  
Update a car object in state based on a dropdown selection (e.g., model, color).  
Build a Settings Panel where users update app settings (e.g., font size, theme) in an object.  
Implement a Character Creator for a game, updating attributes like name and strength.  
Create a Contact Form that updates an object with user details as they type.
02:39:55 - Updating Arrays in State 🍎
Concepts Covered:  
Adding/removing array items  
Using immutable array methods (map(), filter(), etc.)
Lab Ideas:  
Build an editable To-Do List with add and remove functionality.  
Create a Shopping Cart where users can remove items from an array.  
Implement a Tag Input component for adding and deleting tags.  
Build a Playlist where users add and remove songs from a state array.  
Create a Favorites List where users can add or remove items like books or movies.
02:48:55 - Updating Array of Objects in State 🚘
Concepts Covered:  
Updating specific items in an array of objects  
Using map() for immutable updates
Lab Ideas:  
Build a Student List where users can update grades for individual students.  
Implement CRUD operations for an employee database (create, read, update, delete).  
Create a Kanban Board with tasks that can be edited or moved between columns.  
Build a Recipe App where users edit ingredients in a recipe (array of objects).  
Implement a Task Manager where users update task details like priority or status.
03:01:42 - To-Do List App ☝
Concepts Covered:  
Combining state updates  
Form submissions  
Local storage persistence
Lab Ideas:  
Build a functional To-Do List with add, edit, and delete features.  
Add local storage to persist tasks across page refreshes.  
Implement task completion with a filter for completed vs. pending tasks.  
Add drag-and-drop to reorder tasks in the list.  
Create a Category Filter to group tasks by categories like "Work" or "Personal".
03:24:17 - useEffect() Hook 🌟
Concepts Covered:  
Handling side effects  
Cleanup functions
Lab Ideas:  
Fetch API data (e.g., a list of users) and display it using useEffect().  
Implement a timer that updates the UI every second with a cleanup function.  
Build a Weather App that fetches weather data based on user input.  
Create a Chat App that listens for messages via WebSockets in useEffect().  
Implement Auto-Save for a form that saves to local storage every few seconds.
03:44:08 - Digital Clock App 🕒
Concepts Covered:  
Using setInterval() in useEffect()  
Real-time state updates
Lab Ideas:  
Create a real-time Digital Clock that updates every second.  
Add time zone support to display clocks for different regions.  
Build an Analog Clock using CSS or SVG that updates in real-time.  
Implement a Stopwatch alongside the clock with start/stop controls.  
Add an Alarm Feature that alerts the user at a specified time.
04:00:08 - useContext() Hook 🧗‍♂️
Concepts Covered:  
Global state with useContext()  
Avoiding prop drilling
Lab Ideas:  
Create a theme toggle system with light/dark modes using useContext().  
Build a User Authentication context to manage login state globally.  
Implement a Multi-Language App with language selection stored in context.  
Create a Notification System where messages are managed via context.  
Build a Settings Context to share user preferences (e.g., font size) across components.
04:11:44 - useRef() Hook 🗳️
Concepts Covered:  
DOM manipulation with refs  
Managing focus and persistent values
Lab Ideas:  
Create an auto-focus input field that focuses on mount.  
Build a Stopwatch app using useRef() to manage the interval ID.  
Implement a Video Player with play/pause controls using refs.  
Create a Multi-Input Form where refs manage focus between fields.  
Build a Scroll-to-Section feature that uses refs to scroll to specific elements.
These expanded labs ensure learners gain practical experience with each React concept, progressing from foundational skills to more complex applications. Each chapter now includes at least five labs, offering a robust hands-on learning path. Let me know if you’d like further details or additional labs for any section! 🚀