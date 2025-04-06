# ✉️  Mail Service for knInit

Mail Service is a micro service responsible for sending HTML-formatted emails with dynamic values to selected user groups (Guests or Admins).

---

## Available Endpoints

The project provides 3 main endpoints for handling emails and 1 for saving templates:

### `/mail/sendVerification`
Sends a predefined verification email based on a `User` instance (`Guest` or `Admin`).

###  `/mail/sendToAll/{receivingGroup}`
Sends a new or existing template to a selected group of users.

###  `/mail/schedule/{receivingGroup}/{date}`
Schedules a new or existing email to be sent at a specified date and time.  
📌 *The date must be in ISO format: `yyyy-MM-dd'T'HH:mm:ss (seconds will be ignored)`*

### `/template/save`
Saves a new email template to the database.

---

## 📟 Request Body Formats

### SendEmailToAllRequest
```json
{
  "newTemplate": {
    "templateName": "string",
    "description": "string",
    "recipientGroups": ["string"],
    "templateHtmlBody": "string",
    "templateAlternateTextBody": "string",
    "subject": "string"
  },
  "from": "SELECTED_EMAIL",
  "templateName": "string",
  "dynamicVariables": ["string"]
}
```

### SendVerificationEmailRequest
```json
{
  "from": "SELECTED_EMAIL",
  "subject": "string",
  "verificationLink": "string",
  "personToVerify": {
    "id": {
      "timestamp": 0,
      "date": "2025-04-06T12:37:40.912Z"
    },
    "createdAt": "2025-04-06",
    "updatedAt": "2025-04-06",
    "firstName": "string",
    "lastName": "string",
    "password": "string",
    "emails": ["string"],
    "token": "string",
    "verified": true,
    "type": "string",
    "discordUserName": "string",
    "adminPermissions": ["string"]
  }
}
```

📋 **Note:** To choose between a new or an existing template, set one of the fields (`templateName` or `newTemplate`) to `null`.  
`templateName` corresponds to the `template_name` column in the database.

📌 **Important:** The `from` field is an enum that includes both the email address and password – typing an email manually **will not work**.

---

## Saving a Template

To save an email template without sending it, use the endpoint:
```
POST /template/save
```
### CreateTemplateRequest
```json
{
  "newTemplate": {
    "templateName": "string",
    "description": "string",
    "recipientGroups": ["string"],
    "templateHtmlBody": "string",
    "templateAlternateTextBody": "string",
    "subject": "string"
  }
}
```

---

## 🔧 How to Run Locally

The project includes a `docker-compose` file which starts:
- MongoDB
- Spring Boot with the Mail Service

 Sample data is not included yet.

### ✅ Instructions:
```bash
git clone https://github.com/01szak/Mail-Service.git
cd Mail-Service
docker-compose up --build
```

---

## 📸 Visuals

![Zrzut ekranu 2025-04-06 141920](https://github.com/user-attachments/assets/3d1a77eb-d846-4d54-baf9-d1c753a5a411)

![Zrzut ekranu 2025-04-06 144346](https://github.com/user-attachments/assets/f852bc45-30c1-4883-8230-48fd4fa7e9de)

---

## 👤 Author
Created by [01szak](https://github.com/01szak)

