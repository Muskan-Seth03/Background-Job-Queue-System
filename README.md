# queuectl

queuectl is a command-line tool for managing job queues, including enqueueing jobs, listing jobs by state, managing queue configuration, handling dead letter queues, monitoring queue status, and managing worker processes.

## Commands

- `enqueue <jobJson>`: Enqueue a job with the job JSON string.
- `list --state <state>`: List jobs by state. States include PENDING, PROCESSING, COMPLETED, DEAD.
- `config set <key> <value>`: Manage queue configuration settings like max-retries and backoff-base.
- `dlq list`: List all dead jobs in the dead letter queue.
- `dlq retry`: Retry jobs from the dead letter queue.
- `status`: Show the current status of the queue.
- `worker start --count <n>`: Start worker processes.
- `worker stop`: Stop worker processes.
- `help`: Display help information about commands.

## Usage

Run the tool with the desired command and options. For example:

```
queuectl enqueue '{"jobType":"email","payload":{"to":"user@example.com"}}'
queuectl list --state PENDING
queuectl config set max-retries 3
queuectl dlq list
queuectl status
queuectl worker start --count 5
```

## Requirements

- Java 11 or higher
- Maven

## Build and Run

Use Maven to build and run the project:

```
mvn clean install
mvn spring-boot:run
```

## License

This project is licensed under the MIT License.
