# Read Me for Cadence Testing

## Run Cadence Server
Setup a cadence server locally

### Install docker and start service locally
- make sure you have docker installed https://docs.docker.com/engine/installation/
- run `docker-compose up` to start the cadence service
- make sure to keep the process running in the background

### Register Domain
- `docker run --network=host --rm ubercadence/cli:master --do test-domain domain register -rd 1`
- check that the domain is registered `docker run --network=host --rm ubercadence/cli:master --do test-domain domain describe`

## Run Cadence Worker Service
- start the spring server with `gradle bootRun`
- this will create a single worker with the default cadence configurations on tasklist `HelloWorldTaskList`

### Execute a Workflow
```docker run --network=host --rm ubercadence/cli:master --do test-domain workflow start --tasklist HelloWorldTaskList --workflow_type HelloWorldWorkflow::sayHello --execution_timeout 3600 --input \"World\"```
- this will create a new workflow on the test-domain we created on the `HelloWorldTasklist` which our worker listens to
- once the worker receives the task it will execute the `sayHello` function implemented in the code with the specified input

### View Workflow List
```docker run --network=host --rm ubercadence/cli:master --do test-domain workflow list```

### View a Workflows Execution History
```docker run --network=host --rm ubercadence/cli:master --do test-domain workflow showid 1965109f-607f-4b14-a5f2-24399a7b8fa7```
- replacing the uuid field with the desired workflow's uuid