# 📸 MiniGram

MiniGram — это учебный мини-клон социальной сети, написанный на Java (Servlets, JPA, JSP), с подключением к MySQL и использованием Bootstrap для фронтенда.

---



## ⚙️ Технологии

- Java 11+
- Jakarta Servlet API
- JSP
- JPA (Hibernate)
- MySQL
- Bootstrap 5
- Apache Tomcat

---




## 🚀 Как запустить

### 1. Клонируй репозиторий

```bash
git clone https://github.com/Capusta007/MiniGram.git
cd MiniGram
```

### 2. Создай базы данных
```sql
CREATE DATABASE MiniGramDB; -- основная база --
CREATE DATABASE MiniGram_TestDB: -- тетовая база --
```

### 3. Настрой persistence.xml
Перейди в папку src/main/resources/META-INF/ и:
Скопируй файл шаблона:
```bash
cp persistence.xml.example persistence.xml
```
Заполни своими данными подключения к БД:

```xml
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD_HERE"/>
```

То же самое нужно проделать с persistence.xml в папке src/test/resources/META-INF/

### 4. Собери и запусти проект
-Используй свою IDE или собери .war файл

-Разверни проект на Apache Tomcat

-Перейди в браузере:
http://localhost:8080/MiniGram
