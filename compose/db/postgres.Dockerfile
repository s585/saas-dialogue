FROM postgres:16.1-alpine
COPY postgres/init_scripts/* /docker-entrypoint-initdb.d/