#!/usr/bin/env bash

#gradle assemble

echo 'Copy files...'

scp -i ~/.ssh/id_ed25519 \
    build/libs/MyLabsBackend-0.0.1-SNAPSHOT.jar \
    root@185.182.111.172:~

echo 'Restart server...'

ssh -i ~/.ssh/id_ed25519 root@185.182.111.172 << EOF

pgrep java | xargs kill -9
nohup java -jar MyLabsBackend-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'