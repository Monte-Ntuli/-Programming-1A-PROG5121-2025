This project implements a **complete chat system** consisting of:

* **User Registration**
* **Login Verification**
* **Sending Messages**
* **Storing Messages**
* **Searching Messages**
* **Deleting Messages**
* **Message Reports**
* **JSON File Handling**
* **JUnit 5 Unit Testing**

It demonstrates **Object-Oriented Programming (OOP)**, **regex validation**, **Java Swing (JOptionPane) interaction**, **file management**, **arrays**, **parallel arrays**, **Maven/JUnit**, and **responsible, cited use of AI tools (ChatGPT)**.

---

# 🚀 Features Overview

## ✅ **PART 1 – Registration & Login**

### ✔ Registration includes:

* Username requirements:

  * must contain `_`
  * max length **≤ 5**
* Password complexity rules:

  * ≥ 8 characters
  * at least one **uppercase**
  * at least one **digit**
  * at least one **special character**
* Cellphone validation:

  * must include international code
  * supports strict SA regex: `+27#########`
  * *(Regex developed with assistance from ChatGPT – APA reference included)*

### ✔ Login includes:

* Username + password verification
* Correct authentication messages:

  * **Welcome <name surname> …**
  * **Incorrect credentials, please try again**

---

# 💬 **PART 2 – Messaging System**

Users can:

* Send messages
* Discard messages
* Store messages for later
* Automatically generate:

  * message ID (10-digit)
  * message hash:

    ```
    FirstTwoDigitsOfID:MessageNumber FirstWordLastWord (UPPERCASE)
    ```
* Print full message details:

  * ID
  * Hash
  * Recipient
  * Text

Stored messages are written to:
📄 **messages.json**

Sent messages increment a static counter.

Message validation:

* checks cell number format
* checks message body (<= 250 chars)

---

# 🧰 **PART 3 – Message Tools (MessageArrays)**

Includes **full PoE-required functionality**:

### ✔ a. Display sender + all recipients

Shows:

```
Sender: John Doe → Recipient: +27XXXXXXXXX
Sender: John Doe → Recipient: +27XXXXXXXXX
```

### ✔ b. Show the longest sent message

### ✔ c. Search by message ID

Returns:

```
Recipient: +27XXXXXXXXX
Message: Your message text...
```

### ✔ d. Search all messages for a recipient

### ✔ e. Delete a message using its hash

(Deletes from the history list)

### ✔ f. Display a full sent-message report

With table-like formatting.

### ✔ g. Send stored messages later

User can pick from stored messages → convert to “SEND”.

### ✔ JSON file reading

`readStoredFromJSON()` loads stored messages into an array.

---

# 📂 **Project Structure**

```
src/
 ├── main/java/za/co/monte/chat/
 │     ├── Login.java
 │     ├── Message.java
 │     ├── MessageArrays.java
 │     └── Main.java
 │
 └── test/java/za/co/monte/chat/
       ├── LoginIT.java
       ├── MessageIT.java
       ├── MessageArraysIT.java
       └── MainIT.java
```

---

# 🧪 **JUnit 5 Tests**

Covers:

### ✔ Login class

* getters / setters
* username validation
* password complexity
* cellphone regex
* login result
* registration status message

### ✔ Message class

* ID generation
* hash generation
* body validation
* send/discard/store logic
* JSON saving
* getters

### ✔ MessageArrays

* longest message
* search by ID
* search by recipient
* delete by hash
* build report
* read JSON file

---

# 🛠 Getting Started

## Requirements

* Java **17 or 21**
* NetBeans, IntelliJ or VS Code
* Maven
* GitHub

---

## Clone the Repository

```bash
git clone https://github.com/Monte-Ntuli/-Programming-1A-PROG5121-2025
cd chat-app-poe
```

---

## Run the App (Maven)

```bash
mvn compile exec:java -Dexec.mainClass="za.co.monte.chat.Main"
```

### In NetBeans:

`Run → Main.java`

---

## Run All Unit Tests

```bash
mvn test
```

---

# 📖 References

**ChatGPT Assistance Citation (APA 7th Edition):**
OpenAI. (2025). *ChatGPT (Jan 2025 version)* [Large language model]. [https://chat.openai.com](https://chat.openai.com)

**Regex Reference:**
Regular expression guidance and structure generated with assistance from ChatGPT (OpenAI, 2025).

**Additional Reading:**
QuickBlox. (n.d.). *Beginner’s Guide to Chat App Architecture.* [https://quickblox.com/blog/beginners-guide-to-chat-app-architecture/](https://quickblox.com/blog/beginners-guide-to-chat-app-architecture/)

---

# 👨‍💻 Author

**Name:** Banele Kamohelo Mpho Ntuli
**Student Number:** ST10493444
**Module:** PROG5121 Programming
**Year:** 2025

# ✅ Rubric Coverage Summary

✔ Username validation
✔ Password complexity (uppercase, number, special char)
✔ Cellphone regex validation
✔ Login authentication
✔ Message ID & hash
✔ Store messages in JSON
✔ Parallel arrays for Part 3
✔ Longest message
✔ Search + delete
✔ Full sent messages report
✔ JUnit testing (Login, Message, MessageArrays, Main)
✔ Ethical + APA-formatted ChatGPT citation

