FROM eclipse-temurin:23-jre
WORKDIR /app
COPY build/install/tea-tastings-bot /app
VOLUME ["/app/tea-tastings-bot.json"]
CMD ["bin/tea-tastings-bot"]
