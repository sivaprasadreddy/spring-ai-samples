# Spring AI with OpenAI

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

```shell
curl --location 'http://localhost:8080/ai/chat?question=Tell%20me%20about%20SivaLabs%20blog'

curl --location 'http://localhost:8080/ai/chat-with-prompt?subject=AWS'

curl --location 'http://localhost:8080/ai/chat-with-system-prompt?subject=agile'

curl --location 'http://localhost:8080/ai/chat/movies?director=Quentin%20Tarantino'
curl --location 'http://localhost:8080/ai/chat/movies-as-map?director=Quentin%20Tarantino'
curl --location 'http://localhost:8080/ai/chat/movies-as-list?director=Quentin%20Tarantino'

curl --location 'http://localhost:8080/ai/rag/people?name=siva'

curl --location 'http://localhost:8080/ai/image?instructions=A%20robot%20reading%20news%20paper%20sitting%20on%20a%20bench%20in%20a%20park'

```