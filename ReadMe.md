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