# 🖼️ Vulgarity Image Analyzer

A **Spring Boot-based** project that uses **AWS Rekognition** to analyze images and detect **explicit or unsafe content**, ensuring content moderation for platforms and services.

---

## 🚀 Features

- Image vulgarity detection powered by **AWS Rekognition**  
- Business logic layer for **processing and categorization**  
- Centralized **logging with Logback**  
- **Exception handling** with custom exceptions and global handlers  
- **Swagger integration** for easy API testing and documentation  
- **Cloud-ready configuration** for secure key management  

---

## 🖼️ Architecture Flow

<img width="3840" height="2332" alt="ImageVulgarity_Project_Flow" src="https://github.com/user-attachments/assets/dfb6b181-0ec7-40dc-9ebe-761ca22251d5" />

---

## ⚡ Tech Stack

| Layer             | Technology           |
|-------------------|----------------------|
| **Language**      | Java 17             |
| **Framework**     | Spring Boot 3       |
| **Cloud Service** | AWS Rekognition     |
| **Logging**       | Logback             |
| **Documentation** | Swagger / OpenAPI   |

---

## 🛠️ Setup & Installation

### **1. Clone the repository**
```bash
git clone https://github.com/gautamkr538/VulgarityImageAnalyzer.git
cd VulgarityImageAnalyzer

2. Configure AWS Credentials
Update your application.properties file:
properties
Copy
Edit
aws.accessKey=YOUR_AWS_ACCESS_KEY
aws.secretKey=YOUR_AWS_SECRET_KEY
aws.region=us-east-1

3. Build and Run
mvn clean install
mvn spring-boot:run
