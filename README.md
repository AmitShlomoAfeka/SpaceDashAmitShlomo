**🚀 Space Dash**

📝 Description
   Space Dash is an exciting endless runner game where you control a rocket and must dodge asteroids while navigating through space. The game gets progressively harder as 
   speed increases, testing your reflexes and reaction time. Players can choose between button controls or sensor-based tilt controls to maneuver their rocket.

🌟 Features
   🔹 Smart Control System
      ✔ Button Mode - Move the rocket between lanes using on-screen buttons.
      ✔ Sensor Mode - Tilt your device left or right to move the rocket.


  🔹 Advanced Gameplay Mechanics
     ✔ Wider Road - The game now features five lanes instead of three.
     ✔ Longer Road - Endless mode with distance tracking (Odometer).
     ✔ Crash Sound Effects - Audio feedback on collisions.
     ✔ Collectible Coins - Earn bonus points along the way.

  🔹 Game Modes & Additional Screens
     ✔ Enhanced Main Menu - Choose between:

🎮 Button Mode (slow/fast).
📱 Sensor Mode (tilt-based control).
   ✔ High Score Table - Displays top 10 players.
   ✔ Location-Based Leaderboard - Saves player location along with the high score.
   ✔ Interactive Map - Clicking a high score shows the player's location on a map.
   
🎮 How to Play?
   1️⃣ Tilt the device or use buttons to switch lanes.
   2️⃣ Dodge asteroids to avoid losing lives.
   3️⃣ Collect coins to boost your score.
   4️⃣ The game ends when you lose all lives.
   5️⃣ If your score is high enough, it will be added to the leaderboard and displayed on the map.

📂 Project Structure
bash
Copy
Edit
app/src/main/java/com/example/space_dash_amit_shlomo_01/
│── MainActivity.kt          # Main game activity
│── HighScoresFragment.kt    # Leaderboard screen with Google Maps integration
│── GameManager.kt           # Core game mechanics management
│── HighScoresManager.kt     # Handles high scores and saves player data
│── HighScoresAdapter.kt     # RecyclerView adapter for high scores
│── MenuActivity.kt          # Main menu screen
│── MapFragment.kt           # Handles map and player locations
│── Obstacle.kt              # Manages obstacles and collectible items
│── ui/theme/                # XML styles and theme configurations  


🔄 Latest Updates
   ✅ Added Player Location Tracking - High scores now include GPS location.
   ✅ Fixed Leaderboard Bug - The game now correctly checks if the score is high enough before adding it to the table.
   ✅ Added Collectible Coins - Players can collect coins to increase their score.
   ✅ New UI Design - Updated theme and button styles.
   ✅ Expanded the Play Area - Five lanes instead of three for more strategic movement.
 

 
