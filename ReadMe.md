# Chat App – Registration & Login System (Java)

This project is part of the **PROG5121 PoE** and implements a simple registration and login feature for a chat application.  
It demonstrates **OOP principles**, **unit testing with JUnit**, **GitHub version control**, and **responsible use of AI tools**.

---

## 📌 Features

- **Registration system**:
  - Username validation (must contain an underscore `_` and ≤ 5 characters).
  - Password complexity rules:
    - At least 8 characters long
    - At least 1 capital letter
    - At least 1 number
    - At least 1 special character
  - Cell phone validation:
    - Must include international code
    - South African format supported: `+27#########`

- **Login system**:
  - Verifies stored username and password
  - Returns success or failure messages

- **Unit Tests (JUnit 5)**:
  - Test username formatting
  - Test password complexity
  - Test cellphone validation
  - Test login success/failure

- **Message flags for chat extension** (future work):
  - `Message Sent`
  - `Message Received`
  - `Message Read`

---

## 📂 Project Structure

src/
├── main/java/za/co/monte/chat/
│ ├── Login.java # Core class with validation and login methods
│ └── Main.java # Console-based app entry point
└── test/java/za/co/monte/chat/
└── LoginTest.java # JUnit tests for Login class


---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven or NetBeans IDE
- GitHub account

### Clone the Repository
```bash
git clone https://github.com/Monte-Ntuli/-Programming-1A-PROG5121-2025
cd chat-app-poe

Run the App
mvn compile exec:java -Dexec.mainClass="za.co.monte.chat.Main"
or in NetBeans: Run → Main.java

Run Tests
mvn test

📖 References

Quickblox – Beginner’s Guide to Chat App Architecture - https://quickblox.com/blog/beginners-guide-to-chat-app-architecture/

APA Style – How to cite ChatGPT - https://apastyle.apa.org/blog/how-to-cite-chatgpt

👨‍💻 Author

Name: Banele Kamohelo Mpho Ntuli

Student Number: ST10493444

Module: PROG5121

✅ Rubric Coverage

✔ Username validation implemented

✔ Password validation implemented

✔ Regex cellphone validation (ChatGPT-assisted)

✔ Login authentication messages

✔ JUnit test cases (assertEquals, assertTrue, assertFalse)

✔ Code follows clean standards (no redundancy, clear methods)
