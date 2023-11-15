# rdbx
Web application with discount calculation and admin/manager panel

## Before start
In the application.properties file, replace the values ​​with yours </br>
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_name #Your databae name
spring.datasource.username=username #Your username to connect to the database
spring.datasource.password=password #Your password to connect to the database

spring.mail.host=smtp.yandex.ru #Your smtp host
spring.mail.port=465 #Smtp port
spring.mail.username=username #Your username/email for smtp host
spring.mail.password=password #Your password for smtp host
spring.mail.protocol=smtps #Protocol
```
In the UserService.java file, replace the values with yours </br>
```java
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findFirstByName("ROLE_USER").orElseThrow(IllegalStateException::new)));

        userRepository.save(user);

        try {
            String qrCodeData = generateQRCode(user.getUsername());
            System.out.println(qrCodeData);

            Smsc smsc = new Smsc("login", "password"); // Your login and password for the SMSC service
            String[] result = smsc.send_sms(user.getPhoneNumber(), qrCodeData, 0,
                    "", "", 0, "", "");
            System.out.println("ID сообщения: " + Arrays.toString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
```

## Used
1. Main - Spring Boot, Spring Security, Spring Data JPA </br>
2. Spring Boot Starter Mail - for working with emails </br>
3. ZXing and Java UUID Generator for QR code generation </br>
4. Tomcat and JSTL - servlets </br>
5. SMSC API - for sms sends </br>
6. MySQL - database </br>
7. Apache Maven - for build </br>
8. JSP, JS, CSS - front
