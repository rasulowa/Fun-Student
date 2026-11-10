# Fun Student 🎓

Веб-приложение для управления студентами и пользователями (учебный проект).
Проект включает в себя полноценный бэкенд на Spring Boot и фронтенд, размещенный в статических ресурсах.

## 🛠 Технологический стек:

* **Backend:** Java 21, Spring Boot 4.0.8, Spring Security (JWT), Spring Data JPA, Hibernate.
* **Frontend:** HTML5, CSS3, Vanilla JavaScript.
* **База данных:** H2 (встроенная, для легкого запуска без установки PostgreSQL).
* **Сборка:** Maven (с использованием Maven Wrapper).
* **Библиотеки:** Lombok, JJWT.

## 🚀 Как запустить проект локально

Так как проект использует встроенную базу данных H2, вам **не нужно** ничего дополнительно устанавливать или настраивать. База данных создается в оперативной памяти при каждом запуске.


### 1. Клонируйте репозиторий::

git clone https://github.com/rasulowa/Fun-Student.git


### 2. Перейдите в папку с проектом:

cd Fun-Student


### 3. Запустите проект с помощью Maven Wrapper:

Для Windows (Git Bash / CMD):

./mvnw.cmd spring-boot:run


Для Linux / macOS:

./mvnw spring-boot:run


### 4. После появления надписи Started StudentApplication... в консоли, откройте браузер и перейдите по адресу:

http://localhost:8080

