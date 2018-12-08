# TokenBasedAuthentication
Authentication based on Token using S3 and DynamoDB
This project based on 2 step authentication wherein password (stored in dynamoDB) and token file(S3) is used for authentication. First time the user registers, he uploads the file which is used as token for the all next times. Uploaded file is hashed by default AWS hashing method.
