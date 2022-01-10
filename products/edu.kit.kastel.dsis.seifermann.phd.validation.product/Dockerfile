FROM debian:bullseye-slim

RUN apt-get update && \
    apt-get install -y openjdk-11-jre-headless swi-prolog && \
    rm -rf /var/lib/apt/lists/*

ADD target/products/edu.kit.kastel.dsis.seifermann.phd.validation.product/linux/gtk/x86_64 /opt/eclipse

WORKDIR "/opt/eclipse"
VOLUME ["/tmp/result"]

ENTRYPOINT ["./eclipse"]
CMD ["/tmp/result"]
