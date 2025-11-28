## SQL Practice JDBC Example

This project provides a small, self-contained example demonstrating **Java Database Connectivity (JDBC)**. It utilizes the free SQL practice problems and database structure available at **sql-practice.com**.

---

### Key Components

* **Database:** Configured to run against both **H2 (in-memory)** for quick testing and **MySQL** for a production-like setup.
* **Purpose:** Illustrates fundamental JDBC operations, including:
    * **Connection management**
    * **Statement execution**
    * **Result set handling**
    * **SQL querying** based on the practice exercises.
* **Goal:** Serve as a minimal **proof of concept** or a **learning tool** for connecting a Java application to a relational database using standard JDBC API.
* **Run H2:** mvn clean test -Ddatabase=h2
* **Run MySQL:** mvn clean test -Ddatabase=mysql -Dhost={host} -Duser={user} -Dpassword={password}
    * host - e.g. "127.0.0.1:3306"
