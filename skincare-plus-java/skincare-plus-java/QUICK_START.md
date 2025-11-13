# 🚀 QUICK START - SkinCare+ Java Project

## ⚡ 5-Minute Setup

### 1️⃣ Prerequisites Check
```bash
java -version    # Need 17+
mvn -version     # Need 3.8+
mysql --version  # Need 8.0+
```

### 2️⃣ Database Setup
```bash
mysql -u root -p
CREATE DATABASE skincare_db;
EXIT;
```

### 3️⃣ Configure & Run
```bash
cd backend
# Edit: src/main/resources/application.properties
# Change: spring.datasource.password=YOUR_PASSWORD

mvn clean install
mvn spring-boot:run
```

### 4️⃣ Test API
Open: http://localhost:8080/swagger-ui.html

---

## 📍 Key Files Location

```
skincare-plus-java/
├── README.md                    ← Project overview
├── SETUP_GUIDE.md              ← Detailed setup
├── INTERVIEW_GUIDE.md          ← Interview prep
├── PROJECT_SUMMARY.md          ← This summary
├── database/schema.sql         ← Database setup
└── backend/
    ├── pom.xml                 ← Dependencies
    └── src/main/
        ├── java/com/skincare/
        │   ├── SkincareApplication.java
        │   ├── model/          ← Entities
        │   ├── repository/     ← Data access
        │   ├── controller/     ← REST APIs
        │   ├── security/       ← JWT auth
        │   └── dto/           ← Data objects
        └── resources/
            └── application.properties
```

---

## 🔑 Must Change in application.properties

```properties
# Line 16: Your MySQL password
spring.datasource.password=YOUR_MYSQL_PASSWORD

# Line 34: Get from console.anthropic.com
claude.api.key=YOUR_CLAUDE_API_KEY

# Line 38: Get from console.cloud.google.com
google.maps.api.key=YOUR_GOOGLE_MAPS_KEY
```

---

## 🧪 Test Commands

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"Test@123"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"test","password":"Test@123"}'
```

---

## 🐛 Quick Fixes

**App won't start?**
```bash
mvn clean install -U
```

**Database error?**
```bash
mysql -u root -p
SHOW DATABASES;  # Check skincare_db exists
```

**Port 8080 busy?**
Change in application.properties: `server.port=8081`

---

## 📤 Push to GitHub

```bash
git init
git add .
git commit -m "Initial commit: SkinCare+ Application"
git remote add origin https://github.com/USERNAME/skincare-plus-java.git
git push -u origin main
```

---

## 🎯 Interview Demo (5 mins)

1. **Show GitHub** (30s)
2. **Code walkthrough** (2m)
   - Model → Repository → Controller
3. **Run app** (1m)
   ```bash
   mvn spring-boot:run
   ```
4. **Test API** (1.5m)
   - Open Swagger UI
   - Register → Login → Get Profile

---

## 📝 Key Points for Interview

**Technologies:**
- Java 17, Spring Boot 3.2, MySQL 8, JWT, Maven

**Architecture:**
- MVC pattern with layered architecture
- JPA/Hibernate for ORM
- RESTful API design

**Security:**
- JWT token authentication
- BCrypt password encryption
- Spring Security configuration

**Database:**
- 4 tables with proper relationships
- Normalized schema (3NF)
- Foreign key constraints

---

## 💡 One-Line Pitch

"I built an AI-powered skincare platform using Java Spring Boot that demonstrates full-stack development, secure authentication, database design, and API integration."

---

## ✅ Pre-Interview Checklist

- [ ] App runs without errors
- [ ] Can explain each component
- [ ] GitHub repo is public
- [ ] Practiced demo 3 times
- [ ] Read INTERVIEW_GUIDE.md
- [ ] Laptop charged
- [ ] Confident & ready!

---

## 🆘 Emergency Contacts

**Documentation:**
- Setup: SETUP_GUIDE.md
- Interview: INTERVIEW_GUIDE.md
- Full details: README.md

**Online Help:**
- Spring Boot: spring.io/guides
- Stack Overflow: stackoverflow.com
- Baeldung: baeldung.com

---

## 🎉 You're Ready!

✅ Complete project
✅ Professional docs
✅ Working code
✅ Interview prep

**Go ace that CTS interview! 🚀**

---

*Keep this card handy during interview prep*
