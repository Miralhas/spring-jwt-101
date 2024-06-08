# spring-jwt-101
JWT Authentication with Spring Boot 3.3 and Spring Security 6.3

## Generating Public and Private RSA keys
```bash
# create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```
Insert the public.pem and private.pem files inside the resources folder (`src/main/resources`)