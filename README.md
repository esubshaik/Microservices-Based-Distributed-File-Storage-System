# 📁 Microservices-Based Distributed File Storage System (DFS)

A **Distributed File Storage System** built using **Spring Boot microservices** and **React**, designed to store and retrieve files across **multiple storage nodes** with **automatic service discovery, replication, and streaming-based file transfer**.

This project demonstrates real-world **distributed systems concepts** such as **Eureka-based service discovery**, **Feign client communication**, **octet-stream file transfer**, and **replica-based fault tolerance**.

---

## 📦 Technologies Used

### Backend
- Java 17
- Spring Boot
- Spring Cloud Netflix Eureka
- Spring Cloud OpenFeign
- Spring Data JPA
- H2 / RDBMS
- REST APIs
- Octet-stream file transfer

### Frontend
- React
- Axios
- HTML / CSS

---

## Key Highlights

✔ Distributed storage across multiple node servers  
✔ Automatic node discovery using **Eureka Server**  
✔ No hardcoded IPs or ports for storage nodes  
✔ Backend dynamically connects to available nodes  
✔ File replication across multiple nodes  
✔ Octet-stream based upload & download (supports any file format)  
✔ Metadata managed centrally  
✔ Used H2 DB to store filename and chunk details (can be replaced with any other DB)
✔ React UI for file upload, listing, and download  
✔ Scalable, fault-tolerant design  

---

## System Architecture

                         ┌──────────────────────────┐
                         │        React UI          │
                         │  (Upload / Download UI)  │
                         └─────────────┬────────────┘
                                       │
                                       │ HTTP (Octet Stream)
                                       ▼
                    ┌────────────────────────────────────┐
                    │           Backend Server            │
                    │      (Metadata + Coordinator)       │
                    │                                    │
                    │  - File Metadata Management         │
                    │  - Chunking & Replica Selection     │
                    │  - Feign Clients                    │
                    └─────────────┬──────────────────────┘
                                  │  Service Name
                                  │  (node-server)
                                  ▼
                         ┌──────────────────┐
                         │   Eureka Server   │
                         │ (Service Registry)│
                         └─────────┬────────┘
                                   │
          ┌────────────────────────┼────────────────────────┐
          ▼                        ▼                        ▼
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│  Storage Node 1  │    │  Storage Node 2  │    │  Storage Node 3  │
│  (node-server)   │    │  (node-server)   │    │  (node-server)   │
│                  │    │                  │    │                  │
│ - Store Chunks   │    │ - Store Chunks   │    │ - Store Chunks   │
│ - Serve Chunks   │    │ - Serve Chunks   │    │ - Serve Chunks   │
└──────────────────┘    └──────────────────┘    └──────────────────┘


---

## 🧩 Core Components

### 1️⃣ Eureka Server
- Acts as a **service registry**
- All storage nodes register automatically
- Backend fetches live node list dynamically

### 2️⃣ Backend Server (Coordinator)
- Manages file metadata
- Splits files into chunks
- Replicates chunks across multiple nodes
- Uses **Feign Client** to communicate with nodes
- No explicit node IP/port configuration
- Handles upload, download, and file listing

### 3️⃣ Storage Node Servers
- Store file chunks locally
- Register themselves with Eureka
- Serve chunks on demand
- Replication ensures fault tolerance

### 4️⃣ Frontend (React)
- Upload files of **any format**
- View uploaded files
- Download files seamlessly
- Uses raw byte (octet-stream) transfers

---

## 🔗 APIs Overview

| Method | Endpoint | Description |
|------|--------|-------------|
| POST | `/files/upload` | Upload file using octet-stream |
| GET | `/files/list` | List stored files (metadata) |
| GET | `/files/{fileId}` | Download file |
| GET | `/eureka` | Eureka dashboard |

---

## 🔄 File Upload Flow

1. User uploads file from React UI
2. Backend receives raw byte stream
3. File metadata stored centrally
4. File is split into chunks
5. Chunks are replicated across multiple nodes
6. Node selection is dynamic via Eureka

---

## 📥 File Download Flow

1. User requests file from UI
2. Backend fetches metadata
3. Chunks are retrieved from available nodes
4. File is reconstructed
5. Streamed back to the client

---

## 🔐 Fault Tolerance & Scalability

- Multiple replicas per file chunk
- Node failure handled automatically
- New storage nodes can join dynamically
- No system restart required
- Horizontal scaling supported

---

## 🛠️ Running the Project

### Start Order
1. Eureka Server
2. Storage Node Servers (any number)
3. Backend Server
4. Frontend (React)