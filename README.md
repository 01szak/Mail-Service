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

📋 **Note:** 
- To choose between a new or an existing template, set one of the fields (`templateName` or `newTemplate`) to `null`.  
- `templateName` corresponds to the `template_name` column in the database.
- dynamic variables in html body should be named as Value_1, Value_2 etc. and values in dynamicVariables should be placed in order (e.g. dynamicVariables[0] for Value_1)
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

![Zrzut ekranu 2025-04-06 150811](https://github.com/user-attachments/assets/60cf1c71-0f8b-406c-8fac-25171b4aa4f1)
![Zrzut ekranu 2025-04-06 150900](https://github.com/user-attachments/assets/4f28db45-0889-4325-acc3-186035ba478a)
![Zrzut ekranu 2025-04-06 151053](https://github.com/user-attachments/assets/0aaa9034-5302-44ba-9c18-b21d72a52347)
![image](https://github.com/user-attachments/assets/da3e907e-6cd7-4d3b-8c54-5e9f225cce13)


---

## 👤 Author
Created by [01szak](https://github.com/01szak)

