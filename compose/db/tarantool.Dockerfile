FROM tarantool/tarantool:3.0.1
COPY ../../../saas-space/compose/db/saas-dialogue/config.yaml saas-dialogue/instances.yaml saas-dialogue/saas-dialogue-0.0.1-1.rockspec /opt/tarantool/saas-dialogue/
COPY ../../../saas-space/compose/db/saas-dialogue/override /opt/tarantool/saas-dialogue
RUN apt update  \
    && apt --no-install-suggests install -y git cmake unzip\
    && cd /opt/tarantool/saas-dialogue \
    && tt build \
    && apt clean \
    && apt -y autoremove \
    && rm -rf /var/lib/apt/lists/
CMD ["tarantool"]