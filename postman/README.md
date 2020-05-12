# Postman
Automated REST API tests with YAddress API.

## How to run

### Via Postman collection runner
#### Pre-requisites:
- have [Postman](https://www.postman.com/downloads/) installed
#### Running
- open Postman
- in the top left, hit "Import" button
- on "import file" tab, hit "Choose Files" button
- select both environment and collection json files present in [postman folder](/postman)
- over collection tab, hit RUN button

![run button](/postman/tutorial-img/run.png)
- in collection runner screen, choose the environment and load data.csv file on "select file" button. In the end, it should look like this:

![collection runner screen](/postman/tutorial-img/collection-runner.png)
- note: if you hit "Preview" and it is not EXACTLY like the image below, please edit [data.csv](/postman/data.csv) file changing `,` for `;`. Then load it again and see if it is fixed.

![preview screen](/postman/tutorial-img/preview.png)
- now everything is ready to run. Just click "Run YAddress"
- on results screen, you can navigate through the iterations on the right side and check all the results.

![results screen](/postman/tutorial-img/results.png)
- also note that all assertions has their own descriptions, retrieved from data.csv file 
### Via command line
#### Pre-requisites:
- have [newman](https://blog.postman.com/installing-newman-on-windows/) installed
#### Running 
- open your terminal in the [postman](/postman) project's folder
- run `newman run YAddress.postman_collection.json -e YAddress.postman_environment.json -d data.csv` 
- you should see the results in your terminal

![newman-results](/postman/tutorial-img/newman-results.png)
- if you want to try this command line with more options like generating HTML and other cool features, you can take a look at [this Postman Documentation](https://learning.postman.com/docs/postman/collection-runs/command-line-integration-with-newman/).