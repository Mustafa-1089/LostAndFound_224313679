Lost & Found App
An Android application built in Java that allows users to post and browse lost and found item listings. Users can submit details about items they've lost or found, upload images, and search/filter through all posted listings.

Features
Post Listings — Create adverts for lost or found items with full details
Image Upload — Attach a photo to each listing from your device gallery
Category Tagging — Organise listings by category (Electronics, Clothing, Jewellery, etc.)
Live Search — Search listings by name, description, or location in real time
Category Filter — Filter all listings by category using a dropdown spinner
Auto Timestamp — Each post is automatically stamped with the date and time it was created
Remove Listings — Mark items as resolved and remove them from the list instantly

Built With
Java — Primary programming language
Android Studio — IDE
Room Database — Local SQLite database abstraction layer
RecyclerView — Efficient scrollable list of item cards
ActivityResultLauncher — Modern image picker API
Material Design Components — UI styling

Key Implementation Details
Image Storage
Images selected from the gallery are copied into the app's private internal storage rather than saving the URI. This ensures the image remains accessible across sessions, as Android revokes temporary URI permissions after navigation.
Search & Filter
All filtering runs through a single optimised Room query using SQL LIKE wildcards for text search combined with conditional category matching — keeping the logic clean and efficient.
Validation
Form validation uses a reusable isEmpty() helper method to avoid repetitive code and provide consistent user feedback via Toast messages.
