# Investment-funds-

> **Disclaimer:** Este repositorio no corresponde a código oficial de BTG Pactual, únicamente corresponde a una prueba técnica personal.

amaris test

## How to run locally

### Prerequisites
- Java 17+ (OpenJDK 17 recommended)
- Gradle 8.x or later (or use the provided wrapper)

### Build and Run
```bash
# Using the wrapper (Recommended)
# Run with 'local' profile to use in-memory mock database
./gradlew bootRun --args='--spring.profiles.active=local'
```

Alternatively, if you have set `SPRING_PROFILES_ACTIVE=local` in your environment:
```bash
./gradlew bootRun
```

## Deployment to AWS Lambda

This project is configured to be deployed as an AWS Lambda function using the Serverless Framework.

### Prerequisites
- Node.js and NPM
- Serverless Framework (`npm install -g serverless`)
- AWS CLI configured with appropriate credentials

### Steps to Deploy

1. **Build the Artifact**
   Create the shadow JAR (Uber-JAR) required for AWS Lambda:
   ```bash
   ./gradlew clean shadowJar
   ```
   This will generate `build/libs/investment-funds-1.0.0-aws.jar`.

2. **Deploy**
   Run the deployment command. By default, it will use the 'prod' profile (defined in serverless.yml):
   ```bash
   serverless deploy
   ```

   **Important:** You must configure the MongoDB environment variables for the Lambda function. You can do this via the AWS Console after deployment or by updating `serverless.yml` (e.g., using SSM Parameter Store or Secrets Manager).

   Required Environment Variables:
   - `MONGO_HOST`
   - `MONGO_PORT`
   - `MONGO_DATABASE`
   - `MONGO_USERNAME`
   - `MONGO_PASSWORD`

3. **Remove Deployment**
   To remove the stack from AWS:
   ```bash
   serverless remove
   ```

## CI/CD Pipeline (GitHub Actions)

This repository includes a GitHub Actions workflow `.github/workflows/deploy.yml` that automatically deploys the application to AWS Lambda when pushing to the `master` branch.

### Required GitHub Secrets

To enable the pipeline, you must configure the following **Secrets** in your GitHub repository settings (Settings > Secrets and variables > Actions):

*   `AWS_ACCESS_KEY_ID`: Your AWS Access Key.
*   `AWS_SECRET_ACCESS_KEY`: Your AWS Secret Key.

*(Optional) If you want to automate database credentials injection:*
*   `MONGO_HOST`
*   `MONGO_PASSWORD`, etc. (and update `serverless.yml` to reference them).
