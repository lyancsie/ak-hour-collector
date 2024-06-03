# 📧 A&K Academy Email Sending Application

Welcome to the monthly lesson e-mail sending for the teachers of A&K Academy! This application allows teachers to provide the app links to their lessons, collect the number of those lessons, and send the details to the company's director at the beginning of every month.

## 🚀 Main Features

- **Collect Lesson Links:** Teachers can provide links to their lessons.
- **Monthly Statistics:** The app gathers lesson statistics from the provided links.
- **Automated Email Sending:** Sends the lesson statistics to the director (lambda and gmail api version available).

## 🌲 Branches

### `lambda` Branch
This branch is designed as an AWS Lambda application that sends the lesson statistics of the **previous** month to the director. The director's email address is configurable.

### `develop` Branch
This branch aims to achieve the same goal via Gmail integration. However, it is still a work in progress and not fully functional yet.

## ⚙️ Environment Variables

To run this application, you need to set the following environment variables:

| Variable Name       | Description                                       |
|---------------------|---------------------------------------------------|
| `atlassian-email`   | Your Atlassian email address                      |
| `api-key`           | Your configuration API key                        |
| `urls`              | Lesson URLs                                       |
| `year`              | Year (only required on the `develop` branch)      |
| `month`             | Month (only required on the `develop` branch)     |
| `hourly-wage`       | Hourly wage rate                                  |
| `vat`               | Value Added Tax (boolean)                         |

## 📝 Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/email-sending-app.git
   cd email-sending-app
   ```

2. **Checkout to the desired branch:**
   ```bash
   # For AWS Lambda setup
   git checkout lambda

   # For Gmail integration setup (development)
   git checkout develop
   ```
3. **Create AWS and set the environment variables:**
    - Create an AWS account
    - Create credentials
    - Set AWS_ACCESS_KEY and AWS_SECRET_ACCESS_KEY for the lambda branch.
4. Verify e-mail addresses for both the sender and the recipient e-mail in AWS  
5. **Install dependencies:**
   ```bash
   mvn clean install
   ```

## 📬 How It Works

1. Teachers submit their lesson links.
2. The application collects and compiles the lesson statistics.
3. At the beginning of each month, an email with the statistics is sent to the director.

For AWS Lambda, the application runs automatically. For the Gmail integration (develop branch), manual steps might be required until the branch is fully functional.

## 👥 Contributing

I welcome contributions! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

---

Happy coding! 😊 If you have any questions or need further assistance, feel free to open an issue or contact me.

