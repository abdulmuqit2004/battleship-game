# Battleship Game

## Project Description

This is a graphical user interface (GUI)-based Battleship game. It is a multi-client, multiplayer game where players can join in, place their ships, and take turns to attack each other’s ships. The game is implemented using Java, with a client-server architecture, allowing players to play from different machines. Players communicate via chat, roll dice to determine their turn, and attempt to sink each other's fleet.

### Features
- **Multiplayer**: 4 players can play from different machines using sockets and multi-threading.
- **Ship Placement**: Players can place their ships in the grid, with the option to rotate and position them strategically.
- **Turn-based Gameplay**: Players take turns attacking the enemy’s grid.
- **Chat System**: Players can communicate with each other through a live chat.
- **Win Condition**: The first player to sink all enemy ships wins.

## How to Run the Game

### Prerequisites

To run the Battleship game, make sure you have the following installed:

- **Java 23** (or newer)
- **Maven** (for dependency management)
- **Socket Server & Client Configuration** (for multiplayer play)

### Steps to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/battleship.git


2. Navigate to the project directory:

   ```bash
   cd battleship

3. Install the dependencies using Maven:

   ```bash
   mvn install
    
4. Run the Server

   ```bash
   mvn exec:java -Dexec.mainClass="com.battleship.server.Server"

Alternatively, if you are using an IDE like IntelliJ IDEA, simply open the project and run the `Main` class.

### Dependencies:
- Java 23 (or later)
- Any IDE with Java support (e.g., IntelliJ IDEA, Eclipse)
- Optionally, Docker for containerization (not required)

> If you are using Docker, ensure you have Docker installed, and use the provided Dockerfile to build and run the container.

## Other Resources

### Libraries Used:
- **Java Swing**: Used to create the GUI for the Ludo game.

### Boilderplate Code:
- None used.

> For any questions or issues, feel free to open an issue in the repository.

### Video Demo
If you have a video demo, you can embed it here:
[Google Drive Demo](link_to_your_video)

Alternatively, you can host the demo on a platform like YouTube, ensuring it's accessible to your instructors for grading.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
