# My Wallet App
How to build and run:

## 1. Build
Run the gradle build either through interactive IDE
(OR) in the command line by running the command ***gradle*** from the root folder of the project

## 2. JUnits & Code Coverage
When the gradle build is successfully completed, the junits are also executed as part of the build. <br>
The JUnit reports along with Code Coverage are available at:

- [JUnit Report](build\reports\tests\test\index.html)<br>
- [Code Coverage Report](build\jacoco\test\html\index.html)

## 3. Run the app
1. Go to the folder: *<project_folder>*\delivery
2. Run the batch file: **RunWallet.bat**
   <p>The app is up & running now, ready to recieve REST requests in the default port 8080</p>

## 4. Test the app using Swagger
1. In a browser, open the link [here](http://localhost:8080/swagger-ui.html)
2. From the home page, click on the option: **wallet-controller**
3. After that, click the **GET** button for the REST API *processTxn*
4. Next click, the option **Try it out**
5. Enter the parameters for inputs: **amount** and **wallet** and finally click **Execute**
6. Try various test conditions for both positive & negative cases.
